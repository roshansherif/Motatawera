package com.example.egyedu.MyCourses;

public class MyCoursesModel {  private String category;
    private String center_name;
    private String course_name;
    private String id;
    private String date;
    private String image;
    private String info;
    private int price;

    public MyCoursesModel () {
    }

    public MyCoursesModel ( String date ,String category , String center_name , String course_name , String id , String image , String info , int price ) {
        this.category = category;
        this.center_name = center_name;
        this.course_name = course_name;
        this.id = id;
        this.date = date;
        this.image = image;
        this.info = info;
        this.price = price;
    }


    public String getCategory () {
        return category;
    }

    public void setCategory ( String category ) {
        this.category = category;
    }

    public String getCenter_name () {
        return center_name;
    }

    public void setCenter_name ( String center_name ) {
        this.center_name = center_name;
    }


    public String getCourse_name () {
        return course_name;
    }

    public void setCourse_name ( String course_name ) {
        this.course_name = course_name;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }


    public String getDate () {
        return date;
    }

    public void setDate ( String date ) {
        this.date = date;
    }

    public String getImage () {
        return image;
    }

    public void setImage ( String image ) {
        this.image = image;
    }

    public String getInfo () {
        return info;
    }

    public void setInfo ( String info ) {
        this.info = info;
    }

    public int getPrice () {
        return price;
    }

    public void setPrice ( int price ) {
        this.price = price;
    }
}
