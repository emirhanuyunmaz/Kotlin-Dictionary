package com.emirhanuyunmaz.kotlindictionary.room_database_dictionary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface DictionaryDao {

    @Query("SELECT * FROM Dictionary")
    fun getAll():List<Dictionary>

    @Insert
    fun insert(vararg dictionary: Dictionary)

    @Delete
    fun delete(dictionary: Dictionary)

    @Query("SELECT * FROM Dictionary WHERE id IN (:getId)")
    fun getSearc(getId:Int):Dictionary

    @Update
    fun update(vararg dictionary: Dictionary)
}