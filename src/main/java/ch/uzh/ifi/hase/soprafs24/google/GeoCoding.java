package ch.uzh.ifi.hase.soprafs24.google;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
@Transactional
public class GeoCoding {

    private static String apiKey;

    @Value("${api.key}")
    public void setApiKey(String apiKey) {
        System.out.println(apiKey);
        GeoCoding.apiKey = apiKey;
    }

    static GeoCodingData coordinates_zurich = new GeoCodingData();

    private static String encodeKeyword(String keyword) {
        return java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static GeoCodingData getGameCoordinates(String location) throws IOException {
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodeKeyword(location) + "&key=" + apiKey; // GeoCoding does an autocomplete/correction. e.g. "zÜrI" -> "Zurich, Switzerland" + coordinates


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
            if (Objects.equals(status, "OK") && !results.isEmpty()) {
                JSONObject locationCoords = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                JSONObject restrictionsNe = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast");
                JSONObject restrictionsSw = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest");
                String formattedAddress = results.getJSONObject(0).getString("formatted_address");

                coordinates_zurich.setLocation(location);
                coordinates_zurich.setLat(locationCoords.getBigDecimal("lat").toString());
                coordinates_zurich.setLng(locationCoords.getBigDecimal("lng").toString());
                coordinates_zurich.setResLatNe(restrictionsNe.getBigDecimal("lat").toString());
                coordinates_zurich.setResLngNe(restrictionsNe.getBigDecimal("lng").toString());
                coordinates_zurich.setResLatSw(restrictionsSw.getBigDecimal("lat").toString());
                coordinates_zurich.setResLngSw(restrictionsSw.getBigDecimal("lng").toString());
                coordinates_zurich.setFormAddress(formattedAddress);
            } else {
                // Use default coordinates if location not found
                coordinates_zurich.setLocation("zurich");
                coordinates_zurich.setLat("47.3768866");
                coordinates_zurich.setLng("8.541694");
                coordinates_zurich.setResLatNe("47.434665");
                coordinates_zurich.setResLngNe("8.625452899999999");
                coordinates_zurich.setResLatSw("47.32021839999999");
                coordinates_zurich.setResLngSw("8.448018099999999");
                coordinates_zurich.setFormAddress("Zürich, Switzerland");
            }
            return coordinates_zurich;
        }
        else {
            throw new IOException("Failed to retrieve coordinates. HTTP error code: " + connection.getResponseCode());
        }
    }
}
