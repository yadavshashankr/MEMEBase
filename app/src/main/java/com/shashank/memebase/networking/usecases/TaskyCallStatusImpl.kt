package com.shashank.memebase.networking.usecases

import android.content.Context
import android.widget.Toast
import com.shashank.memebase.networking.usecases.domain.TaskyLoader
import com.shashank.memebase.networking.usecases.domain.TaskyCallStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * TaskStatusImpl will harbour the code for un-successful api calls and
 * would present errors, messages in dialogs
 * with respect to Response codes.
 */
class TaskyCallStatusImpl @Inject constructor(private val taskyLoader: TaskyLoader, private val context: Context) : TaskyCallStatus {

    override suspend fun onRequestCallStarted() {
        taskyLoader.setLoading(true)
    }

    override suspend fun onResponse(responseCode: Int, responseMessage: String) {
        taskyLoader.setLoading(false)

        if(responseCode in 400..500){
            withContext(Dispatchers.Main){
                Toast.makeText(context, responseMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override suspend fun onFailure(responseCode: Int, errorMessage: String) {
        withContext(Dispatchers.Main){
            taskyLoader.setLoading(false)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}