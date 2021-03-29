package com.example.restapiapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database (entities = {TableRow.class},version = 1)
public abstract class SpaceXDatabase extends RoomDatabase {

    private static SpaceXDatabase obj;

    public static synchronized SpaceXDatabase getObj(Context context){
        if(obj == null) {
            obj = Room.databaseBuilder (context.getApplicationContext ( ), SpaceXDatabase.class,
                    "SpaceX_Data").allowMainThreadQueries ().fallbackToDestructiveMigration ( ).build ( );
        }
        return obj;
    }

    public abstract SpaceXDao spaceXDao();
}
