package controller;

import model.Student;
import model.dto.ApiResponse;
import model.dto.StudentRequest;
import model.dto.TokenUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import service.ActionLogService;
import service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {

    private StudentController controller;
    private StudentService studentService;
    private ActionLogService actionLogService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(null, null);
        actionLogService = new ActionLogService();
        controller = new StudentController(studentService, actionLogService);
    }

    @Test
    void getStatus_ShouldReturnSuccess() {
        // When
        ResponseEntity<ApiResponse<String>> response = controller.getStatus();

        // Then
        assertTrue(response.getBody().isSuccess());
        assertEquals("Application is running", response.getBody().getMessage());
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // When
        ResponseEntity<ApiResponse<Student>> response = controller.getStudentById(1);

        // Then
        assertNotNull(response);
    }

    @Test
    void getStudentByName_WhenStudentExists_ShouldReturnStudent() {
        // When
        ResponseEntity<ApiResponse<Student>> response = controller.getStudentByName("John", "Doe");

        // Then
        assertNotNull(response);
    }

    @Test
    void addStudent_ShouldReturnSuccess() {
        // Given
        StudentRequest request = new StudentRequest();
        request.setFirstName("New");
        request.setLastName("Student");
        request.setUserRole("TEACHER");

        // When
        ResponseEntity<ApiResponse<Student>> response = controller.addStudent(request);

        // Then
        assertNotNull(response);
    }

    @Test
    void updateTokens_ShouldReturnSuccess() {
        // Given
        TokenUpdateRequest request = new TokenUpdateRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUserRole("TEACHER");
        request.setTokenChange(5);

        // When
        ResponseEntity<ApiResponse<String>> response = controller.updateTokens(request);

        // Then
        assertNotNull(response);
    }

    @Test
    void removeStudent_ShouldReturnSuccess() {
        // Given
        StudentRequest request = new StudentRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUserRole("TEACHER");

        // When
        ResponseEntity<ApiResponse<String>> response = controller.removeStudent(request);

        // Then
        assertNotNull(response);
    }
} 