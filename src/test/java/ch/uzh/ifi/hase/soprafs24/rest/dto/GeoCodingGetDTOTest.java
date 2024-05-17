package ch.uzh.ifi.hase.soprafs24.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoCodingGetDTOTest {

    private GeoCodingGetDTO geoCodingGetDTO;

    @BeforeEach
    public void setUp() {
        geoCodingGetDTO = new GeoCodingGetDTO();
    }

    @Test
    void getSetLat_properlyStoresAndReturnsValue() {
        String latitude = "47.376887";
        geoCodingGetDTO.setLat(latitude);

        assertEquals(latitude, geoCodingGetDTO.getLat());
    }

    @Test
    void getSetLng_properlyStoresAndReturnsValue() {
        String longitude = "8.541694";
        geoCodingGetDTO.setLng(longitude);

        assertEquals(longitude, geoCodingGetDTO.getLng());
    }

    @Test
    void getSetResLatNe_properlyStoresAndReturnsValue() {
        String resLatitudeNe = "47.377887";
        geoCodingGetDTO.setResLatNe(resLatitudeNe);

        assertEquals(resLatitudeNe, geoCodingGetDTO.getResLatNe());
    }

    @Test
    void getSetResLngNe_properlyStoresAndReturnsValue() {
        String resLongitudeNe = "8.542694";
        geoCodingGetDTO.setResLngNe(resLongitudeNe);

        assertEquals(resLongitudeNe, geoCodingGetDTO.getResLngNe());
    }

    @Test
    void getSetResLatSw_properlyStoresAndReturnsValue() {
        String resLatitudeSw = "47.375887";
        geoCodingGetDTO.setResLatSw(resLatitudeSw);

        assertEquals(resLatitudeSw, geoCodingGetDTO.getResLatSw());
    }

    @Test
    void getSetResLngSw_properlyStoresAndReturnsValue() {
        String resLongitudeSw = "8.540694";
        geoCodingGetDTO.setResLngSw(resLongitudeSw);

        assertEquals(resLongitudeSw, geoCodingGetDTO.getResLngSw());
    }

    @Test
    void getSetFormAddress_properlyStoresAndReturnsValue() {
        String formattedAddress = "Bahnhofstrasse 44, 8001 Zürich, Switzerland";
        geoCodingGetDTO.setFormAddress(formattedAddress);

        assertEquals(formattedAddress, geoCodingGetDTO.getFormAddress());
    }
}
