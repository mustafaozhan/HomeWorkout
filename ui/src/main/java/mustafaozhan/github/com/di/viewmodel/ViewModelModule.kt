package mustafaozhan.github.com.di.viewmodel

import dagger.Module
import dagger.Provides
import mustafaozhan.github.com.api.ApiRepository
import mustafaozhan.github.com.db.ExerciseDao
import mustafaozhan.github.com.di.scope.ActivityScope
import mustafaozhan.github.com.ui.player.PlayerViewModel
import mustafaozhan.github.com.ui.exercises.ExerciseViewModel
import mustafaozhan.github.com.ui.favorite.FavoriteExercisesViewModel

@Module
class ViewModelModule {

    @Provides
    @ActivityScope
    internal fun providesExerciseViewModel(
        apiRepository: ApiRepository,
        exerciseDao: ExerciseDao
    ) = ExerciseViewModel(
        apiRepository,
        exerciseDao
    )

    @Provides
    @ActivityScope
    internal fun providesPlayerViewModel() = PlayerViewModel()

    @Provides
    @ActivityScope
    internal fun providesFavoriteExercisesViewModel(exerciseDao: ExerciseDao) =
        FavoriteExercisesViewModel(exerciseDao)
}
