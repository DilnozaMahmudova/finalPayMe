package uz.dilnoza.finalpayme.ui.screen.fragmentScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_add_card.date
import kotlinx.android.synthetic.main.activity_add_card.list
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.AddCardContract
import uz.dilnoza.finalpayme.presenter.AddCardPresenter
import uz.dilnoza.finalpayme.repository.AddCardRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClient
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.ui.adapter.PictureRecycler
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog
import uz.dilnoza.finalpayme.ui.transformers.Horizontal


class AddCard : AppCompatActivity(), AddCardContract.View {
    private lateinit var presenter: AddCardPresenter
    private var color = 0
    private val ls = listOf(
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6,
        R.drawable.card7
    )
    private val adapter = PictureRecycler(ls)
    private val api = ApiClient.retrofit.create(CardApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        presenter = AddCardPresenter(AddCardRepository(api), this)
        list.adapter = adapter
        list.layoutManager = Horizontal(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.setOnClickListener { color = it }
        addCard.setOnClickListener { presenter.add() }
    }


    override fun getColor(): Int = color

    override fun getName(): String = full.text.toString().trim()

    override fun getDate(): String =date.text.toString()

    override fun getPan(): String ="8600"+ cardNumber.unmaskedText

    override fun openLoading() {
        viewAdd.visibility = View.VISIBLE
        progressAdd.show()

    }

    override fun closeLoading() {
        progressAdd.hide()
        viewAdd.visibility = View.GONE

    }

    override fun openCardVerify() {
        startActivity(Intent(this, CardVerify::class.java).putExtra("PAN", "8600"+cardNumber.unmaskedText))
    }

    override fun showMessage(message: String, error: String) {
        when (error) {
            "name" -> cardName.error = message
            "pan" -> CN.error = message
            "date" -> d.error = message
            "error" -> {
                val dialog = MessageDialog(this, 0, message)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
                dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.dialog_button) }
                dialog.show()
            }
            "" -> {
                val dialog = MessageDialog(
                    this,
                    1,
                    message
                )
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
                    openCardVerify()
                }
                dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.dialog_button) }
                dialog.show()
            }
        }
    }
}