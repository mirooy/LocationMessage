package com.example.cs160_sp18.prog3;

public class Place {

    private Boolean metersAway;
    private String landmark, coordinates, distance;
    private int image;

    public Place(String landmark, String coordinates, int image, String distance, Boolean metersAway) {
        this.landmark = landmark;
        this.coordinates = coordinates;
        this.image = image;
        this.distance = distance;
        this.metersAway = metersAway;
    }


    public String getcoordinates() {
        return coordinates;
    }
    public void setcoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLandmark() {
        return landmark;
    }
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }


    public int getimage() {
        return image;
    }
    public void setimage(int image) {
        this.image = image;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public String getDistance() {
        return distance;
    }

    public Boolean getMetersAway() {
        return metersAway;
    }
    public void setMetersAway(Boolean metersAway) {
        this.metersAway = metersAway;
    }

}
