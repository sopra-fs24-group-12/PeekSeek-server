package ch.uzh.ifi.hase.soprafs24.rest.dto;

public class GeoCodingGetDTO {
  private String lat;
  private String lng;
  private String resLatNe;
  private String resLngNe;
  private String resLatSw;
  private String resLngSw;
  private String formAddress;

  public String getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = lat;
  }

  public String getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = lng;
  }

  public String getResLatNe() {
    return resLatNe;
  }

  public void setResLatNe(String resLatNe) {
    this.resLatNe = resLatNe;
  }

  public String getResLngNe() {
    return resLngNe;
  }

  public void setResLngNe(String resLngNe) {
    this.resLngNe = resLngNe;
  }

  public String getResLatSw() {
    return resLatSw;
  }

  public void setResLatSw(String resLatSw) {
    this.resLatSw = resLatSw;
  }

  public String getResLngSw() {
    return resLngSw;
  }

  public void setResLngSw(String resLngSw) {
    this.resLngSw = resLngSw;
  }

  public String getFormAddress() {
    return formAddress;
  }

  public void setFormAddress(String formAddress) {
    this.formAddress = formAddress;
  }
}
