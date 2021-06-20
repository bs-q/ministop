package com.iservice.agency.data.local.sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iservice.agency.data.model.db.FoodEntity;

import java.util.List;

@Dao
public interface DbFoodDao {

    @Query("SELECT * FROM db_food WHERE type=:type")
    LiveData<List<FoodEntity>> loadAllToLiveData(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(FoodEntity entity);

    @Query("SELECT * FROM db_food WHERE id=:id")
    FoodEntity findFoodById(Long id);
}
