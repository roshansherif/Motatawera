package com.example.egyedu.most_viewed;

public class ViewedModel {
    private String category;
    private String center_name;
    private String course_name;
    private String date;
    private String id;
    private String image;
    private String info;
    private boolean isOnline;
    private String link;
    private String phone;
    private int price;

    public ViewedModel() {
    }


    public ViewedModel(String category, String center_name,  String course_name, String date, String id, String image, String info, boolean isOnline, String link, String phone, int price) {
        this.category = category;
        this.center_name = center_name;

        this.course_name = course_name;
        this.date = date;
        this.id = id;
        this.image = image;
        this.info = info;
        this.isOnline = isOnline;
        this.link = link;
        this.phone = phone;
        this.price = price;
    }


    public String getCategory () {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCenter_name() { return center_name; }
    public void setCenter_name(String center_name)
    { this.center_name = center_name;}

    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}