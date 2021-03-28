package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_card_verify.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.VerifyCardContract
import uz.dilnoza.finalpayme.presenter.VerifyCardPresenter
import uz.dilnoza.finalpayme.repository.VerifyCardRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClientAuth
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog

class CardVerify : AppCompatActivity(),VerifyCardContract.View {
    private lateinit var presenter: VerifyCardPresenter
    private lateinit var pan:String
    private val api = ApiClientAuth.retrofit.create(CardApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_verify)
        pan = intent.getStringExtra("PAN")!!
        presenter = VerifyCardPresenter(VerifyCardRepository(api),this)
        verifyCard.setOnClickListener {
            presenter.verify()
        }
    }
    override fun getCode() = pinCard.text.toString()

    override fun getPan() = pan


    override fun openLoader() {
        viewCard.visibility = View.VISIBLE
        progressCard.show()

    }

    override fun closeLoader() {
        progressCard.hide()
        viewCard.visibility = View.GONE

    }


    override fun showMessage(message: String, error: String) {
        if (error == "code") {
            pinCard.setLineColor(Color.RED)
            codeCard.text = message
            codeCard.setTextColor(Color.RED)
        }
        if (error == "") {
            val dialog = MessageDialog(this, 0, message)
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
            dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
            dialog.show()
        }
    }

    override fun openHome() {
        val dialog = MessageDialog(this, 2, "You've completed Verify SuccessFully!")
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            startActivity(Intent(this, Card::class.java))
        }
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.dialog_button) }
        dialog.show()
    }
}