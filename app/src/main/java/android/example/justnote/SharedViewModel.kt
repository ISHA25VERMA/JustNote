package android.example.justnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _color = MutableLiveData<String>()

    val color : LiveData<String> = _color

    fun saveColor(newColor : String){
        _color.value = newColor
    }

//    public fun ChangeColor(colorM : Int){
//        color.value = colorM
//    }
//
    public fun getValue() : LiveData<String> {
        return color
    }

}