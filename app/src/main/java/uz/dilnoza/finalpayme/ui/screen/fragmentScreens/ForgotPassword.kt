package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.forgot_password.*
import kotlinx.android.synthetic.main.login.phone_number
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.ForgotContract
import uz.dilnoza.finalpayme.presenter.ForgotPresenter
import uz.dilnoza.finalpayme.repository.ForgotPasswordRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog


class ForgotPassword : AppCompatActivity(), ForgotContract.View {
    private lateinit var presenter:ForgotPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)
        presenter= ForgotPresenter(ForgotPasswordRepository(api),this)
        reset.setOnClickListener { presenter.reset() }

    }

    override fun getPhoneNumber(): String = "+998" + phone_numberFor.unmaskedText
    override fun openLoader() {
        viewFor.visibility = View.VISIBLE
        progressFor.show()
    }

    override fun closeLoader() {
        progressFor.hide()
        viewFor.visibility = View.GONE

    }

    override fun showMessage(message: String, error: String) {
        when (error) {
            "phone" -> PoN.error = message
            "" -> {
                val dialog = MessageDialog(this, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
                dialog.show()
            }
        }
    }

    override fun openVerify() {
        val dialog = MessageDialog(this, 1, "Please verify your phone number to proceed. \n Verification code sent to your registered phone number.")
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            startActivity(Intent(this, Verify::class.java).apply { putExtra("Phone","+998"+phone_number.unmaskedText) })
        }
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.dialog_button) }
        dialog.show()
    }
}