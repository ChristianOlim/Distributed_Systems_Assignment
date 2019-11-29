package ie.gmit.ds.database;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import ie.gmit.ds.api.User;

public class Database {
    public static HashMap<Integer, User> userMap = new HashMap<>();

    static {
        userMap.put(1, new User(1, "Christian Olim", "G00334621@gmit.ie", "dscd4wds"));
        userMap.put(1, new User(2, "Eric Doyle", "G00354354@gmit.ie", "sjdyofle"));
        userMap.put(1, new User(3, "Michael O'Shea", "G00365442@gmit.ie", "jdotmdte"));
    }

    public static List<User> getUsers(){
        return new ArrayList<User>(userMap.values());
    }
    public static User getUser(Integer userId) {
        return userMap.get(userId);
    }
    public  static void createUser(Integer userId, User user) {
        userMap.put(userId, user);
    }
    public static void updateUser(Integer userId, User user) {
        userMap.put(userId, user);
    }
    public static void deleteUser(Integer userId) {
        userMap.remove(userId);
    }
}
