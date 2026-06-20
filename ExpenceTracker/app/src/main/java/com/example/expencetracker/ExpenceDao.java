package com.example.expencetracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenceDao {
    @Query("SELECT * FROM expences")
    List<Expence> getAllExpence();

    @Insert
    void addTx(Expence expence);

    @Update
    void updateTx(Expence expence);

    @Delete
    void deleteTx(Expence expence);
}
