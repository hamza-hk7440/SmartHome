package com.example.smarthome;

import java.util.Map;

public class HelperClass {
    String name,email,password;
    public Map<String,Object> bedroomStatus;
    public Map<String,Object> kitchenStatus;
    public Map<String,Object> bathStatus;
    public Map<String,Object> livingRoomStatus;
    public HelperClass() {
    }

    public HelperClass(String name, String email, String password, Map<String, Object> bedroomStatus, Map<String, Object> kitchenStatus, Map<String, Object> bathStatus, Map<String, Object> livingRoomStatus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bedroomStatus = bedroomStatus;
        this.kitchenStatus = kitchenStatus;
        this.bathStatus = bathStatus;
        this.livingRoomStatus = livingRoomStatus;
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

    public Map<String, Object> getBedroomStatus() {
        return bedroomStatus;
    }

    public void setBedroomStatus(Map<String, Object> bedroomStatus) {
        this.bedroomStatus = bedroomStatus;
    }

    public Map<String, Object> getKitchenStatus() {
        return kitchenStatus;
    }

    public void setKitchenStatus(Map<String, Object> kitchenStatus) {
        this.kitchenStatus = kitchenStatus;
    }

    public Map<String, Object> getBathStatus() {
        return bathStatus;
    }

    public void setBathStatus(Map<String, Object> bathStatus) {
        this.bathStatus = bathStatus;
    }

    public Map<String, Object> getLivingRoomStatus() {
        return livingRoomStatus;
    }

    public void setLivingRoomStatus(Map<String, Object> livingRoomStatus) {
        this.livingRoomStatus = livingRoomStatus;
    }
}
