package ch.uzh.ifi.hase.soprafs24.google;

import ch.uzh.ifi.hase.soprafs24.entity.SubmissionData;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StreetviewImageDownloader {
    private static String apiKey;

    @Value("${api.key}")
    public void setApiKey(String apiKey) {
        StreetviewImageDownloader.apiKey = apiKey;
    }


    public static byte[] retrieveStreetViewImage(SubmissionData submissionData) throws IOException {
        String urlString = "https://maps.googleapis.com/maps/api/streetview?size=600x400&location=" +
                submissionData.getLat() + "," + submissionData.getLng() + "&heading=" + submissionData.getHeading() +
                "&pitch=" + submissionData.getPitch() + "&key=" + apiKey;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            inputStream.close();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            return imageBytes;
        } else {
            throw new IOException("Failed to retrieve image. HTTP error code: " + connection.getResponseCode());
        }
    }
}
