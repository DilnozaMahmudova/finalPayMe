package uz.dilnoza.finalpayme.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_color.view.*
import uz.dilnoza.finalpayme.R
import uz.dilnoza.finalpayme.utils.SingleBlock
import uz.dilnoza.finalpayme.utils.extensions.bindItem
import uz.dilnoza.finalpayme.utils.extensions.inflate


class PictureRecycler(
    private val ls: List<Int>
) :
    RecyclerView.Adapter<PictureRecycler.ViewHolder>() {
    var lastSelect = -1;
    private var listener: SingleBlock<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder((parent.inflate(R.layout.item_color)))

    override fun getItemCount() = ls.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind().apply {
        holder.itemView.radio.isChecked=position==lastSelect
    }
    fun setOnClickListener(block: SingleBlock<Int>) {
        notifyDataSetChanged()
        listener = block
    }
    fun select(position: Int,view: View){
       val click=View.OnClickListener {
           lastSelect=position
           notifyDataSetChanged()
       }
        view.setOnClickListener(click)
            view.radio.setOnClickListener(click)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind() = bindItem {
            setOnClickListener {
                select(adapterPosition,it)
            }
                    listener?.invoke(adapterPosition)
            back.setImageResource(ls[adapterPosition])
        }
    }
}
