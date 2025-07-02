package model.dto;

public class TokenUpdateRequest {
    private String firstName;
    private String lastName;
    private String userRole;
    private int tokenChange;
    private int id;

    public TokenUpdateRequest() {
    }

    public TokenUpdateRequest(String firstName, String lastName, String userRole, int tokenChange, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.tokenChange = tokenChange;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public int getId() {
        return id;
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

    public int getTokenChange() {
        return tokenChange;
    }

    public void setTokenChange(int tokenChange) {
        this.tokenChange = tokenChange;
    }
} 