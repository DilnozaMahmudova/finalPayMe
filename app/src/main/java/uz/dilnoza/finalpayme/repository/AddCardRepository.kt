package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.AddCardContract
import uz.dilnoza.finalpayme.datas.AddCardData
import uz.dilnoza.finalpayme.datas.ContactUserData
import uz.dilnoza.finalpayme.datas.ResData
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.utils.SingleBlock

class AddCardRepository(private val api:CardApi):AddCardContract.Model {
    private var listenerSuccess:SingleBlock<String>?=null
    private var listenerMessage:SingleBlock<String>?=null
    override fun message(block: SingleBlock<String>){
        listenerMessage=block
    }

    override fun success(block: SingleBlock<String>) {
        listenerSuccess=block
    }


    override fun addCard(addCardData: AddCardData) {
        api.add(addCardData).enqueue(object : Callback<ResData> {
            override fun onResponse(call: Call<ResData>, response: Response<ResData>) {
                val d=response.body()?:return
                if (d.status=="OK"){
                    listenerSuccess?.invoke(d.message)
                }else
                    listenerMessage?.invoke(d.message)
            }

            override fun onFailure(call: Call<ResData>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }

        })
    }
}