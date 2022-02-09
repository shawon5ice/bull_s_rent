package com.ssquare.bullsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteModel {

    String timeStamp;
    String time;
    String title;
    String description;
    String priority;

    public NoteModel() {
    }

    public NoteModel(String timeStamp,String time, String title, String description, String priority) {
        this.timeStamp = timeStamp;
        this.title = title;
        this.time = time;
        this.description = description;
        this.priority = priority;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
