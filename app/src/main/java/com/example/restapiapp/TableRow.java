package com.example.restapiapp;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SPACE-X TABLE")
public class TableRow {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String agency;
    @ColumnInfo
    private String image;
    @ColumnInfo
    private String wikipedia;
    @ColumnInfo
    private String status;

    public TableRow (String name, String agency, String image, String wikipedia, String status) {
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.wikipedia = wikipedia;
        this.status = status;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getAgency () {
        return agency;
    }

    public String getImage () {
        return image;
    }

    public String getWikipedia () {
        return wikipedia;
    }

    public String getStatus () {
        return status;
    }
}