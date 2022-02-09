package com.ssquare.bullsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModelClass implements Parcelable {
    String uid,name,uName,mail,phone,pass;

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

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserModelClass() {
    }

    public UserModelClass(String uid, String name, String uName, String mail, String phone, String pass) {
        this.uid = uid;
        this.name = name;
        this.uName = uName;
        this.mail = mail;
        this.phone = phone;
        this.pass = pass;
    }

    protected UserModelClass(Parcel in) {
        uid = in.readString();
        name = in.readString();
        uName = in.readString();
        mail = in.readString();
        phone = in.readString();
        pass = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(uName);
        parcel.writeString(mail);
        parcel.writeString(phone);
        parcel.writeString(pass);
    }
}
