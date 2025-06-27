package service;

import model.Student;
import model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private AuditService audit;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(repository, audit);
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        // Given
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "John", "Doe", 5));
        expectedStudents.add(new Student(2, "Jane", "Smith", 3));
        
        when(repository.findAll()).thenReturn(expectedStudents);

        // When
        List<Student> actualStudents = studentService.getAllStudents();

        // Then
        assertEquals(expectedStudents, actualStudents);
        verify(repository).findAll();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe", 5));
        students.add(new Student(2, "Jane", "Smith", 3));
        
        when(repository.findAll()).thenReturn(students);

        // When
        Student result = studentService.getStudentById(1);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void getStudentById_WhenStudentNotExists_ShouldReturnNull() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe", 5));
        
        when(repository.findAll()).thenReturn(students);

        // When
        Student result = studentService.getStudentById(999);

        // Then
        assertNull(result);
    }

    @Test
    void addStudent_WhenTeacher_ShouldAddStudentWithTokens() {
        // Given
        List<Student> students = new ArrayList<>();
        Student newStudent = new Student(0, "New", "Student", 10);
        
        when(repository.findAll()).thenReturn(students);

        // When
        studentService.addStudent(newStudent, UserRole.TEACHER);

        // Then
        verify(repository).saveAll(anyList());
        verify(audit).log(contains("Added student"));
    }

    @Test
    void addStudent_WhenStudent_ShouldThrowExceptionForTokens() {
        // Given
        Student newStudent = new Student(0, "New", "Student", 10);

        // When & Then
        assertThrows(SecurityException.class, () -> {
            studentService.addStudent(newStudent, UserRole.STUDENT);
        });
    }

    @Test
    void changeTokens_WhenTeacher_ShouldAllowPositiveChange() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe", 5));
        
        when(repository.findAll()).thenReturn(students);

        // When
        studentService.changeTokens("John", "Doe", 3, UserRole.TEACHER);

        // Then
        verify(repository).saveAll(anyList());
        verify(audit).log(contains("Changed tokens"));
    }

    @Test
    void changeTokens_WhenStudent_ShouldThrowExceptionForPositiveChange() {
        // When & Then
        assertThrows(SecurityException.class, () -> {
            studentService.changeTokens("John", "Doe", 3, UserRole.STUDENT);
        });
    }

    @Test
    void removeStudent_WhenTeacher_ShouldRemoveStudent() {
        // Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Doe", 5));
        
        when(repository.findAll()).thenReturn(students);

        // When
        studentService.removeStudent("John", "Doe", UserRole.TEACHER);

        // Then
        verify(repository).saveAll(anyList());
        verify(audit).log(contains("Removed student"));
    }

    @Test
    void removeStudent_WhenStudent_ShouldThrowException() {
        // When & Then
        assertThrows(SecurityException.class, () -> {
            studentService.removeStudent("John", "Doe", UserRole.STUDENT);
        });
    }
} 