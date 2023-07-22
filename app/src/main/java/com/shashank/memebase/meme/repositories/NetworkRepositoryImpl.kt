package com.shashank.memebase.meme.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shashank.memebase.db.Dao
import com.shashank.memebase.globals.Constants
import com.shashank.memebase.meme.memesModels.MemeModel
import com.shashank.memebase.meme.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val retroService: RetroService,
                                                private val dao: Dao) : NetworkRepository {

    var mutableMemeList: MutableLiveData<MemeModel?> = MutableLiveData()
    var mutableProgressDialog= MutableLiveData<Boolean>()
    var progressDialog: LiveData<Boolean> = mutableProgressDialog
    override suspend fun getAllMemes(): MemeModel {
        val memes = dao.getAllMemes()
        return memes
    }

    override fun getProgressDlg() : LiveData<Boolean> {
        return progressDialog
    }
    override fun insertRecord(memeModel: MemeModel) {
        dao.insertRecords(memeModel)
    }
    override fun makeApiCall() {
        retroService.getMemeListApi(Constants.ApiProperties.APP_URL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getMemeListObserverRx())
    }

    private fun getMemeListObserverRx(): Observer<MemeModel?> {
        return object : Observer<MemeModel?> {
            override fun onComplete() {
                mutableProgressDialog.postValue(false)
            }
            @SuppressLint("NullSafeMutableLiveData")
            override fun onError(e: Throwable) {
                mutableProgressDialog.postValue(false)
                mutableMemeList.postValue(null)

            }

            override fun onNext(t: MemeModel) {
                mutableMemeList.postValue(t)
                insertRecord(t)
            }

            override fun onSubscribe(d: Disposable) {
            }
        }
    }
}