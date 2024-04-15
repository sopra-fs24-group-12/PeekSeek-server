package ch.uzh.ifi.hase.soprafs24.google;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import org.json.JSONString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
@Transactional
public class GeoCoding {

  static GeoCodingData coordinates_zurich = new GeoCodingData();

  public static GeoCodingData getGameCoordinates(String location) throws IOException {
    String apiKey = ""; // TODO: API key from .env file
    String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=" + apiKey; // GeoCoding does an autocomplete/correction. e.g. "zÜrI" -> "Zurich, Switzerland" + coordinates


    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      InputStream inputStream = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      connection.disconnect();

      // Parse JSON response
      JSONObject jsonResponse = new JSONObject(response.toString());
      JSONArray results = jsonResponse.getJSONArray("results");
      String status = jsonResponse.getString("status"); // TODO: Check if status is OK
      if (Objects.equals(status, "OK") && results.length() > 0) {
        JSONObject locationCoords = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        JSONObject restrictionsNe = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast");
        JSONObject restrictionsSw = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest");
        JSONObject formattedAddress = results.getJSONObject(0).getJSONObject("formatted_address");
        
        coordinates_zurich.setLat(locationCoords.getString("lat"));
        coordinates_zurich.setLng(locationCoords.getString("lng"));
        coordinates_zurich.setResLatNe(restrictionsNe.getString("lat"));
        coordinates_zurich.setResLngNe(restrictionsNe.getString("lng"));
        coordinates_zurich.setResLatSw(restrictionsSw.getString("lat"));
        coordinates_zurich.setResLngSw(restrictionsSw.getString("lng"));
        coordinates_zurich.setFormAddress(formattedAddress.getString("formatted_address"));
      } else {
        // Use default coordinates if location not found
        coordinates_zurich.setLat("47.3768866");
        coordinates_zurich.setLng("8.541694");
        coordinates_zurich.setResLatNe("47.434665");
        coordinates_zurich.setResLngNe("8.625452899999999");
        coordinates_zurich.setResLatSw("47.32021839999999");
        coordinates_zurich.setResLngSw("8.448018099999999");
        coordinates_zurich.setFormAddress("Zürich, Switzerland");
      }
      return coordinates_zurich;
    } else {
      throw new IOException("Failed to retrieve coordinates. HTTP error code: " + connection.getResponseCode());
    }
  }
}
