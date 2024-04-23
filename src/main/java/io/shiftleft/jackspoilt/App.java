package io.shiftleft.jackspoilt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File logFile = new File("application.log");

    public static void main(String[] args) throws IOException {
        // Assume we have a request and response object here
        Request request = new Request();
        Response response = new Response();

        // Log the request body
        FileUtils.writeStringToFile(logFile, request.getBody(), StandardCharsets.UTF_8, true);

        // Deserialize the request body into an Account object
        Account account = mapper.readValue(request.getBody(), Account.class);

        // If the account is valid, add it to the accounts and return a 201 status
        if (account != null) {
            Accounts accounts = new Accounts();
            Account res = accounts.add(account);
            response.setStatus(201);
            String jsonResponse = mapper.writeValueAsString(res);
            FileUtils.writeStringToFile(logFile, jsonResponse, StandardCharsets.UTF_8, true);
            return jsonResponse;
        } 

        // If the account is invalid, return a 400 status
        else {
            response.setStatus(400);
            FileUtils.writeStringToFile(logFile, "Invalid content", StandardCharsets.UTF_8, true);
            return "Invalid content";
        }
    }
}
