import androidx.lifecycle.ViewModel
import com.artkachenko.core_api.base.BaseViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BaseViewModelImpl: ViewModel(), BaseViewModel {

    override val parentJob = Job()

    override val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    override val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    override val scope = CoroutineScope(SupervisorJob() + coroutineContext + exceptionHandler)

    override fun onCleared() {
        parentJob.cancel()
        super.onCleared()
    }
}