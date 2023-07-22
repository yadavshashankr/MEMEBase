package com.shashank.memebase.networking.usecases

import android.content.Context
import android.widget.Toast
import com.shashank.memebase.networking.usecases.domain.TaskyLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * TaskyLoader will harbour the code for loading dialogs and would start or dismiss based on the value isLoading received in
 */
class TaskyLoaderImpl constructor(val context: Context) : TaskyLoader {

    override suspend fun setLoading(isLoading: Boolean) {
        withContext(Dispatchers.Main){
            if (isLoading){
                Toast.makeText(context, "Loading Started", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Loading Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}