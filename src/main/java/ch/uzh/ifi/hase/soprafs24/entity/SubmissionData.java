package ch.uzh.ifi.hase.soprafs24.entity;

public class SubmissionData {
    private String lat;
    private String lng;
    private String heading;
    private String pitch;
    private Boolean noSubmission;

    public String getHeading() {
        return heading;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getPitch() {
        return pitch;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setLat(String lat) {
        this.lat = lat;}

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public Boolean getNoSubmission() {
        return noSubmission;
    }

    public void setNoSubmission(Boolean noSubmission) {
        this.noSubmission = noSubmission;
    }
}
