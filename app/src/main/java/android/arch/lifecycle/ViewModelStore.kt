package android.arch.lifecycle

import java.util.HashMap
import java.util.Iterator

class ViewModelStore {
    private val mMap = HashMap()

    public final Unit clear() {
        Iterator it = this.mMap.values().iterator()
        while (it.hasNext()) {
            ((ViewModel) it.next()).onCleared()
        }
        this.mMap.clear()
    }

    final ViewModel get(String str) {
        return (ViewModel) this.mMap.get(str)
    }

    final Unit put(String str, ViewModel viewModel) {
        ViewModel viewModel2 = (ViewModel) this.mMap.put(str, viewModel)
        if (viewModel2 != null) {
            viewModel2.onCleared()
        }
    }
}
