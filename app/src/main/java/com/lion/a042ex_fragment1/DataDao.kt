package com.lion.a042ex_fragment1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    // 학생 정보 저장
    @Insert
    fun insertStudentData(dataModel: DataModel)

    // 학생의 모든 정보를 가져온다.
    @Query("select * from StudentTable")
    fun selectStudentDataAll() : List<DataModel>

    // 특정 학생의 정보를 가져온다.
    @Query("select * from StudentTable where idx = :idx")
    fun selectStudentDataOne(idx:Int) : DataModel
}