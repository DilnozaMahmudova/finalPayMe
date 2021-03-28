package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.registration.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.RegistrationContract
import uz.dilnoza.finalpayme.repository.RegRepository
import uz.dilnoza.finalpayme.presenter.RegistrationPresenter
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog


class Registration : AppCompatActivity(), RegistrationContract.View {
    private lateinit var presenter: RegistrationPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        presenter = RegistrationPresenter(RegRepository(api), this)
        reg.setOnClickListener {
            presenter.reg()
        }
    }

    override fun getLastName(): String = lastName.text.toString()

    override fun getFirstName() = first_name.text.toString()

    override fun getPhoneNumber(): String = "+998" + phone_number.unmaskedText

    override fun getPassword(): String = password.text.toString().trim().replace(" ", "")
    override fun getConfirmPassword(): String = confirmPass.text.toString().trim()

    override fun openLoading() {
        view.visibility = View.VISIBLE
        progress.show()

    }

    override fun closeLoading() {
        progress.hide()
        view.visibility = View.GONE

    }

    override fun openSmsCode(phone: String) {
        val dialog = MessageDialog(
            this,
            1,
            "You've completed Registration SuccessFully! \n Please verify your phone number to proceed. \n Verification code sent to your registered phone number."
        )
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { _, _ ->
            startActivity(Intent(this, Verify::class.java).apply {
                putExtra(
                    "Phone",
                    "+998" + phone_number.unmaskedText
                )
            })
        }
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setBackgroundResource(R.drawable.dialog_button)
        }
        dialog.show()
    }

    override fun showMessage(message: String, error: String) {
        when (error) {
            "confirm" -> confPass.error = message
            "first" -> firstN.error = message
            "last" -> lastN.error = message
            "password" -> passW.error = message
            "phone" -> PN.error = message
            "" -> {
                val dialog = MessageDialog(this, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setBackgroundResource(R.drawable.dialog_shape)
                }
                dialog.show()
            }
        }
    }
}