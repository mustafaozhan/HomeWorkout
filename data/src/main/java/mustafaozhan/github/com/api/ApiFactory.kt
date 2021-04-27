/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package mustafaozhan.github.com.api

import android.content.Context
import com.squareup.moshi.Moshi
import mustafaozhan.github.com.data.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class ApiFactory
@Inject constructor(private val context: Context) {

    companion object {
        private const val TIME_OUT: Long = 3
    }

    private val endpoint: String
        get() = context.getString(R.string.backend_endpoint)

    val apiService: ApiService by lazy { initApiServices() }

    private fun initApiServices() = createRetrofit(getClient())
        .create(ApiService::class.java)

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .client(httpClient)
            .build()
    }

    private fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()
}
