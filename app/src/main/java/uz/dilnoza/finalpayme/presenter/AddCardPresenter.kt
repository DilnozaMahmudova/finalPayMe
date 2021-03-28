package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.AddCardContract
import uz.dilnoza.finalpayme.datas.AddCardData

class AddCardPresenter(private val model: AddCardContract.Model, private val view: AddCardContract.View):AddCardContract.Presenter {
    init {
        model.message(this::error)
        model.success(this::success)
    }
    override fun add() {
        val name=view.getName()
        val pan=view.getPan()
        val date=view.getDate()
        val color=view.getColor()
        if (name.length < 3 || name.length > 25) {
            view.showMessage("Name length is between 3 and 25","name")
        } else  if (pan.length!=16 ){
            view.showMessage("Card number is wrong","pan")
        }else if(date.length!=5){
            view.showMessage("Date is wrong","date")
        }else if (color in 7..-1) {
            view.showMessage("Error in the \"Color\" section","error")
        }
        else{
            view.openLoading()
            model.addCard(AddCardData(pan,date,name,color))

        }
    }
    private fun error(message: String) {
        view.showMessage(message,"error")
        view.closeLoading()
    }

    private fun success(message: String) {
        view.closeLoading()
        view.showMessage(message,"")
    }
}