package service;

import model.Student;
import repository.StudentRepository;
import security.RequiresTeacher;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("Student not found: ID " + id));
    }

    public void addStudent(Student student) {
        List<Student> students = repository.findAll();
        int maxId = students.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0);
        student.setId(maxId + 1);

        students.add(student);
        repository.saveAll(students);
        audit.log("Added student: " + student);
    }

    @RequiresTeacher
    public void removeStudent(int id) {
        List<Student> students = repository.findAll();
        boolean removed = students.removeIf(s -> s.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException("Student not found: ID " + id);
        }
        repository.saveAll(students);
        audit.log("Removed student with ID: " + id);
    }

    @RequiresTeacher
    public void changeTokens(int id, int delta) {
        List<Student> students = repository.findAll();
        Student target = students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student not found: ID " + id));
        int newTokenCount = target.getTokens() + delta;
        if (newTokenCount < 0) {
            newTokenCount = 0; // просто обнуляем
        }
        target.setTokens(newTokenCount);
        repository.saveAll(students);
        audit.log("Changed tokens for ID " + id + " by " + delta + ". New total: " + newTokenCount);
    }
}
