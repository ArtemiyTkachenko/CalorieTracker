import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearedDataBinding<T : ViewBinding> : ReadWriteProperty<Fragment, T>,
    LifecycleObserver {
    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        _value ?: throw IllegalStateException("Trying to call an auto-cleared value outside of the view lifecycle.")

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        _value = null
    }
}

@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadWriteProperty<Fragment, T> {
    return AutoClearedDataBinding()
}