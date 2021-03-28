package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.VerifyContract
import uz.dilnoza.finalpayme.datas.SmsCodeData
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage

class VerifyPresenter(private val model: VerifyContract.Model,private val view: VerifyContract.View):VerifyContract.Presenter {
    private var storage = LocalStorage.getInstance()
    init {
        model.message(this::error)
        model.success(this::success)
    }
    override fun send() {
        val phone=view.getPhoneNumber()
        if (view.getCode().length!=6){
            view.showMessage("The code is incomplete","code")
        }else{
            view.openLoader()
            model.send(SmsCodeData(phone,view.getCode()))
        }

    }
    private fun error(message: String) {
        view.showMessage(message,"")
        view.closeLoader()
    }

    private fun success(token: String) {
        view.closeLoader()
        view.openLogin()
    }
}