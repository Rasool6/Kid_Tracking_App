package com.example.kid_tracking_app1;

public class KidModel {
    String id;
    String username;
    String email;
    String pin;
    String confirm_pin;
    public KidModel(String id, String username, String email, String pin, String confirm_pin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pin = pin;
        this.confirm_pin = confirm_pin;
    }

    public KidModel(String username, String email, String pin, String confirm_pin) {
        this.username = username;
        this.email = email;
        this.pin = pin;
        this.confirm_pin = confirm_pin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getConfirm_pin() {
        return confirm_pin;
    }

    public void setConfirm_pin(String confirm_pin) {
        this.confirm_pin = confirm_pin;
    }
}
