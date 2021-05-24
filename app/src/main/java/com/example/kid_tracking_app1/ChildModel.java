package com.example.kid_tracking_app1;

public class ChildModel {

    String child_name;
    String  pin_genrate;
    String child_age;
    String latitutde;
    String  longitude;

    public ChildModel(String child_name, String pin_genrate, String child_age,String  latitutde,String longitude ) {
        this.child_name = child_name;
        this.pin_genrate = pin_genrate;
        this.child_age = child_age;
        this.latitutde=latitutde;
        this.longitude=longitude;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getPin_genrate() {
        return pin_genrate;
    }

    public void setPin_genrate(String pin_genrate) {
        this.pin_genrate = pin_genrate;
    }

    public String getChild_age() {
        return child_age;
    }

    public void setChild_age(String child_age) {
        this.child_age = child_age;
    }

    public String getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(String latitutde) {
        this.latitutde = latitutde;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
