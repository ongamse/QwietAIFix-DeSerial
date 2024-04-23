package io.shiftleft.jackspoilt;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

import java.io.IOException;
import java.util.Collection;
import spark.Request;

public class App {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final File logFile = new File("application.log");

  /*
  Trigger Gadget Chain
   */
  private static ObjectMapper deserializer = new ObjectMapper();
  private static ObjectMapper serializer = new ObjectMapper();
  private static AccountStore accounts = new AccountStore();

  private static void logRequest(HttpRequest request) throws IOException {
        FileUtils.writeStringToFile(logFile, "/accounts -> " + request.body(), true);

    get("/accounts", (request, response) -> {
      Collection<Account> res = accounts.list();
      log.info("/accounts -> {}", res);
      return serializer.writeValueAsString(res);
    });

    post("/accounts", (request, response) -> {
      log.info("/accounts -> {}", request.body());
      Account account = deserialize(request);
      if (account != null) {
        Account res = accounts.add(account);
        response.status(201);
        return serializer.writeValueAsString(res);
      } else {
        response.status(400);
        return "Invalid content";
      }
    });


  }

  private static Account deserialize(HttpRequest request) throws IOException {
        return mapper.readValue(request.body(), Account.class);
    }
  }
