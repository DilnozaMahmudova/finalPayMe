package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.verify.*
import kotlinx.android.synthetic.main.verify.progress
import kotlinx.android.synthetic.main.verify.view
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.VerifyContract
import uz.dilnoza.finalpayme.presenter.VerifyPresenter
import uz.dilnoza.finalpayme.repository.VerifyRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.LoginApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class Verify : AppCompatActivity(), VerifyContract.View {
    private lateinit var phoneNumber: String
    private lateinit var presenter: VerifyPresenter
    private val api = ApiClientAuth.retrofit.create(LoginApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify)
        phoneNumber = intent.getStringExtra("Phone")!!
        presenter = VerifyPresenter(VerifyRepository(api), this)
        verify.setOnClickListener {
            presenter.send()
        }
    }

    override fun getCode() = pin.text.toString()

    override fun getPhoneNumber() = phoneNumber


    override fun openLoader() {
        view.visibility = View.VISIBLE
        progress.show()

    }

    override fun closeLoader() {
        progress.hide()
        view.visibility = View.GONE

    }


    override fun showMessage(message: String, error: String) {
        if (error == "code") {
            pin.setLineColor(Color.RED)
            code.text = message
            code.setTextColor(Color.RED)
        }
        if (error == "") {
            val dialog = MessageDialog(this, 0, message)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
            dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
            dialog.show()
        }
    }

    override fun openLogin() {
        val dialog = MessageDialog(this, 2, "You've completed Verify SuccessFully!")
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            startActivity(Intent(this, Card::class.java))
        }
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.dialog_button) }
        dialog.show()
    }

}