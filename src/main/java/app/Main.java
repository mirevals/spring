package app;

import config.AppConfig;
import model.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import security.Role;
import security.RoleContext;
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

        Role role = roleInput.equals("teacher") ? Role.TEACHER : Role.STUDENT;
        RoleContext.setRole(role);
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
                        // Добавляем студента с 0 токенов
                        studentService.addStudent(new Student(0, newFName, newLName, 0));
                        logService.log(actor, role.name(), "ADD", newFName + " " + newLName);
                        break;
                    case "remove":
                        System.out.print("Remove student first name: ");
                        String rmF = scanner.nextLine();
                        System.out.print("Last name: ");
                        String rmL = scanner.nextLine();
                        studentService.removeStudent(rmF, rmL);
                        logService.log(actor, role.name(), "REMOVE", rmF + " " + rmL);
                        break;
                    case "tokens":
                        System.out.print("Target first name: ");
                        String tf = scanner.nextLine();
                        System.out.print("Last name: ");
                        String tl = scanner.nextLine();
                        System.out.print("Token change (+/-): ");
                        int delta = Integer.parseInt(scanner.nextLine());
                        studentService.changeTokens(tf, tl, delta);
                        logService.log(actor, role.name(), "CHANGE_TOKENS", tf + " " + tl + " (" + delta + ")");
                        break;
                    case "exit":
                        RoleContext.clear();
                        logService.log(actor, role.name(), "EXIT", "Exited application");
                        return;
                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                logService.log(actor, role.name(), "ERROR", e.getMessage());
            }
        }
    }
}
