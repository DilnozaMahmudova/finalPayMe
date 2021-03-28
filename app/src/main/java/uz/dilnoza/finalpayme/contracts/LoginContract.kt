package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.LoginData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface LoginContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun login(data: LoginData)
        fun rememberMe(rem:Boolean,data: LoginData)
        fun setToken(token:String)
    }
    interface View {
        fun getPassword(): String
        fun getRememberMe(): Boolean
        fun openCards()
        fun getPhoneNumber(): String
        fun openLoader()
        fun closeLoader()
        fun showMessage(message: String,error:String)
        fun openRegistration()
        fun forgotPassword()

    }
    interface Presenter {
        fun logIn()
        fun register()
        fun forgotPass()
    }
}