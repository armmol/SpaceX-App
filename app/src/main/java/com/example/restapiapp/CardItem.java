package com.example.restapiapp;
public class CardItem {
    String name,agency,wiki,status;
    int img;

    public String getName () {
        return name;
    }

    public String getAgency () {
        return agency;
    }

    public String getWiki () {
        return wiki;
    }

    public String getStatus () {
        return status;
    }

    public int getImg () {
        return img;
    }

    public CardItem(String a, String b, String c, String d, int e)
    {
        name = a;
        agency =b;
        wiki =c;
        status = d;
        img =e;
    }
}
