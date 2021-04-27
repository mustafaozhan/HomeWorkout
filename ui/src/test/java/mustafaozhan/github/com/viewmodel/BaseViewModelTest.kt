package mustafaozhan.github.com.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mustafaozhan.github.com.rule.TestCoroutineRule
import org.junit.Rule

abstract class BaseViewModelTest<ViewModelType> {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    protected abstract var viewModel: ViewModelType
}
