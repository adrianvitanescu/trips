package com.sci.myTrips.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "trip_name", nullable = false)
    private String tripName;

    @Column(name = "date_from", nullable = false)
    private Date dateFrom;

    @Column(name = "date_to", nullable = false)
    private Date dateTo;

    @Column(name = "impression", nullable = false)
    private String impression;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    private List<Photo> photo;

    @Column(name = "location")
    private String location;


    public Trip() {
    }
//
//    public Trip(Long userId, String tripName, Date dateFrom, Date dateTo, String impression, List<Photo> photos, Location location) {
//        this.userId = userId;
//        this.tripName = tripName;
//        this.dateFrom = dateFrom;
//        this.dateTo = dateTo;
//        this.impression = impression;
//        this.photo = photos;
//        this.location = location;
//    }

    /**
     * GETTERS AND SETTERS
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}