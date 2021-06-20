package com.iservice.agency.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity(tableName = "db_food")
public class FoodEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "price")
    public Integer price;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo()
    private String image;

    public FoodEntity() {
    }
}
