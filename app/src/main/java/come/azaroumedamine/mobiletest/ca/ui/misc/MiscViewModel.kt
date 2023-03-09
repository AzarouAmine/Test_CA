package come.azaroumedamine.mobiletest.ca.ui.misc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MiscViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is misc Fragment"
    }
    val text: LiveData<String> = _text
}