package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.VerifyCardContract
import uz.dilnoza.finalpayme.datas.CardData
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.datas.VerifyCardData
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.utils.SingleBlock

class VerifyCardRepository(private val api:CardApi):VerifyCardContract.Model {
    private var listenerSuccess: SingleBlock<String>? = null
    private var listenerMessage: SingleBlock<String>? = null
    override fun message(block: SingleBlock<String>) {
        listenerMessage = block
    }

    override fun success(block: SingleBlock<String>) {
        listenerSuccess = block
    }

    override fun verify(data: VerifyCardData) {
        api.verify(data).enqueue(object : Callback<ResponseData<CardData>> {
            override fun onResponse(
                call: Call<ResponseData<CardData>>,
                response: Response<ResponseData<CardData>>
            ) {
                val dataCode = response.body() ?: return
                if (dataCode.status == "OK") {
                    listenerSuccess?.invoke(dataCode.message)
                } else listenerMessage?.invoke(dataCode.message)
            }

            override fun onFailure(call: Call<ResponseData<CardData>>, t: Throwable) {
                listenerMessage?.invoke("Network Error")
            }
        })
    }
}