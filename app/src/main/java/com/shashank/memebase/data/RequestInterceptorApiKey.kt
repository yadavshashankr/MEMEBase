package com.shashank.memebase.data


import com.shashank.memebase.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestInterceptorApiKey @Inject constructor() : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("x-api-key", BuildConfig.API_KEY)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}