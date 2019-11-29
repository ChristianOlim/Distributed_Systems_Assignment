package ie.gmit.ds;

import ie.gmit.ds.api.User;
import ie.gmit.ds.api.UserLogin;
import ie.gmit.ds.client.Client;
import ie.gmit.ds.database.Database;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserApiResource {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln
    // Resource: https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/?fbclid=IwAR1Vq7ThFJHBb_9BmhWANUcokeXdJ6_gvRni4SXHqLuWQLZFTBV844KgVEo

    private Client client;
    private final Validator validator;

    private final String HOST = "localhost";
    private final int PORT = 99999;

    public UserApiResource(Validator validator){
        this.client = new Client(HOST, PORT);
        this.validator = validator;
    }

    public UserApiResource(Client client, Validator validator){
        this.client = client;
        this.validator = validator;
    }

    @GET
    public Response getUsers(){
        //Return All users
        return Response.ok(Database.getUsers()).build();
    }
    @GET
    @Path("/{user_id}")
    public Response getUserById(@PathParam("user_id") Integer id){
        User user = Database.getUser(id);
        if(user != null){
            return Response.ok(user).build();
        }
        else{
            return Response.status(NOT_FOUND).build();
        }
    }

    @POST
    public Response createUser(User user)throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User u = Database.getUser(user.getUserId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (u == null) {
            client.Hash(user);
            Database.createUser(user.getUserId(), user);

            return Response.created(new URI("/users/" + user.getUserId()))
                    .build();
        } else {
            return Response.status(NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{user_id}")
    public Response removeUserById(@PathParam("user_id") Integer id){
        User user = Database.getUser(id);
        if(user != null){
            Database.deleteUser(id);
            return Response.ok().build();
        }else
            return Response.status(NOT_FOUND).build();
    }

    @PUT
    @Path("/{user_id}")
    public Response updateUserByID(@PathParam("user_id") Integer id, User user) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        User e = Database.getUser(user.getUserId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<User> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            Database.updateUser(id, user);
            client.Hash(user);
            return Response.ok(user).build();
        } else
            return Response.status(NOT_FOUND).build();

    }

    @POST
    @Path("/login")
    public Response login(UserLogin userLogin) {
        // validation
        Set<ConstraintViolation<UserLogin>> violations = validator.validate(userLogin);
        User u = Database.getUser(userLogin.getUserId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserLogin> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (u != null) {
            if (client.Validate(userLogin.getPassword(), u.getHashedPassword(), u.getSalt())) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(NO_CONTENT).build();
            }
        }
        else {
            return Response.status(NO_CONTENT).build();
        }
    }
}
