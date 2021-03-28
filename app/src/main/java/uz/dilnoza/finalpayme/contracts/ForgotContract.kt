package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.LoginData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface ForgotContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun reset(data: LoginData)
    }
    interface View{
        fun getPhoneNumber(): String
        fun openLoader()
        fun closeLoader()
        fun showMessage(message: String,error:String)
        fun openVerify()
    }
    interface Presenter{
        fun reset()
    }
}