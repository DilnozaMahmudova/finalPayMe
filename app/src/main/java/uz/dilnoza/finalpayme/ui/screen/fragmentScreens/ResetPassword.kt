package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.confirmPass
import kotlinx.android.synthetic.main.activity_reset_password.password
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.ResetPasswordContract
import uz.dilnoza.finalpayme.presenter.ResetPasswordPresenter
import uz.dilnoza.finalpayme.repository.ResetPasswordRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class ResetPassword : AppCompatActivity(),ResetPasswordContract.View {
    private lateinit var phoneNumber: String
    private lateinit var presenter: ResetPasswordPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        phoneNumber = intent.getStringExtra("Phone")!!
        presenter = ResetPasswordPresenter(ResetPasswordRepository(api),this)
        reset.setOnClickListener {
            presenter.send()
        }
    }
    override fun getCode() = pinV.text.toString()

    override fun getPhoneNumber() = phoneNumber

    override fun getPassword(): String = password.text.toString().trim().replace(" ","")
    override fun getConfirmPassword(): String = confirmPass.text.toString().trim()


    override fun openLoader() {
        viewR.visibility = View.VISIBLE
        progressR.show()

    }

    override fun closeLoader() {
        progressR.hide()
        viewR.visibility = View.GONE

    }


    override fun showMessage(message: String, error: String) {
        if (error == "code") {
            pinV.setLineColor(Color.RED)
            codeR.text = message
            codeR.setTextColor(Color.RED)
        }
        if (error== "password" ){ passW.error = message }
        if (error=="confirm" ){confPass.error = message }
        if (error == "") {
            val dialog = MessageDialog(this, 0, message)
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
            dialog.show()
        }
    }

    override fun openLogin() {
        val dialog = MessageDialog(this, 2, "You've completed reset password SuccessFully!")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ -> startActivity(Intent(this, Login::class.java)) }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.dialog_button) }
        dialog.show()
    }
}