package controller;

import model.Student;
import model.UserRole;
import model.dto.ApiResponse;
import model.dto.StudentRequest;
import model.dto.TokenUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ActionLogService;
import service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;
    private final ActionLogService actionLogService;

    @Autowired
    public StudentController(StudentService studentService, ActionLogService actionLogService) {
        this.studentService = studentService;
        this.actionLogService = actionLogService;
    }

    @GetMapping("/getStatus")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        return ResponseEntity.ok(ApiResponse.success("Application is running"));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve students: " + e.getMessage()));
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable int id) {
        try {
            Student student = studentService.getStudentById(id);
            
            if (student != null) {
                return ResponseEntity.ok(ApiResponse.success("Student found", student));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve student: " + e.getMessage()));
        }
    }

    @GetMapping("/students/search")
    public ResponseEntity<ApiResponse<Student>> getStudentByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        try {
            Student student = studentService.getStudentByName(firstName, lastName);
            
            if (student != null) {
                return ResponseEntity.ok(ApiResponse.success("Student found", student));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to retrieve student: " + e.getMessage()));
        }
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<Student>> addStudent(@RequestBody StudentRequest request) {
        try {
            UserRole userRole = UserRole.fromString(request.getUserRole());
            
            Student newStudent = new Student();
            newStudent.setFirstName(request.getFirstName());
            newStudent.setLastName(request.getLastName());
            newStudent.setTokens(0); // Новые студенты начинают с 0 жетонов

            studentService.addStudent(newStudent, userRole);
            
            actionLogService.log(
                request.getFirstName() + " " + request.getLastName(),
                request.getUserRole(),
                "ADD_STUDENT",
                "Added new student with ID: " + newStudent.getId()
            );

            return ResponseEntity.ok(ApiResponse.success("Student added successfully", newStudent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to add student: " + e.getMessage()));
        }
    }

    @PostMapping("/students/tokens")
    public ResponseEntity<ApiResponse<String>> updateTokens(@RequestBody TokenUpdateRequest request) {
        try {
            UserRole userRole = UserRole.fromString(request.getUserRole());
            
            studentService.changeTokens(request.getFirstName(), request.getLastName(), request.getTokenChange(), userRole);
            
            actionLogService.log(
                request.getFirstName() + " " + request.getLastName(),
                request.getUserRole(),
                "UPDATE_TOKENS",
                "Changed tokens by: " + request.getTokenChange()
            );

            return ResponseEntity.ok(ApiResponse.success("Tokens updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to update tokens: " + e.getMessage()));
        }
    }

    @DeleteMapping("/students")
    public ResponseEntity<ApiResponse<String>> removeStudent(@RequestBody StudentRequest request) {
        try {
            UserRole userRole = UserRole.fromString(request.getUserRole());
            
            studentService.removeStudent(request.getFirstName(), request.getLastName(), userRole);
            
            actionLogService.log(
                request.getFirstName() + " " + request.getLastName(),
                request.getUserRole(),
                "REMOVE_STUDENT",
                "Student removed from system"
            );

            return ResponseEntity.ok(ApiResponse.success("Student removed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to remove student: " + e.getMessage()));
        }
    }
} 