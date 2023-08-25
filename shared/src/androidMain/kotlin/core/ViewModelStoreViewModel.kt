package core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import com.rickclephas.kmm.viewmodel.KMMViewModel

class ViewModelStoreViewModel : ViewModel() {

    private val viewModelStores: HashMap<String, ViewModelStore> = HashMap(emptyMap())

    fun clearDestination(destinationId: String) {
        viewModelStores.firstNotNullOfOrNull { (destinationIdentifier, destinationViewModelStore) ->
            if (destinationIdentifier == destinationId) {
                destinationViewModelStore.clear()
                viewModelStores.remove(destinationIdentifier)
                return
            }
        }
    }

    fun <VM : KMMViewModel> getViewModel(destinationIdentifier: String, viewModelClazz: Class<VM>): VM? {
        return viewModelStores[destinationIdentifier]?.get(viewModelClazz.storeId()) as? VM
    }

    fun putViewModel(destinationIdentifier: String, viewModel: ViewModel) {

        var viewModelStore = viewModelStores.entries.firstOrNull { (key, _) -> key == destinationIdentifier }?.value

        if (viewModelStore == null) {
            viewModelStore = ViewModelStore()
            viewModelStores[destinationIdentifier] = viewModelStore
        }

        viewModelStore.put(viewModel::class.java.storeId(), viewModel)
    }

    override fun onCleared() {
        viewModelStores.forEach { (_, value) -> value.clear() }
        viewModelStores.clear()
        super.onCleared()
    }

    private fun Class<*>.storeId() = (this::class.java.`package`?.name ?: "") + this::class.java.name
}
