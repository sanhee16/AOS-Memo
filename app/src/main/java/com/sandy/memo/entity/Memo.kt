package com.sandy.memo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// mysql 에서 table
@Entity
data class Memo(var title: String, var content: String, var date: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}