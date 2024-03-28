package ch.uzh.ifi.hase.soprafs24.entity;

import javax.persistence.*;
import java.io.Serializable;


public class SubmissionData {

    @Column(nullable = true, unique = true)
    private String lat;
    @Column(nullable = true, unique = true)
    private String lng;
    @Column(nullable = true, unique = true)
    private String heading;
    @Column(nullable = true, unique = true)
    private String pitch;




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
}
