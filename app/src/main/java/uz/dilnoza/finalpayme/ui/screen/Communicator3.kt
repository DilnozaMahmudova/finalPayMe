package uz.dilnoza.finalpayme.ui.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator3: ViewModel(){

    val message = MutableLiveData<Any>()

    fun setMsgCommunicator(msg:String){
        message.value = msg
    }
}