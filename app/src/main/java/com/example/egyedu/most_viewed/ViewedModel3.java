package com.example.egyedu.most_viewed;

public class ViewedModel3 {
    private String name3;
    private String id;
    private String image3;
    private String info3;


    public ViewedModel3 () {
    }

    public ViewedModel3 ( String name3 , String id , String image3 , String info3 ) {

        this.name3 = name3;
        this.id = id;
        this.image3 = image3;
        this.info3 = info3;
    }


    public String getName () {
        return name3;
    }

    public void setName ( String name3 ) {
        this.name3 = name3;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getImage () {
        return image3;
    }

    public void setImage ( String image3 ) {
        this.image3 = image3;
    }

    public String getInfo () {
        return info3;
    }

    public void setInfo ( String info3 ) {
        this.info3 = info3;
    }
}