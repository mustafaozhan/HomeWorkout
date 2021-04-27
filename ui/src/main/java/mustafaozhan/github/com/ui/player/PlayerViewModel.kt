package mustafaozhan.github.com.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.ui.player.Data.Companion.TITLE_DISAPPEAR_DELAY
import mustafaozhan.github.com.util.MutableSingleLiveData
import mustafaozhan.github.com.util.SingleLiveData

@Suppress("TooManyFunctions")
class PlayerViewModel : ViewModel(), PlayerEvent {

    // region SEED
    private var _state = MutableLiveData(PlayerState())
    var state: LiveData<PlayerState> = _state

    fun getEvent() = this as PlayerEvent

    private var _effect = MutableSingleLiveData<PlayerEffect>()
    var effect: SingleLiveData<PlayerEffect> = _effect

    private lateinit var data: Data
    // endregion

    fun setData(exercises: List<Exercise>) {
        data = Data(exercises.first(), exercises)
        setLoading(true)
        _effect.postValue(PlayerEffect.PlayVideoEffect(data.currentItem.videoUrl))
    }

    // region events
    override fun exitPlayer() {
        _effect.postValue(PlayerEffect.BackEffect)
    }

    override fun videoEnd() = when (data.playlist?.last()) {
        data.currentItem -> endWorkout()
        else -> playNextVideo()
    }

    private fun playNextVideo() {
        try {
            data.playlist?.get(getCurrentItemPosition() + 1)?.let {
                data.currentItem = it
                setLoading(true)
                _effect.postValue(PlayerEffect.PlayVideoEffect(it.videoUrl))
            }
        } catch (e: IndexOutOfBoundsException) {
            endWorkout()
        }
    }

    private fun endWorkout() {
        if (data.playlist?.size == 1) {
            _effect.postValue(PlayerEffect.PlaybackEnd)
        } else {
            _effect.postValue(
                PlayerEffect.OpenSummaryScreen(
                    data.skipCount,
                    data.playlist?.size ?: -1
                )
            )
        }
    }

    private fun setLoading(state: Boolean) {
        _state.value = _state.value?.copy(loading = state)
    }

    private fun getCurrentItemPosition() = data.playlist?.indexOf(data.currentItem) ?: -1

    override fun videoStart() {
        _state.value = _state.value?.copy(
            titleVisibility = true,
            title = data.currentItem.name
        )

        setLoading(false)

        viewModelScope.launch {
            delay(TITLE_DISAPPEAR_DELAY)
            _state.value = _state.value?.copy(titleVisibility = false)
        }
    }

    override fun onSkipClick() {
        data.skipCount++
        playNextVideo()
    }

    override fun onCancelClick() {
        endWorkout()
    }
    // endregion
}
