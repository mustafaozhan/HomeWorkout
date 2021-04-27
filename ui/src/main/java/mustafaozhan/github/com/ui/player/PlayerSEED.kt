package mustafaozhan.github.com.ui.player

import mustafaozhan.github.com.model.Exercise

// State
data class PlayerState(
    val titleVisibility: Boolean = true,
    val loading: Boolean = true,
    val title: String = ""
)

// Effect
sealed class PlayerEffect {
    object BackEffect : PlayerEffect()
    data class PlayVideoEffect(val url: String) : PlayerEffect()
    object PlaybackEnd : PlayerEffect()
}

// Event
interface PlayerEvent {
    fun exitPlayer()
    fun videoEnd()
    fun videoStart()
    fun onSkipClick()
    fun onCancelClick()
}

data class Data(
    var currentItem: Exercise,
    var playlist: List<Exercise>? = null
) {
    companion object {
        const val TITLE_DISAPPEAR_DELAY: Long = 5000L
    }
}
