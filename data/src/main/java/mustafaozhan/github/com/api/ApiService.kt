/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package mustafaozhan.github.com.api

import mustafaozhan.github.com.model.Exercise
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        private const val KEY_DEFAULT_PATH = "632a3747-71f1-11eb-a9e0-d145d60a1fd6"
    }

    @GET("api/jsonBlob/{path_key}")
    suspend fun getExercises(
        @Path("path_key") pathKey: String = KEY_DEFAULT_PATH
    ): List<Exercise>
}
