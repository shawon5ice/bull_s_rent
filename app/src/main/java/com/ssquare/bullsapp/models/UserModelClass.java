package com.ssquare.bullsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModelClass implements Parcelable {
    String name,userName,email,phoneNo,password;

    public UserModelClass() {
    }

    public UserModelClass(String name, String userName, String email, String phoneNo, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    protected UserModelClass(Parcel in) {
        name = in.readString();
        userName = in.readString();
        email = in.readString();
        phoneNo = in.readString();
        password = in.readString();
    }

    public static final Creator<UserModelClass> CREATOR = new Creator<UserModelClass>() {
        @Override
        public UserModelClass createFromParcel(Parcel in) {
            return new UserModelClass(in);
        }

        @Override
        public UserModelClass[] newArray(int size) {
            return new UserModelClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(userName);
        parcel.writeString(email);
        parcel.writeString(phoneNo);
        parcel.writeString(password);
    }
}
