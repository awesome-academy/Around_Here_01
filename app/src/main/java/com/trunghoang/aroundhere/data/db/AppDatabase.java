package com.trunghoang.aroundhere.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.trunghoang.aroundhere.data.db.entity.PlaceEntity;
import com.trunghoang.aroundhere.util.Constants;

@Database(entities = {PlaceEntity.class},
        version = Constants.DATABASE_VERSION,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDAO placeDAO();

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context,
                            AppDatabase.class,
                            AppDatabase.class.getSimpleName())
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }
}
