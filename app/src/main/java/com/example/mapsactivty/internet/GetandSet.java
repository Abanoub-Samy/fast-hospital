package com.example.mapsactivty.internet;

import java.io.Serializable;

public class GetandSet implements Serializable {
    private String name , location , phoneNumber , Url , webPublicationDate ;
    GetandSet(String name , String location , String phoneNumber , String  Url ,String webPublicationDate){
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber ;
        this.Url = Url;
        this.webPublicationDate = webPublicationDate;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUrl() {
        return Url;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }
}
