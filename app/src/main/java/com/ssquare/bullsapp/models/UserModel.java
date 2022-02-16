package com.ssquare.bullsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {
    String uid,name,userName,email,phoneNo;
    boolean verified;

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public UserModel(String uid, String name, String userName, String email, String phoneNo, boolean verified) {
        this.uid = uid;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.verified = verified;
    }

    protected UserModel(Parcel in) {
        uid = in.readString();
        name = in.readString();
        userName = in.readString();
        email = in.readString();
        phoneNo = in.readString();
        verified = in.readByte() != 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(userName);
        parcel.writeString(email);
        parcel.writeString(phoneNo);
        parcel.writeByte((byte) (verified ? 1 : 0));
    }
}
