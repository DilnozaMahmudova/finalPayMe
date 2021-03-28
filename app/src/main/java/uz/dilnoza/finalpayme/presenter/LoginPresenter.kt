package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.LoginContract
import uz.dilnoza.finalpayme.datas.LoginData

class LoginPresenter(private val model: LoginContract.Model, private val view: LoginContract.View) :
    LoginContract.Presenter {


    init {
        model.message(this::error)
        model.success(this::success)
    }

    override fun logIn() {
        if (view.getPhoneNumber().length!=13) {
            view.showMessage("Phone number is wrong", "phone")
        } else if (view.getPassword().length < 6 || view.getPassword().length > 20) {
            view.showMessage("password length is between 6 and 20", "password")
        } else {
            view.openLoader()
            model.login(LoginData(view.getPhoneNumber(), view.getPassword()))
        }
    }

    override fun register() {
        view.openRegistration()
    }

    override fun forgotPass() {
        view.forgotPassword()
    }

    private fun error(message: String) {
        view.showMessage(message, "")
        view.closeLoader()
    }

    private fun success(token: String) {
        if (view.getRememberMe()) model.rememberMe(view.getRememberMe(), LoginData(view.getPhoneNumber(), view.getPassword()))
        model.setToken(token)
        view.closeLoader()
        view.openCards()

    }
}