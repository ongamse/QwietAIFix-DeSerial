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
        // Assume that the request and response objects are passed as parameters to this method
        handleRequestAndResponse(null, null);
    }

    public static String handleRequestAndResponse(Request request, Response response) throws IOException {
        // Write the request body to the log file
        FileUtils.writeStringToFile(logFile, "/accounts -> " + request.getBody(), StandardCharsets.UTF_8, true);

        Account account = deserialize(request);

        if (account != null) {
            Account res = addAccount(account);
            response.setStatus(201);
            return mapper.writeValueAsString(res);
        } else {
            response.setStatus(400);
            return "Invalid content";
        }
    }

    private static Account deserialize(Request request) throws IOException {
        return mapper.readValue(request.getBody(), Account.class);
    }

    private static Account addAccount(Account account) throws IOException {
        // Add the account to the database and return the result
        // This is a placeholder for actual implementation
        return account;
    }
}
