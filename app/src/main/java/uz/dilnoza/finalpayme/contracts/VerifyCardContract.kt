package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.SmsCodeData
import uz.dilnoza.finalpayme.datas.VerifyCardData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface VerifyCardContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun verify(data: VerifyCardData)
    }
    interface View{
        fun getCode():String
        fun getPan():String
        fun openLoader()
        fun closeLoader()
        fun showMessage(message:String,error:String)
        fun openHome()
    }
    interface Presenter{
        fun verify()
    }
}