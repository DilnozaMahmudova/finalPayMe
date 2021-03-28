package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.ResetPasswordData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface ResetPasswordContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun send(data: ResetPasswordData)
    }
    interface View{
        fun getCode():String
        fun getPhoneNumber():String
        fun getPassword():String
        fun getConfirmPassword():String
        fun openLoader()
        fun closeLoader()
        fun showMessage(message:String,error:String)
        fun openLogin()
    }
    interface Presenter{
        fun send()
    }
}