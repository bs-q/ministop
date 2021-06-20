package com.iservice.agency.data.local.sqlite;

import androidx.lifecycle.LiveData;

import com.iservice.agency.data.model.db.FoodEntity;
import com.iservice.agency.data.model.db.UserEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface DbService {

    Observable<List<UserEntity>> getAllDbUser();

    LiveData<List<UserEntity>> loadAllToLiveData();

    Observable<Long> insertUser(UserEntity user);

    Observable<Long> insertFood(FoodEntity foodEntity);

    Observable<Boolean> deleteUser(UserEntity user);

    Observable<UserEntity> findById(Long id);

    LiveData<List<FoodEntity>> loadAllToLiveData(String type);

    FoodEntity findFoodById(Long id);


}
