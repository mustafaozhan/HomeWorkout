package mustafaozhan.github.com.viewmodel

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.rule.TestCoroutineRule
import mustafaozhan.github.com.ui.player.Data
import mustafaozhan.github.com.ui.player.PlayerEffect
import mustafaozhan.github.com.ui.player.PlayerViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlayerViewModelTest : BaseViewModelTest<PlayerViewModel>() {
    @ExperimentalCoroutinesApi
    @get:Rule
    val scope = TestCoroutineRule()

    override lateinit var viewModel: PlayerViewModel

    private val mockExercises = listOf(
        Exercise(123, "", "", ""),
        Exercise(234, "", "", "")
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = PlayerViewModel()
    }

    @Test
    fun setData() = with(viewModel) {
        setData(mockExercises)

        Assert.assertEquals(mockExercises.first(), data.currentItem)
        Assert.assertEquals(mockExercises, data.playlist)

        Assert.assertEquals(true, state.value?.loading)
        Assert.assertEquals(PlayerEffect.PlayVideoEffect(data.currentItem.videoUrl), effect.value)
    }

    @Test
    fun exitPlayer() {
        viewModel.getEvent().exitPlayer()
        Assert.assertEquals(PlayerEffect.BackEffect, viewModel.effect.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun videoStart() = scope.runBlockingTest {
        viewModel.setData(mockExercises)
        viewModel.getEvent().videoStart()

        Assert.assertEquals(true, viewModel.state.value?.titleVisibility)
        Assert.assertEquals(viewModel.data.currentItem.name, viewModel.state.value?.title)
        Assert.assertEquals(false, viewModel.state.value?.loading)

        delay(Data.TITLE_DISAPPEAR_DELAY)
        Assert.assertEquals(false, viewModel.state.value?.titleVisibility)
    }

    @Test
    fun onSkipClick() = with(viewModel) {
        setData(mockExercises)
        val currentData = data
        getEvent().onSkipClick()
        Assert.assertEquals(currentData.skipCount, data.skipCount)
    }

    @Test
    fun onCancelClick() = with(viewModel) {
        setData(mockExercises)
        getEvent().onCancelClick()

        Assert.assertEquals(
            PlayerEffect.OpenSummaryScreen(
                data.skipCount,
                data.playlist?.size ?: -1
            ), effect.value
        )

        setData(listOf(Exercise(123, "", "", "")))
        getEvent().onCancelClick()

        Assert.assertEquals(
            PlayerEffect.PlaybackEnd, effect.value
        )
    }
}
