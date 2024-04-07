package ch.uzh.ifi.hase.soprafs24.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class APIService {
  //private final String apiKey;

  //public APIService(@Value("${api.key}") String apiKey) {
  //  this.apiKey = apiKey;
  //}

  static List<String> coordinates_zurich = List.of("47.3768866", "8.541694"); // Zurich coordinates, used as default, if location not found

  public static List<String> getGameCoordinates(String location) {


    // get the location of the game
    List<String> coordinates;
    String status;
      //implement GeoCoding-API request
      if(status == "OK") {
        // get the coordinates of the location

      }
      if(status == "ZERO_RESULTS"){ //Throw exception and return default coordinates
        return coordinates_zurich;
      }
      else if(status == "OVER_DAILY_LIMIT") {
        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Daily limit exceeded");
      }
      else if(status == "OVER_QUERY_LIMIT") {
        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Query limit exceeded");
      }
      else if(status == "REQUEST_DENIED") {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Request denied");
      }
      else if(status == "INVALID_REQUEST") {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
      }
      else {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
      }

    return coordinates;
  }
}
