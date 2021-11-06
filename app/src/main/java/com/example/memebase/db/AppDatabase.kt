package com.example.memebase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.memebase.models.memesModels.Data
import com.example.memebase.models.memesModels.MemeModel
import com.example.memebase.models.memesModels.Memes
import com.example.memebase.models.userModels.Users


@Database(entities = [MemeModel::class, Users::class],
                version = 2,
                exportSchema = false)
@TypeConverters(Data.TypeConverterData::class,
                Memes.TypeConverterMemes::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context): AppDatabase {
            if(DB_INSTANCE == null) {
                DB_INSTANCE =  Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "APP_DB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
    }
