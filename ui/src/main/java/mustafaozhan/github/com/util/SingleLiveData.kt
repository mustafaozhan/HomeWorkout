package mustafaozhan.github.com.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveData<T> : LiveData<T>() {
    protected val pending = AtomicBoolean(false)

    @Throws
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }
}