package com.shashank.memebase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shashank.memebase.meme.memesModels.Data
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.memesModels.Memes


@Database(entities = [MemeModel::class],
                version = 4,
                exportSchema = false)

@TypeConverters(
                Data.ConverterMemes::class,
                Memes.TypeConverterMemes::class,
                MemeModel.TypeConverterData::class)
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
