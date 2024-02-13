package es.jac.gymlog.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ExerciseDb::class],
    version = 1
)

abstract class ExercisesDatabase: RoomDatabase(){

    abstract fun exerciseDao(): ExerciseDao

    companion object{

        private const val DATABASE_NAME = "GYMLOG_DB"

        private var instance: ExercisesDatabase? = null
        fun getInstance(context: Context): ExercisesDatabase {
            synchronized(this){
                if (instance == null){
                    Room.databaseBuilder(context.applicationContext,
                        ExercisesDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance!!
        }


    }


}