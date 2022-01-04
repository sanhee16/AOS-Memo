package com.sandy.memo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.sandy.memo.entity.Memo


/**
 * mysql 에서 query
 *
 */
@Dao
interface MemoDao {
    @Query("SELECT * FROM Memo")
    fun getAll(): LiveData<List<Memo>>

    /**
     * primary key가 일치하면 replace
     */
    @Insert(onConflict = REPLACE)
    fun insert(todo: Memo)

    /**
     * update : primary key 비교해서 찾고, 다른 부분만 update
     */
    @Update
    fun update(todo: Memo)

    @Delete
    fun delete(todo: Memo)

    @Query("DELETE FROM Memo WHERE id = :id")
    fun deleteFromId(id: Int)

    @Query("SELECT * FROM Memo WHERE id = :id")
    fun getItem(id: Int) : Memo

    @Query("UPDATE Memo SET pin = :pin WHERE id = :id")
    fun updatePin(id: Int, pin: Boolean)
}