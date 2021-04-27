package mustafaozhan.github.com.di.view

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mustafaozhan.github.com.ui.exercises.ExerciseFragment
import mustafaozhan.github.com.ui.favorite.FavoriteExercisesFragment
import mustafaozhan.github.com.ui.player.PlayerFragment
import mustafaozhan.github.com.ui.summary.SummaryFragment

@Suppress("unused")
@Module
abstract class FragmentInjectionModule {

    @ContributesAndroidInjector
    abstract fun contributesExerciseFragment(): ExerciseFragment

    @ContributesAndroidInjector
    abstract fun contributesPlayerFragment(): PlayerFragment

    @ContributesAndroidInjector
    abstract fun contributesFavoriteExercisesFragment(): FavoriteExercisesFragment

    @ContributesAndroidInjector
    abstract fun contributesSummaryFragment(): SummaryFragment
}
