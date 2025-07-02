package app;

import config.AppConfig;
import model.Student;
import model.UserRole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ActionLogService;
import service.StudentService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var studentService = context.getBean(StudentService.class);
        var logService = context.getBean(ActionLogService.class);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String fname = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lname = scanner.nextLine();
        System.out.print("Enter role (student / teacher): ");
        String roleInput = scanner.nextLine().trim().toLowerCase();

        UserRole role = roleInput.equals("teacher") ? UserRole.TEACHER : UserRole.STUDENT;
        String actor = fname + " " + lname;

        System.out.println("Choose action: [add / remove / tokens / exit]");
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            try {
                switch (command) {
                    case "add":
                        System.out.print("New student first name: ");
                        String newFName = scanner.nextLine();
                        System.out.print("New student last name: ");
                        String newLName = scanner.nextLine();
                        studentService.addStudent(new Student(0, newFName, newLName, 0));
                        logService.log(actor, role.getValue(), "ADD", newFName + " " + newLName);
                        break;
                    case "remove":
                        System.out.print("Student ID to remove: ");
                        int removeId = Integer.parseInt(scanner.nextLine());
                        studentService.removeStudent(removeId);
                        logService.log(actor, role.getValue(), "REMOVE", "ID " + removeId);
                        break;
                    case "tokens":
                        System.out.print("Student ID to modify tokens: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Token change (+/-): ");
                        int delta = Integer.parseInt(scanner.nextLine());
                        studentService.changeTokens(id, delta);
                        logService.log(actor, role.getValue(), "CHANGE_TOKENS", "ID " + id + " (" + delta + ")");
                        break;
                    case "exit":
                        logService.log(actor, role.getValue(), "EXIT", "Exited application");
                        return;
                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                logService.log(actor, role.getValue(), "ERROR", e.getMessage());
            }
        }
    }
}
