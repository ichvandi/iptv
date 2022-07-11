package com.vandoc.iptv.util

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Ichvandi
 * Created on 19/06/2022 at 11:17.
 */
class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        while (!response.isSuccessful && tryCount < 3) {
            tryCount++

            response.close()
            response = chain.proceed(request)
        }

        return response
    }
}