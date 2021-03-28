package uz.dilnoza.finalpayme.datas

import androidx.recyclerview.widget.DiffUtil

data class CardData(
    var id:Int,
    val phoneNumber:String?,
    val pan:String?,
    val expireAt:String?,
    val name:String?,
    val color:Int?,
    val amount:Long
){
    companion object{
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<CardData>() {
            override fun areItemsTheSame(oldItem: CardData, newItem: CardData) = oldItem.pan == newItem.pan
            override fun areContentsTheSame(oldItem: CardData, newItem: CardData) = oldItem.name == newItem.name && oldItem.expireAt == newItem.expireAt
        }
    }

}