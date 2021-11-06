package com.example.memebase.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memebase.db.Dao
import com.example.memebase.models.memesModels.MemeModel
import com.example.memebase.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val retroService: RetroService,
                                            private val dao: Dao) {

    var mutableMemeList: MutableLiveData<MemeModel> = MutableLiveData()


    fun getAllMemes(): LiveData<MemeModel> {
        return dao.getAllMemes()
    }



    fun insertRecord(memeModel: MemeModel) {


        dao.insertRecords(memeModel)
    }

    fun makeApiCall() {

        retroService.getMemeListApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getMemeListObserverRx())


    }

    private fun getMemeListObserverRx(): Observer<MemeModel> {

        return object : Observer<MemeModel> {
            override fun onComplete() {
                //hide progress indicator .

            }

            override fun onError(e: Throwable) {
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