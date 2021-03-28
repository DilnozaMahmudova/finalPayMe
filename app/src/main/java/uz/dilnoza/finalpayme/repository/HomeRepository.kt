package uz.dilnoza.finalpayme.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.dilnoza.finalpayme.contracts.HomeContract
import uz.dilnoza.finalpayme.datas.CardData
import uz.dilnoza.finalpayme.datas.ResponseData
import uz.dilnoza.finalpayme.sourse.retrofit.CardApi
import uz.dilnoza.finalpayme.utils.ResultData

class HomeRepository(private val api:CardApi): HomeContract.Model {
    override fun getAll(block: (ResultData<List<CardData>>) -> Unit) {
        api.getAll().enqueue(object :Callback<ResponseData<List<CardData>>>{
            override fun onResponse(
                call: Call<ResponseData<List<CardData>>>,
                response: Response<ResponseData<List<CardData>>>
            ) {
                val data=response.body()
                when{
                    data==null->block(ResultData.message("Empty result returned"))
                    data.status=="ERROR"->block(ResultData.message(data.message))
                    data.status=="OK"->block(ResultData.data(data.data?: emptyList()))
                }
            }

            override fun onFailure(call: Call<ResponseData<List<CardData>>>, t: Throwable) {
                block(ResultData.failure(t))
            }

        })
    }

}