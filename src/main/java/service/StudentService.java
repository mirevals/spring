package service;

import model.Student;
import repository.StudentRepository;
import security.Role;
import security.RoleContext;
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

    // Доступен и студентам, и преподавателям
    public void addStudent(Student student) {
        Role role = RoleContext.getRole();
        if (role == Role.STUDENT && student.getTokens() > 0) {
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


    @security.RequiresTeacher
    public void removeStudent(String firstName, String lastName) {
        List<Student> students = repository.findAll();
        students.removeIf(s -> s.getFirstName().equalsIgnoreCase(firstName)
                && s.getLastName().equalsIgnoreCase(lastName));
        repository.saveAll(students);
        audit.log("Removed student: " + firstName + " " + lastName);
    }

    public void changeTokens(String firstName, String lastName, int delta) {
        Role role = RoleContext.getRole();
        if (delta > 0 && role != Role.TEACHER) {
            throw new SecurityException("Only teachers can increase tokens.");
        }

        List<Student> students = repository.findAll();
        for (Student s : students) {
            if (s.getFirstName().equalsIgnoreCase(firstName)
                    && s.getLastName().equalsIgnoreCase(lastName)) {
                s.setTokens(s.getTokens() + delta);
                audit.log("Changed tokens for " + firstName + " " + lastName + " by " + delta);
                break;
            }
        }
        repository.saveAll(students);
    }
}
