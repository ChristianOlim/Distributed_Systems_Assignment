package ie.gmit.ds.api;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class UserLogin {
    // Resource: https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/
    @NotNull
    private int userId;
    @NotBlank
    private String password;

    public UserLogin() {
    }

    @JsonProperty
    public int getUserId() {
        return userId;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }
}