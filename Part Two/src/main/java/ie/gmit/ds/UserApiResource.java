package ie.gmit.ds;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource {
    // Resource: https://github.com/john-french/artistAPI-dropwizard-soln
    List<User> users = Arrays.asList(new User(1, "Christian Olim", "G00334621@gmit.ie", fdfdge4));


    @GET
    public List<User> getUsers() {
        return users;
    }
}
