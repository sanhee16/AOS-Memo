package com.sandy.memo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sandy.memo.dao.MemoDao
import com.sandy.memo.entity.Memo

// mysql 에서 database
@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    // 데이터베이스 객체를 인스턴스 할 때 싱글톤으로 구현하기를 권장
    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "memo-database"
                    ).build()
                }
            }
            return instance
        }
    }
}