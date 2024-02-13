package es.jac.gymlog.room_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ExerciseDao {


    @Query("SELECT * FROM exercises WHERE muscle = :muscle")
    suspend fun getExerciseByMuscle(muscle:String): MutableList<ExerciseDb>

    @Query("SELECT * FROM exercises WHERE name = :name")
    suspend fun getOneExercise(name: String):ExerciseDb

    @Insert
    suspend fun insertExercise(exercise: ExerciseDb)

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()
}