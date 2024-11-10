package com.lion.a042ex_fragment1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("StudentTable")
data class DataModel(
    @PrimaryKey(autoGenerate = true)
    var idx:Int = 0,
    var name:String = "",
    var age:Int = 0,
    var korean:Int = 0
)