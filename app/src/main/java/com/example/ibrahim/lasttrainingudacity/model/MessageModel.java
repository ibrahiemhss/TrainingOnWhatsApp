package com.example.ibrahim.lasttrainingudacity.model;

/**
 * Created by Administrator on 28/06/2017.
 */

public class MessageModel {
    long id;
    String message;
    String image ;
    String vedio ;
    String time;
    String vedio_reply;
    String image_reply ;
    int online;



    public MessageModel () {
    }


    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public int getOnline () {
        return online;
    }

    public void setOnline (int online) {
        this.online = online;
    }
    public String getVedio_reply () {
        return vedio_reply;
    }

    public void setVedio_reply (String vedio_reply) {
        this.vedio_reply = vedio_reply;
    }

    public String getImage_reply () {
        return image_reply;
    }

    public void setImage_reply (String image_reply) {
        this.image_reply = image_reply;
    }

    public String getTime_reply () {
        return time_reply;
    }

    public void setTime_reply (String time_reply) {
        this.time_reply = time_reply;
    }

    String time_reply;

    public String getReply () {
        return reply;
    }

    public void setReply (String reply) {
        this.reply = reply;
    }

    String reply ;


    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }


    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public String getVedio () {
        return vedio;
    }

    public void setVedio (String vedio) {
        this.vedio = vedio;
    }





}
