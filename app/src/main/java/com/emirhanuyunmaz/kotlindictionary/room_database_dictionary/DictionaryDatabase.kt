package com.emirhanuyunmaz.kotlindictionary.room_database_dictionary

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dictionary::class],version=1)
abstract class DictionaryDatabase :RoomDatabase(){
    abstract fun dictionaryDao():DictionaryDao
}