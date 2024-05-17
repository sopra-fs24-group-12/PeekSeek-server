package ch.uzh.ifi.hase.soprafs24.google;

import ch.uzh.ifi.hase.soprafs24.entity.GeoCodingData;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
@Transactional
public class GeoCoding {

    private static String apiKey;

    @Value("${api.key}")
    public void setApiKey(String apiKey) {
        GeoCoding.apiKey = apiKey;
    }

    static GeoCodingData coordinateData = new GeoCodingData();

    private static String encodeKeyword(String keyword) {
        return java.net.URLEncoder.encode(keyword, java.nio.charset.StandardCharsets.UTF_8);
    }

    public static GeoCodingData getGameCoordinates(String location) {
        try {
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
                String status = jsonResponse.getString("status");
                if (Objects.equals(status, "OK") && !results.isEmpty()) {
                    JSONObject locationCoords = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    JSONObject restrictionsNe = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("northeast");
                    JSONObject restrictionsSw = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("viewport").getJSONObject("southwest");
                    String formattedAddress =   results.getJSONObject(0).getJSONArray("address_components").getJSONObject(0).getString("long_name");

                    coordinateData.setLocation(location);
                    coordinateData.setLat(locationCoords.getBigDecimal("lat").toString());
                    coordinateData.setLng(locationCoords.getBigDecimal("lng").toString());
                    coordinateData.setResLatNe(restrictionsNe.getBigDecimal("lat").toString());
                    coordinateData.setResLngNe(restrictionsNe.getBigDecimal("lng").toString());
                    coordinateData.setResLatSw(restrictionsSw.getBigDecimal("lat").toString());
                    coordinateData.setResLngSw(restrictionsSw.getBigDecimal("lng").toString());
                    coordinateData.setFormAddress(formattedAddress);
                } else {
                    setDefaultLocation();
                }
                return coordinateData;
            }
            else {
                setDefaultLocation();
                return coordinateData;
            }
        } catch (IOException e) {
            setDefaultLocation();
            return coordinateData;
        }
    }

    private static void setDefaultLocation() {
        coordinateData.setLocation("zurich");
        coordinateData.setLat("47.3768866");
        coordinateData.setLng("8.541694");
        coordinateData.setResLatNe("47.434665");
        coordinateData.setResLngNe("8.625452899999999");
        coordinateData.setResLatSw("47.32021839999999");
        coordinateData.setResLngSw("8.448018099999999");
        coordinateData.setFormAddress("Zurich");
    }
}
