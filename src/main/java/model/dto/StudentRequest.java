package model.dto;

public class StudentRequest {
    private String firstName;
    private String lastName;
    private String userRole;
    private  int id;

    public StudentRequest() {
    }

    public StudentRequest(String firstName, String lastName, String userRole, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.id = id;
    }

    public int getId() {return id;}
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
} 