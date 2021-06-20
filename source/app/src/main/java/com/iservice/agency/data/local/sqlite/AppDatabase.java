package com.iservice.agency.data.local.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.iservice.agency.data.local.sqlite.dao.DbFoodDao;
import com.iservice.agency.data.local.sqlite.dao.DbUserDao;
import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.data.model.db.UserEntity;

@Database(entities = {UserEntity.class, FoodEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DbUserDao getDbUserDao();
    public abstract DbFoodDao getDbFoodDao();
}
