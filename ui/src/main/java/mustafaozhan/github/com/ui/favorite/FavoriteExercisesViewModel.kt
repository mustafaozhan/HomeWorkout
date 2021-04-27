package mustafaozhan.github.com.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mustafaozhan.github.com.db.ExerciseDao
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.util.MutableSingleLiveData
import mustafaozhan.github.com.util.SingleLiveData

class FavoriteExercisesViewModel(
    private val exerciseDao: ExerciseDao
) : ViewModel(), FavoriteExercisesEvent {

    // region SEED
    private var _state = MutableLiveData(FavoriteExercisesState())
    var state: LiveData<FavoriteExercisesState> = _state

    private var _effect = MutableSingleLiveData<FavoriteExercisesEffect>()
    var effect: SingleLiveData<FavoriteExercisesEffect> = _effect

    fun getEvent() = this as FavoriteExercisesEvent
    // endregion

    init {
        _state.value = _state.value?.copy(loading = true)
        viewModelScope.launch {
            exerciseDao.collectFavoriteExercises()
                .collect { exerciseList ->
                    _state.value = _state.value?.copy(
                        exerciseList = exerciseList.filter { it.isFavorite },
                        loading = false
                    )
                }
        }
    }

    // region events
    override fun favoriteClicked(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.insertExercise(exercise.copy(isFavorite = !exercise.isFavorite))
        }
    }

    override fun playExercise(exercise: Exercise) {
        _effect.postValue(FavoriteExercisesEffect.PlayExercise(listOf(exercise)))
    }

    override fun onBackPressed() {
        _effect.postValue(FavoriteExercisesEffect.BackEffect)
    }
    // endregion
}
