package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.ForgotContract
import uz.dilnoza.finalpayme.datas.LoginData

class ForgotPresenter(private val model: ForgotContract.Model, private val view: ForgotContract.View) :ForgotContract.Presenter{
    init {
        model.message(this::error)
        model.success(this::success)
    }

    override fun reset() {
        if (view.getPhoneNumber().length!=13) {
            view.showMessage("Phone number is wrong", "phone")
        } else {
            view.openLoader()
            model.reset(LoginData(view.getPhoneNumber(), "12345678"))
        }
    }

    private fun error(message: String) {
        view.showMessage(message, "")
        view.closeLoader()
    }

    private fun success(phone: String) {
        view.closeLoader()
        view.openVerify()

    }
}