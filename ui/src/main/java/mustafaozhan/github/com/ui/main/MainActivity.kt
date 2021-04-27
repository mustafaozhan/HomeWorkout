package mustafaozhan.github.com.ui.main

import android.os.Bundle
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mustafaozhan.github.com.base.BaseActivity
import mustafaozhan.github.com.ui.R
import mustafaozhan.github.com.util.showSnack

class MainActivity : BaseActivity() {

    companion object {
        internal const val BACK_DELAY: Long = 2000
    }

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if (findNavController(containerId).currentDestination?.id == R.id.exerciseFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            doubleBackToExitPressedOnce = true
            showSnack(findViewById(containerId), R.string.click_back_again_to_exit)

            lifecycle.coroutineScope.launch {
                delay(BACK_DELAY)
                doubleBackToExitPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }
}
