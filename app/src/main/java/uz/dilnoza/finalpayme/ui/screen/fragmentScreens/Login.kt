package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.LoginContract
import uz.dilnoza.finalpayme.presenter.LoginPresenter
import uz.dilnoza.finalpayme.repository.LoginRepository
import uz.dilnoza.finalpayme.sourse.local.shared.LocalStorage
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class Login : AppCompatActivity(),LoginContract.View {
    private lateinit var presenter:LoginPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    private var storage = LocalStorage.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        if (storage.getRemember()){
            openCards()
        }
        presenter= LoginPresenter(LoginRepository(api),this)
        login.setOnClickListener{ presenter.logIn()
        }
        registration.setOnClickListener { presenter.register() }
        forgot.setOnClickListener { presenter.forgotPass() }
    }


    override fun getPhoneNumber(): String="+998"+phone_number.unmaskedText

    override fun getPassword(): String=password.text.toString()

    override fun getRememberMe(): Boolean=checkbox.isChecked

    override fun openCards() {
       startActivity(Intent(this, Card::class.java))
    }

    override fun openLoader() {
        view.visibility=View.VISIBLE
        progress.show()
    }

    override fun closeLoader() {
        progress.hide()
        view.visibility=View.GONE

    }

    override fun openRegistration() {
        startActivity(Intent(this, Registration::class.java))
    }

    override fun forgotPassword() {
        startActivity(Intent(this, ForgotPassword::class.java))
    }

    override fun showMessage(message: String, error: String) {
        when(error){
            "phone"->PN.error=message
            "password"->{
                passW.error=message
            }
            ""-> {
                val dialog = MessageDialog(this, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
                dialog.show()
            }
        }
    }

}