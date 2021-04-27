package mustafaozhan.github.com.homeworkout.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import mustafaozhan.github.com.di.AppDatabaseModule
import mustafaozhan.github.com.di.scope.ActivityScope
import mustafaozhan.github.com.di.scope.FragmentScope
import mustafaozhan.github.com.di.view.ActivityInjectionModule
import mustafaozhan.github.com.di.view.FragmentInjectionModule
import mustafaozhan.github.com.di.view.GlideModule
import mustafaozhan.github.com.di.viewmodel.ViewModelModule
import mustafaozhan.github.com.homeworkout.HomeWorkoutApp
import javax.inject.Singleton

@Singleton
@ActivityScope
@FragmentScope
@Component(
    modules = [
        // Android
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        // App
        ApplicationModule::class,
        // UI
        ActivityInjectionModule::class,
        FragmentInjectionModule::class,
        ViewModelModule::class,
        GlideModule::class,
        // Data
        AppDatabaseModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: HomeWorkoutApp): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: HomeWorkoutApp)
}