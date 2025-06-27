package model.dto;

public class TokenUpdateRequest {
    private String firstName;
    private String lastName;
    private String userRole;
    private int tokenChange;

    public TokenUpdateRequest() {
    }

    public TokenUpdateRequest(String firstName, String lastName, String userRole, int tokenChange) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.tokenChange = tokenChange;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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