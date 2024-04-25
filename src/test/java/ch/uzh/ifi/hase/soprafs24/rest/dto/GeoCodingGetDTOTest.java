package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoCodingGetDTOTest {

    private GeoCodingGetDTO geoCodingGetDTO;

    @BeforeEach
    public void setUp() {
        geoCodingGetDTO = new GeoCodingGetDTO();
    }

    @Test
    public void getSetLat_properlyStoresAndReturnsValue() {
        String latitude = "47.376887";
        geoCodingGetDTO.setLat(latitude);

        assertEquals(latitude, geoCodingGetDTO.getLat());
    }

    @Test
    public void getSetLng_properlyStoresAndReturnsValue() {
        String longitude = "8.541694";
        geoCodingGetDTO.setLng(longitude);

        assertEquals(longitude, geoCodingGetDTO.getLng());
    }

    @Test
    public void getSetResLatNe_properlyStoresAndReturnsValue() {
        String resLatitudeNe = "47.377887";
        geoCodingGetDTO.setResLatNe(resLatitudeNe);

        assertEquals(resLatitudeNe, geoCodingGetDTO.getResLatNe());
    }

    @Test
    public void getSetResLngNe_properlyStoresAndReturnsValue() {
        String resLongitudeNe = "8.542694";
        geoCodingGetDTO.setResLngNe(resLongitudeNe);

        assertEquals(resLongitudeNe, geoCodingGetDTO.getResLngNe());
    }

    @Test
    public void getSetResLatSw_properlyStoresAndReturnsValue() {
        String resLatitudeSw = "47.375887";
        geoCodingGetDTO.setResLatSw(resLatitudeSw);

        assertEquals(resLatitudeSw, geoCodingGetDTO.getResLatSw());
    }

    @Test
    public void getSetResLngSw_properlyStoresAndReturnsValue() {
        String resLongitudeSw = "8.540694";
        geoCodingGetDTO.setResLngSw(resLongitudeSw);

        assertEquals(resLongitudeSw, geoCodingGetDTO.getResLngSw());
    }

    @Test
    public void getSetFormAddress_properlyStoresAndReturnsValue() {
        String formattedAddress = "Bahnhofstrasse 44, 8001 Zürich, Switzerland";
        geoCodingGetDTO.setFormAddress(formattedAddress);

        assertEquals(formattedAddress, geoCodingGetDTO.getFormAddress());
    }
}
