package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.utils.SingleBlock
import uz.dilnoza.finalpayme.datas.ContactUserData


interface RegistrationContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun register(data: ContactUserData)
    }
    interface View{
        fun getLastName(): String
        fun getFirstName(): String
        fun getPhoneNumber(): String
        fun getPassword(): String
        fun openLoading()
        fun closeLoading()
        fun openSmsCode(phone:String)
        fun showMessage(message: String,error:String)
        fun getConfirmPassword():String
    }
    interface Presenter{
        fun reg()
    }
}