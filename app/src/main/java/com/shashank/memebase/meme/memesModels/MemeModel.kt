package com.shashank.memebase.meme.memesModels


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shashank.memebase.globals.Constants


@Entity(tableName = Constants.ApiProperties.TABLE_NAME_MEME_MODEL)
data class MemeModel(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")val id: Int = 0,
                     @ColumnInfo(name = "data")val data: Data
)












