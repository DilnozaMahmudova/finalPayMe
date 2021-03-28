package uz.dilnoza.finalpayme.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.view.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.LoginContract
import uz.dilnoza.finalpayme.presenter.LoginPresenter
import uz.dilnoza.finalpayme.repository.LoginRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class LoginFragment :Fragment(R.layout.login),LoginContract.View {
    private lateinit var presenter: LoginPresenter
    private lateinit var loginView:View
    private var condClose = true
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginView=view
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
        condClose = false
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayout,CardFragment(),"Card")?.addToBackStack("Login")?.commit()
    }

    override fun openLoader() {
        loginView.view.visibility=View.VISIBLE
        loginView.progress.show()
    }

    override fun closeLoader() {
        progress.hide()
        loginView.view.visibility=View.GONE

    }

    override fun openRegistration() {
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayout,RegistrationFragment(),"Registration")?.addToBackStack("Login")?.commit()
    }

    override fun forgotPassword() {
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentLayout,ForgotFragment(),"Forgot")?.addToBackStack("Login")?.commit()
    }

    override fun showMessage(message: String, error: String) {
        when(error){
            "phone"->PN.error=message
            "password"->{
                passW.error=message
            }
            ""-> {
                val dialog = MessageDialog(context!!, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
                dialog.show()
            }
        }
    }
    override fun onDetach() {
        super.onDetach()
        if (condClose)
            activity?.finish()
    }
}