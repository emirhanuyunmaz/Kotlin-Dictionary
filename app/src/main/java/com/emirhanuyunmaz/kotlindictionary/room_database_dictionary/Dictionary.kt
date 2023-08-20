package com.emirhanuyunmaz.kotlindictionary.room_database_dictionary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dictionary(

    @ColumnInfo("turkishWord")
    var turkishWord:String,

    @ColumnInfo("englishWord")
    var englishWord:String,

) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}