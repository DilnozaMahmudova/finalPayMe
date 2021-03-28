package uz.dilnoza.finalpayme.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.datas.CardData
import uz.dilnoza.finalpayme.utils.SingleBlock
import uz.dilnoza.finalpayme.utils.extensions.bindItem
import uz.dilnoza.finalpayme.utils.extensions.inflate
import java.text.DecimalFormat

class CardAdapter : ListAdapter<CardData, CardAdapter.ViewHolder>(CardData.ITEM_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_card))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind() = bindItem {
            val d = getItem(adapterPosition)
            name.text = d.name
            pan.text = d.pan?.substring(0, 4) + " **** **** " + d.pan?.substring(12, 16)
            date.text = d.expireAt.toString()
            val formatter=DecimalFormat("#,###")
            amount.text =formatter.format( d.amount.toString()).replace(","," ")+" sum"
        }
    }
}