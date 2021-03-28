package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.SmsCodeData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface VerifyContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun send(data: SmsCodeData)
    }
    interface View{
        fun getCode():String
        fun getPhoneNumber():String
        fun openLoader()
        fun closeLoader()
        fun showMessage(message:String,error:String)
        fun openLogin()
    }
    interface Presenter{
        fun send()
    }
}