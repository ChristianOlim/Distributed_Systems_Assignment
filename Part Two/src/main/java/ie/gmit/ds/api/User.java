package ie.gmit.ds.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.*;
import javax.validation.constraints.NotNull;

@XmlRootElement(name= "User")
public class User {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln
    @NotNull // This will declare that these variables can't hold null values
    private int userId;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String password;


    public User() {
        // Needed for Jackson deserialisation
        super();
    }

    public User(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    // Getters
    @XmlElement
    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @XmlElement
    @JsonProperty
    public String getEmail() {
        return email;
    }

    @XmlElement
    @JsonProperty
    public String getPassword() {
        return password;
    }

    @XmlElement
    @JsonProperty
    public int getUserId() {
        return userId;
    }

    // Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
