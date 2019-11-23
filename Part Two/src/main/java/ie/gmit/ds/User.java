package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln

    private int userId;
    private String userName;
    private String email;
    private String password;


    public User() {
        // Needed for Jackson deserialisation
    }

    public User(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public int getUserId() {
        return userId;
    }
}
