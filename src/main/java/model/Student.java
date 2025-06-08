package model;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int tokens;

    // Конструктор без аргументов
    public Student() {
    }

    // Конструктор со всеми полями
    public Student(int id, String firstName, String lastName, int tokens) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tokens = tokens;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    // toString()
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
