package mustafaozhan.github.com.viewmodel

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import mustafaozhan.github.com.api.ApiRepository
import mustafaozhan.github.com.db.ExerciseDao
import mustafaozhan.github.com.model.Exercise
import mustafaozhan.github.com.ui.exercises.ExerciseEffect
import mustafaozhan.github.com.ui.exercises.ExerciseViewModel
import mustafaozhan.github.com.util.Result
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExerciseViewModelTest : BaseViewModelTest<ExerciseViewModel>() {

    override lateinit var viewModel: ExerciseViewModel

    @RelaxedMockK
    lateinit var apiRepository: ApiRepository

    @RelaxedMockK
    lateinit var exerciseDao: ExerciseDao

    private val mockResponse = listOf(Exercise(12, "", "asd", ""))

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery {
            apiRepository.getExercises()
        } coAnswers {
            Result.Success(mockResponse)
        }
        viewModel = ExerciseViewModel(apiRepository, exerciseDao)
    }

    @Test
    fun filterList() {
        val mockEvent1 = Exercise(12, "", "asd", "")
        val mockEvent2 = Exercise(12, "", "cdf", "")

        viewModel.data.unFilteredList = mutableListOf<Exercise>().apply {
            add(mockEvent1)
            add(mockEvent2)
        }

        viewModel.filterList("a")
        assertEquals(true, viewModel.state.value?.exerciseList?.contains(mockEvent1))

        viewModel.filterList("d")
        assertEquals(true, viewModel.state.value?.exerciseList?.contains(mockEvent2))
    }

    @Test
    fun openExercise() {
        val mockEvent = Exercise(12, "", "asd", "")
        viewModel.getEvent().openExercise(mockEvent)
        assertEquals(ExerciseEffect.PlayExercise(listOf(mockEvent)), viewModel.effect.value)
    }

    @Test
    fun openFavoriteExercises() {
        viewModel.getEvent().openFavoriteExercises()
        assertEquals(ExerciseEffect.OpenFavoriteExercises, viewModel.effect.value)
    }

    @Test
    fun startWorkout() {
        viewModel.getEvent().startWorkout()
        assertEquals(ExerciseEffect.PlayExercise(mockResponse), viewModel.effect.value)
    }
}
