package mustafaozhan.github.com.homeworkout

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import mustafaozhan.github.com.homeworkout.di.DaggerApplicationComponent
import javax.inject.Inject

class HomeWorkoutApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
