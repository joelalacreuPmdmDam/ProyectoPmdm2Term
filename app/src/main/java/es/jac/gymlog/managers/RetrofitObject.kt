package es.jac.gymlog.managers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitObject {

    companion object{
        private var instance: Retrofit? = null

        val baseUrl = "https://api.api-ninjas.com/"

        fun getInstance():Retrofit{
            synchronized(this){
                if(instance==null){
                    instance = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return instance!!
        }
    }
}