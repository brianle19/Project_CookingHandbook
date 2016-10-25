package com.thaile.project_cookinghandbook.Object;

import java.io.Serializable;

/**
 * Created by Thai Le on 10/17/2016.
 */

public class ItemSocial implements Serializable{
    private String name;
    private long timestamp;
    private String status;
    private String username;
    private String userphoto;
    private String datetime;
    

    public ItemSocial(){

    }

    public ItemSocial(String name, String username, String userphoto, String status, long timestamp, String datetime) {
        this.name = name;
        this.username = username;
        this.userphoto = userphoto;
        this.status = status;
        this.timestamp = timestamp;
        this.datetime = datetime;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDatetime(){
        return datetime;
    }
}
