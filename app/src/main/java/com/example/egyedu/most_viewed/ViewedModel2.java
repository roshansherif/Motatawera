package com.example.egyedu.most_viewed;

public class ViewedModel2 {
    private String name;
    private String id;
    private String image;
    private String info;


    public ViewedModel2 () {
    }

    public ViewedModel2 ( String name , String id , String image , String info ) {

        this.name = name;
        this.id = id;
        this.image = image;
        this.info = info;
    }


    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
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
}