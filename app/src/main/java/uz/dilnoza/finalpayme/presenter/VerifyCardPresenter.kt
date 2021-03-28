package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.VerifyCardContract
import uz.dilnoza.finalpayme.datas.VerifyCardData

class VerifyCardPresenter (private val model: VerifyCardContract.Model, private val view: VerifyCardContract.View):VerifyCardContract.Presenter {
    init {
        model.message(this::error)
        model.success(this::success)
    }
    override fun verify() {
        val pan=view.getPan()
        if (view.getCode().length!=6){
            view.showMessage("The code is incomplete","code")
        }else{
            view.openLoader()
            model.verify(VerifyCardData(pan,view.getCode()))
        }

    }
    private fun error(message: String) {
        view.showMessage(message,"")
        view.closeLoader()
    }

    private fun success(message: String) {
        view.closeLoader()
        view.openHome()
    }
}