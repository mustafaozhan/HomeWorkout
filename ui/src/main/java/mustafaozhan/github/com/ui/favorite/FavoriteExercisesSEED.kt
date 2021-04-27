package mustafaozhan.github.com.ui.favorite

import mustafaozhan.github.com.model.Exercise

// State
data class FavoriteExercisesState(
    val exerciseList: List<Exercise> = listOf(),
    val loading: Boolean = true
)

// Effect
sealed class FavoriteExercisesEffect {
    data class PlayExercise(val exercise: List<Exercise>) : FavoriteExercisesEffect()
    object BackEffect : FavoriteExercisesEffect()
}

// Event
interface FavoriteExercisesEvent {
    fun favoriteClicked(exercise: Exercise)
    fun playExercise(exercise: Exercise)
    fun onBackPressed()
}
