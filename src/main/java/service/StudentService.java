package service;

import model.Student;
import model.UserRole;
import repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final AuditService audit;

    public StudentService(StudentRepository repository, AuditService audit) {
        this.repository = repository;
        this.audit = audit;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(int id) {
        return repository.findAll().stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Student getStudentByName(String firstName, String lastName) {
        return repository.findAll().stream()
                .filter(s -> s.getFirstName().equalsIgnoreCase(firstName) && 
                           s.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    // Доступен и студентам, и преподавателям
    public void addStudent(Student student, UserRole userRole) {
        if (userRole == UserRole.STUDENT && student.getTokens() > 0) {
            throw new SecurityException("Students can't assign tokens to new students.");
        }

        List<Student> students = repository.findAll();

        // Генерируем новый уникальный id
        int maxId = students.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0);
        student.setId(maxId + 1);

        students.add(student);
        repository.saveAll(students);
        audit.log("Added student: " + student);
    }

    public void removeStudent(String firstName, String lastName, UserRole userRole) {
        if (userRole != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can remove students.");
        }

        List<Student> students = repository.findAll();
        boolean removed = students.removeIf(s -> s.getFirstName().equalsIgnoreCase(firstName)
                && s.getLastName().equalsIgnoreCase(lastName));
        
        if (removed) {
            repository.saveAll(students);
            audit.log("Removed student: " + firstName + " " + lastName);
        } else {
            throw new IllegalArgumentException("Student not found: " + firstName + " " + lastName);
        }
    }

    public void changeTokens(String firstName, String lastName, int delta, UserRole userRole) {
        if (delta > 0 && userRole != UserRole.TEACHER) {
            throw new SecurityException("Only teachers can increase tokens.");
        }

        List<Student> students = repository.findAll();
        boolean found = false;
        
        for (Student s : students) {
            if (s.getFirstName().equalsIgnoreCase(firstName)
                    && s.getLastName().equalsIgnoreCase(lastName)) {
                s.setTokens(s.getTokens() + delta);
                found = true;
                audit.log("Changed tokens for " + firstName + " " + lastName + " by " + delta);
                break;
            }
        }
        
        if (found) {
            repository.saveAll(students);
        } else {
            throw new IllegalArgumentException("Student not found: " + firstName + " " + lastName);
        }
    }
}
