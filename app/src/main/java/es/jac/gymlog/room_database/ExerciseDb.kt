package es.jac.gymlog.room_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("exercises")
data class ExerciseDb(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val type: String,
    @ColumnInfo
    val muscle: String,
    @ColumnInfo
    val equipment: String,
    @ColumnInfo
    val difficulty: String,
    @ColumnInfo
    val instructions: String
)

