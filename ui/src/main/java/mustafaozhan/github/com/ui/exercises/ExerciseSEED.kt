package mustafaozhan.github.com.ui.exercises

import mustafaozhan.github.com.model.Exercise

// State
data class ExerciseState(
    val exerciseList: List<Exercise> = listOf(),
    val isLoading: Boolean = true,
    val favoriteItemCount: Int = 0
)

// Effect
sealed class ExerciseEffect {
    data class PlayExercise(val exercise: List<Exercise>) : ExerciseEffect()
    object OpenFavoriteExercises : ExerciseEffect()
}

// Event
interface ExerciseEvent {
    fun favoriteClicked(item: Exercise)
    fun openExercise(item: Exercise)
    fun openFavoriteExercises()
}

// Data
data class ExerciseData(
    var unFilteredList: MutableList<Exercise>? = mutableListOf(),
    var query: String = ""
)
