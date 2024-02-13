package es.jac.gymlog.managers

import es.jac.gymlog.models.ExerciseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExerciseAPI {

    @GET("/v1/exercises")
    suspend fun getExercises(
        @Query("muscle") muscle: String,@Header("X-Api-Key") apiKey: String
    ): Response<List<ExerciseResponse>>
}
