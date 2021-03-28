package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.ResetPasswordContract
import uz.dilnoza.finalpayme.datas.ResetPasswordData
import uz.dilnoza.finalpayme.datas.SmsCodeData

class ResetPasswordPresenter(private val model: ResetPasswordContract.Model, private val view: ResetPasswordContract.View):ResetPasswordContract.Presenter {
    init {
        model.message(this::error)
        model.success(this::success)
    }
    override fun send() {
        val password=view.getPassword()
        val confirm=view.getConfirmPassword()
        val phone=view.getPhoneNumber()
        if (phone.length!=13 ){
            view.showMessage("phone number is wrong","phone")
        }else if (view.getCode().length!=6){
            view.showMessage("The code is incomplete","code")
        }else if(password.length<6||password.length>20){
            view.showMessage("password length is between 6 and 20","password")
        }else if(confirm.length<6||confirm.length>20){
            view.showMessage("password length is between 6 and 20","confirm")
        }else if (confirm !=password) {
            view.showMessage("password and confirm password must be the same","confirm")
        }else{
            view.openLoader()
            model.send(ResetPasswordData(phone,password,view.getCode()))
        }

    }
    private fun error(message: String) {
        view.showMessage(message,"")
        view.closeLoader()
    }

    private fun success(phone: String) {
        view.closeLoader()
        view.openLogin()
    }
}