package uz.dilnoza.finalpayme.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_card.*
import retrofit2.HttpException
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.contracts.HomeContract
import uz.dilnoza.finalpayme.datas.CardData
import uz.dilnoza.finalpayme.presenter.HomePresenter
import uz.dilnoza.finalpayme.repository.HomeRepository
import uz.dilnoza.finalpayme.sourse.retrofit.ApiClient
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.ui.adapter.CardAdapter
import uz.dilnoza.finalpayme.ui.dialog.MessageDialog
import uz.dilnoza.finalpayme.ui.transformers.Horizontal
import uz.dilnoza.finalpayme.utils.MessageData

class CardFragment : Fragment(R.layout.activity_card), HomeContract.View {
    private val adapter = CardAdapter()
    private val api = ApiClient.retrofit.create(CardApi::class.java)
    private lateinit var presenter: HomePresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = HomePresenter(HomeRepository(api), this)
        list.adapter = adapter
        list.layoutManager = Horizontal(context!!, LinearLayoutManager.HORIZONTAL, false)
        add.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentLayout, AddCardFragment(), "AddCard")?.addToBackStack("Card")
                ?.commit()
        }
        presenter.load()
    }

    override fun showMessage(message: MessageData) {
        var text = ""
        val pos = 0
        message.onMessage {
            text = it
        }.onResource {
            text = getString(it)
        }.onFailure {
            text = when (it) {
                is HttpException -> "Not connected to the internet!"
                else -> "Unspecified error. Please try again."
            }
        }
        val dialog = MessageDialog(context!!, pos, text)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit") { _, _ -> }
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setBackgroundResource(R.drawable.dialog_button)
        }
        dialog.show()
    }

    override fun loadCard(ls: List<CardData>) {
        adapter.submitList(ls)
    }

    override fun openLoader() {
        viewCar.visibility = View.VISIBLE
        progressCar.show()
    }

    override fun closeLoader() {
        progressCar.hide()
        viewCar.visibility = View.GONE

    }
}