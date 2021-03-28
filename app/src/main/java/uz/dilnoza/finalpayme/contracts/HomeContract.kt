package uz.dilnoza.finalpayme.contracts

import uz.dilnoza.finalpayme.datas.CardData
import uz.dilnoza.finalpayme.utils.MessageData
import uz.dilnoza.finalpayme.utils.ResultData

import uz.dilnoza.finalpayme.utils.SingleBlock

interface HomeContract {

    interface Model {
        fun getAll(block:(ResultData<List<CardData>>)->Unit)
    }

    interface View {
        fun showMessage(message: MessageData)
        fun loadCard(ls: List<CardData>)
        fun openLoader()
        fun closeLoader()
    }

    interface Presenter {
        fun load()
    }
}
