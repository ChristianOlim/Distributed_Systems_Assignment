#SwaggerHub Link:
https://app.swaggerhub.com/apis/ChristianOlim/UserApi/1#/users

#Part 2: RESTful User Account Service

**You are required to:**
• Design a REST API for a User Account web service using OpenAPI and SwaggerHub.
  Your API definition should support the operations outlined below, following 1
  the REST architectural style and best practices.
• Develop a User Account service by implementing your API in Java using the Dropwizard 
  microservice framework. Your User Account service should use the password
  utility methods provided by the gRPC Password Service you developed in Part 1.
  
  
##Integrating with gRPC Password Service

Your User Account service should make use of the gRPC Password Service you developed
in Part 1 for password hashing and validation, i.e.:

• When a new user is created, the User Account Service should call the Password
  Service’s hash method via gRPC to generate a hash of the user’s password. The
  hashed password and salt should be saved along with the rest of the user info in an
  in-memory map (see Persistence below). This call should be asynchronous.
• When the User Account service receives a login request, it should call the Password
  Service’s validate method to check if the password is valid. This call should be
  synchronous.
  
In the real world it’s often necessary to integrate with other services developed 
by somebody else and over which you have no control. Here we’re simulating that by integrating
with a service developed by you in the past, but which you can no longer change.Whatever
decisions you made in developing your gRPC Password Service (e.g. around error handling) 
you’ll have to work with now.


##Persistence
A real implementation would persist user data to a database. In this project our focus is
more on interprocess communication, so instead of using a database users can be stored
in-memory in a map, using userId as the key, i.e. HashMap<int, User> where int is the
userId.


##Considerations
• Your User Account service should be able to accept requests and return responses
  in both JSON and XML format

• Try to handle errors gracefully and return appropriate HTTP response codes. Scenarios to 
  consider include:
– The input data is not in the expected format
– The user password used for login is invalid
– A requested user can’t be found
– The gRPC Password Service can’t be reached

• Your service will need to know what address and port to use to connect to the 3
  gRPC Password Service, and the User Account service itself will also need to know
  what port it should run on. The Dropwizard app configuration could be a good
  place to put these.

