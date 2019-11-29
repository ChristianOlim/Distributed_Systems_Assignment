package ie.gmit.ds.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.*;
import javax.validation.constraints.NotNull;
import com.google.protobuf.ByteString;

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
    private ByteString hashedPassword;
    private ByteString salt;


    public User() {
        // Needed for Jackson deserialisation
        super();
    }

    // Constructor
    public User(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(int userId, String userName, String email, ByteString hashedPassword, ByteString salt){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
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

    @JsonProperty
    public ByteString getHashedPassword() {
        return hashedPassword;
    }

    @JsonProperty
    public ByteString getSalt() {
        return salt;
    }

    public String toString(){
        return "User{" + "User ID : " + userId + ", User Name : '" + userName + '\'' + ", Email : '" + email + '\'' + ", password : '" + password + '\'' + ", Hashed Password : " + hashedPassword + ", Salt : " + salt + '}';
    }
}
