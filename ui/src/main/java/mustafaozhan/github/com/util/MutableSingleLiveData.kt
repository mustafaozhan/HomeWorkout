package mustafaozhan.github.com.util

import androidx.annotation.MainThread

@Suppress("RedundantOverride")
class MutableSingleLiveData<T> : SingleLiveData<T>() {

    public override fun postValue(value: T) {
        super.postValue(value)
    }

    @MainThread
    public override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    operator fun invoke(param: T) {
        value = param
    }
}
