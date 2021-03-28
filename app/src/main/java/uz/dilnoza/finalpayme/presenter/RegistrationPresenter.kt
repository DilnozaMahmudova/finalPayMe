package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.RegistrationContract
import uz.dilnoza.finalpayme.datas.ContactUserData

class RegistrationPresenter(
    private val model: RegistrationContract.Model,
    private val view: RegistrationContract.View
) : RegistrationContract.Presenter {
    init {
        model.message(this::error)
        model.success(this::success)
    }
    override fun reg() {
        val firstName=view.getFirstName()
        val lastName=view.getLastName()
        val phoneNumber=view.getPhoneNumber()
        val password=view.getPassword()
        val confirm=view.getConfirmPassword()
        if (lastName.length < 3 || lastName.length > 15) {
            view.showMessage("lastName length is between 4 and 10","last")
        }else if (firstName.length < 3 || firstName.length > 15) {
            view.showMessage("firstName length is between 4 and 15","first")
        } else  if (phoneNumber.length!=13 ){
            view.showMessage("phone number is wrong","phone")
        }else if(password.length<6||password.length>20){
            view.showMessage("password length is between 6 and 20","password")
        }else if(confirm.length<6||confirm.length>20){
        view.showMessage("password length is between 6 and 20","confirm")
    }else if (confirm !=password) {
            view.showMessage("password and confirm password must be the same","confirm")
        }
        else{
            view.openLoading()
            model.register(ContactUserData(phoneNumber, password, lastName, firstName))

        }
    }
    private fun error(message: String) {
        view.showMessage(message,"")
        view.closeLoading()
    }

    private fun success(phone: String) {
        view.closeLoading()
        view.openSmsCode(phone)
    }

}