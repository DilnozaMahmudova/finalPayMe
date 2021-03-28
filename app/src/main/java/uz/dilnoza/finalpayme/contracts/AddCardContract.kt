package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.AddCardData
import uz.dilnoza.finalpayme.datas.ContactUserData
import uz.dilnoza.finalpayme.utils.MessageData
import uz.dilnoza.finalpayme.utils.SingleBlock

interface AddCardContract {
    interface Model{
        fun message(block: SingleBlock<String>)
        fun success(block: SingleBlock<String>)
        fun addCard(addCardData: AddCardData)
    }
    interface View{
        fun getColor(): Int
        fun getName(): String
        fun getDate(): String
        fun getPan(): String
        fun openLoading()
        fun closeLoading()
        fun openCardVerify()
        fun showMessage(message: String,error:String)
    }
    interface Presenter{
        fun add()
    }
}