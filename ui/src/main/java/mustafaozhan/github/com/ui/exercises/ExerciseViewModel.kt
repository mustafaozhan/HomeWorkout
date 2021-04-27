package mustafaozhan.github.com.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mustafaozhan.github.com.api.ApiRepository
import mustafaozhan.github.com.db.ExerciseDao
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.util.MutableSingleLiveData
import mustafaozhan.github.com.util.SingleLiveData
import timber.log.Timber

class ExerciseViewModel(
    private val apiRepository: ApiRepository,
    private val exerciseDao: ExerciseDao
) : ViewModel(), ExerciseEvent {

    // region SEED
    private var _state = MutableLiveData(ExerciseState())
    var state: LiveData<ExerciseState> = _state

    private var _effect = MutableSingleLiveData<ExerciseEffect>()
    var effect: SingleLiveData<ExerciseEffect> = _effect

    fun getEvent() = this as ExerciseEvent

    var data = ExerciseData()
    // endregion

    init {
        viewModelScope.launch {
            getExercisesFromApi()
            getFavoriteExercises()
        }
    }

    private suspend fun getExercisesFromApi() {
        _state.value = _state.value?.copy(isLoading = true)

        apiRepository.getExercises()
            .execute(
                ::exerciseDownloadSuccess,
                ::exerciseDownloadFailed
            ) {
                _state.value = _state.value?.copy(isLoading = false)
            }
    }

    private fun exerciseDownloadSuccess(exercises: List<Exercise>) {
        _state.value = _state.value?.copy(exerciseList = exercises)
        data.unFilteredList = exercises.toMutableList()
    }

    private fun exerciseDownloadFailed(throwable: Throwable) {
        Timber.e(throwable)
    }

    private suspend fun getFavoriteExercises() = exerciseDao.collectFavoriteExercises()
        .collect { exerciseList ->
            _state.value = _state.value?.copy(
                favoriteItemCount = exerciseList.filter { it.isFavorite }.count()
            )
            state.value?.exerciseList?.let {
                val tempList = it.toMutableList()

                exerciseList.forEach { exercise ->
                    tempList.forEachIndexed { index, tempExercise ->
                        if (tempExercise.id == exercise.id) {
                            tempList[index].isFavorite = exercise.isFavorite
                        }
                    }
                }

                _state.value = _state.value?.copy(exerciseList = tempList)
            }
        }

    fun filterList(txt: String) = data.unFilteredList
        ?.filter {
            it.name.contains(txt, true)
        }?.toMutableList()
        ?.let {
            _state.value = _state.value?.copy(exerciseList = it)
        }.run {
            data.query = txt
            true
        }

    // region events
    override fun favoriteClicked(item: Exercise) {
        viewModelScope.launch {
            exerciseDao.insertExercise(item.copy(isFavorite = !item.isFavorite))
        }
    }

    override fun openExercise(item: Exercise) {
        _effect.postValue(ExerciseEffect.PlayExercise(listOf(item)))
    }

    override fun startWorkout() {
        _state.value?.exerciseList?.let {
            _effect.postValue(ExerciseEffect.PlayExercise(it))
        }
    }

    override fun openFavoriteExercises() {
        _effect.postValue(ExerciseEffect.OpenFavoriteExercises)
    }
    // endregion
}
