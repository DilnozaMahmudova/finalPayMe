package uz.dilnoza.finalpayme.sourse.retrofit

import retrofit2.Call
import retrofit2.http.*
import uz.dilnoza.finalpayme.datas.*


interface CardApi {
    /**
     * 1. Get all card
     * */
    @GET("card")
    fun getAll(): Call<ResponseData<List<CardData>>>

    /**
     * 2. add new a card
     * */
    @POST("card")
    fun add(@Body addCardData: AddCardData): Call<ResData>

    /**
     * 3. verify card
     * */
    @POST("card/verify")
    fun verify(@Body verifyCardData: VerifyCardData): Call<ResponseData<CardData>>


}