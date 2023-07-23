package com.shashank.memebase.meme.repositories

import android.util.Log
import com.shashank.memebase.db.Dao
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.network.RetroService
import java.lang.Exception
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val retroService: RetroService,
                                                private val dao: Dao) : NetworkRepository {

    override suspend fun getAllMemes(): MemeModel {
        return dao.getAllMemes()
    }
    override fun insertRecord(memeModel: MemeModel) {
        dao.insertRecords(memeModel)
    }
    override suspend fun makeApiCall() : MemeModel? {

        return try{
            val response = retroService.getMemeListApi()
            if(response.isSuccessful){
                response.body()?.let {
                    dao.insertRecords(it)
                }
                response.body()
            }else{
                Log.e("API_ERROR", response.errorBody().toString())
                null
            }
        }catch (e : Exception){
            Log.e("CALL_ERROR", e.message.toString())
            null
        }
    }
}