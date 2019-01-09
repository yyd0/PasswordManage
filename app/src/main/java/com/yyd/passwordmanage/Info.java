package com.yyd.passwordmanage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Info implements Parcelable {
    @Id
    private long id;
    private String name;
    private String account;
    private String password;
    private String comment;
    private Date createDate;

    public Info() {
    }

    public Info(String name, String account,String password, String comment) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.comment = comment;
        this.createDate = new Date();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeString(this.comment);
        dest.writeLong(this.createDate != null ? this.createDate.getTime() : -1);
    }

    protected Info(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.account = in.readString();
        this.password = in.readString();
        this.comment = in.readString();
        long tmpCreateDate = in.readLong();
        this.createDate = tmpCreateDate == -1 ? null : new Date(tmpCreateDate);
    }

    public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}
