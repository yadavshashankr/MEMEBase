package com.example.memebase.models.userModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memebase.globals.ApplicationConstant
import com.example.memebase.models.memesModels.Data


@Entity(tableName = ApplicationConstant.TABLE_NAME_USERS)
data class Users(@PrimaryKey(autoGenerate = true)
                 @ColumnInfo(name = "id")val id: Int?,
                 @ColumnInfo(name = "userId")val userId: String,
                 @ColumnInfo(name = "password")val userPassword: String,
                 @ColumnInfo(name = "userName")val userName: String

) {


}