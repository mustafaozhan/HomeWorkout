package mustafaozhan.github.com.di.view

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mustafaozhan.github.com.ui.main.MainActivity
import mustafaozhan.github.com.ui.splash.SplashActivity

@Suppress("unused")
@Module
abstract class ActivityInjectionModule {

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity
}
