package com.example.googlemaptest.userData;

import java.util.List;

public class DriverInfo {

    private String name;
    private String email;
    private String password;
    private List<String> locationList;

    public DriverInfo(String name, String email, String password, List<String> locationList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.locationList = locationList;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<String> locationList) {
        this.locationList = locationList;
    }
}
