package mustafaozhan.github.com.viewmodel

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import mustafaozhan.github.com.db.ExerciseDao
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.ui.favorite.FavoriteExercisesEffect
import mustafaozhan.github.com.ui.favorite.FavoriteExercisesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavoriteExercisesViewModelTest : BaseViewModelTest<FavoriteExercisesViewModel>() {
    override lateinit var viewModel: FavoriteExercisesViewModel

    @RelaxedMockK
    lateinit var exerciseDao: ExerciseDao

    private val mockExercise = Exercise(123, "", "", "")

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = FavoriteExercisesViewModel(exerciseDao)
    }

    @Test
    fun playExercise() {
        viewModel.playExercise(mockExercise)
        Assert.assertEquals(
            FavoriteExercisesEffect.PlayExercise(listOf(mockExercise)),
            viewModel.effect.value
        )
    }

    @Test
    fun onBackPressed() {
        viewModel.onBackPressed()
        Assert.assertEquals(FavoriteExercisesEffect.BackEffect, viewModel.effect.value)
    }
}
