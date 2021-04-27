package mustafaozhan.github.com.ui.splash

import android.content.Intent
import android.os.Bundle
import mustafaozhan.github.com.base.BaseActivity
import mustafaozhan.github.com.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
