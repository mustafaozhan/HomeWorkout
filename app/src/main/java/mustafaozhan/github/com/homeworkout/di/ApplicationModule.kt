package mustafaozhan.github.com.homeworkout.di

import android.content.Context
import dagger.Module
import dagger.Provides
import mustafaozhan.github.com.homeworkout.HomeWorkoutApp
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    internal fun providesContext(application: HomeWorkoutApp): Context =
        application.applicationContext
}