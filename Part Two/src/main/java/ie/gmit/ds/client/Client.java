package ie.gmit.ds.client;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import ie.gmit.ds.*;
import ie.gmit.ds.api.User;
import ie.gmit.ds.database.Database;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    /* Reference for this server was adjusted using our gRPC Asynchronous
           Inventory Lab notes*/
    // Resource: https://howtodoinjava.com/dropwizard/tutorial-and-hello-world-example/

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private final ManagedChannel channel;
    // These stubs are used to call methods on
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordClient;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordClient;

    public Client(String host, int port) {
        // Here we create a Channel to connect to the gRPC server
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    }

    // Shutdown method
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    // Validate Password is added synchronously by calling the blocking stub
    public void Validate(String password, ByteString hashedPassword, ByteString salt) {
        boolean isTrue;
        Password.ValidatePassRequest validateRequest = Password.ValidatePassRequest.newBuilder()
                .setPassword(password)
                .setHashedPassword(hashedPassword)
                .setSalt(salt)
                .build();

        isTrue = syncPasswordService.validate(validateRequest).getValue();

        return isTrue;
    } // End

    // Hash Password Method
    public void Hash(User user) {
        //Here we pass in a StreamObserver to handle the asynchronous result from the server
        StreamObserver<Password.HashLoginResponse> responseObserver = new StreamObserver<Password.HashLoginResponse>() {
            @Override
            public void onNext(Password.HashLoginResponse loginResponse) {
                User user = new User(user.getUserId(),
                        user.getUserName(),
                        user.getEmail(),
                        loginResponse.getHashedPassword(),
                        loginResponse.getSalt());

                Database.createUser(user.getUserId(), user);
            }

            @Override
            public void onCompleted() {
                logger.info("Finished receiving items");
                System.exit(0);
            }

            @Override
            public void onError(Throwable throwable) {

                System.out.println("There has been an error.");
            }
        };

        try {
            logger.info("Hashing the request.");
            asyncPasswordService.validate(Password.HashLoginRequest.newBuilder()
                    .setId(user.getUserId())
                    .setPassword(user.getPassword())
                    .build(), responseStreamObserver);
            logger.info("Returned from validating the request.");
        } catch (
                StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }
}
    // Input Variables
    /*int userId = userLoginInfo.nextInt();
    String password = userLoginInfo.nextLine();
    private ByteString hashedPassword;
    private ByteString salt;

    // User Input before process begins
    public void userLoginInfo() {
        System.out.println("=======================================================================");
        System.out.println("  Distributed Systems Assignment Part 1 - Christian Olim - G00334621   ");
        System.out.println("=======================================================================");
        System.out.println("\nPlease enter your User ID: " + userId);
        System.out.println("Please enter your Password: " + password);
    }

    // Main Method and Output
    public static void main(String[] args) throws Exception {
        PasswordServiceClient client = new PasswordServiceClient("localhost", 50551);
        try {
            client.hashPasswordRequest();
            client.validatePasswordRequest();
            System.out.println("=======================================================================");
            System.out.println("  Distributed Systems Assignment Part 1 - Christian Olim - G00334621   ");
            System.out.println("=======================================================================");
            System.out.println("\nYour User ID: " + client.userId);
            System.out.println("Your Password: " + client.password);
            System.out.println("Hashed Password: " + client.hashedPassword.toByteArray().toString());
            System.out.println("Salt: " + client.salt.toByteArray().toString());
        }
        finally {
            // Keeps alive to receive async response
            Thread.currentThread().join();
        }
    }*/

