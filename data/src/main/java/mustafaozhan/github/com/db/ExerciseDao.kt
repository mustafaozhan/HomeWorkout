package mustafaozhan.github.com.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mustafaozhan.github.com.model.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    fun collectFavoriteExercises(): Flow<MutableList<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)
}
