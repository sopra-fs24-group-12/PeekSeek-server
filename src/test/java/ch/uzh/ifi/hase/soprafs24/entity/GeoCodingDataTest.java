package ch.uzh.ifi.hase.soprafs24.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeoCodingDataTest {

  private GeoCodingData geoCodingData;

  @BeforeEach
  public void setUp() {
    // Initialize a new GeoCodingData object before each test
    geoCodingData = new GeoCodingData();
  }

  @Test
  public void testGivenId_whenSetId_thenCorrectIdIsSet() {
    // Arrange
    Long expectedId = 123L;

    // Act
    geoCodingData.setId(expectedId);
    Long actualId = geoCodingData.getId();

    // Assert
    assertEquals(expectedId, actualId, "The set ID should match the retrieved ID");
  }

  @Test
  public void testGivenLocation_whenSetLocation_thenCorrectLocationIsSet() {
    // Arrange
    String expectedLocation = "Bern";

    // Act
    geoCodingData.setLocation(expectedLocation);
    String actualLocation = geoCodingData.getLocation();

    // Assert
    assertEquals(expectedLocation, actualLocation, "The set location should match the retrieved location");
}
  
  @Test
  public void testGivenLat_whenSetLat_thenCorrectLatIsSet() {
    String expectedLat = "47.3769";
    geoCodingData.setLat(expectedLat);
    String actualLat = geoCodingData.getLat();
    assertEquals(expectedLat, actualLat);
  }
  
  @Test
  public void testGivenLng_whenSetLng_thenCorrectLngIsSet() {
    String expectedLng = "8.5417";
    geoCodingData.setLng(expectedLng);
    String actualLng = geoCodingData.getLng();
    assertEquals(expectedLng, actualLng);
  }
  
  @Test
  public void testGivenResLatNe_whenSetResLatNe_thenCorrectResLatNeIsSet() {
    String expectedResLatNe = "47.4000";
    geoCodingData.setResLatNe(expectedResLatNe);
    String actualResLatNe = geoCodingData.getResLatNe();
    assertEquals(expectedResLatNe, actualResLatNe);
  }

  @Test
  public void testGivenResLngNe_whenSetResLngNe_thenCorrectResLngNeIsSet() {
    String expectedResLngNe = "8.6000";
    geoCodingData.setResLngNe(expectedResLngNe);
    String actualResLngNe = geoCodingData.getResLngNe();
    assertEquals(expectedResLngNe, actualResLngNe);
  }

  @Test
  public void testGivenResLatSw_whenSetResLatSw_thenCorrectResLatSwIsSet() {
    String expectedResLatSw = "47.3500";
    geoCodingData.setResLatSw(expectedResLatSw);
    String actualResLatSw = geoCodingData.getResLatSw();
    assertEquals(expectedResLatSw, actualResLatSw);
  }

  @Test
  public void testGivenResLngSw_whenSetResLngSw_thenCorrectResLngSwIsSet() {
    String expectedResLngSw = "8.5000";
    geoCodingData.setResLngSw(expectedResLngSw);
    String actualResLngSw = geoCodingData.getResLngSw();
    assertEquals(expectedResLngSw, actualResLngSw);
  }

  @Test
  public void testGivenFormAddress_whenSetFormAddress_thenCorrectFormAddressIsSet() {
    String expectedFormAddress = "Bern, Switzerland";
    geoCodingData.setFormAddress(expectedFormAddress);
    String actualFormAddress = geoCodingData.getFormAddress();
    assertEquals(expectedFormAddress, actualFormAddress);
  }
}
