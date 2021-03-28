package uz.dilnoza.finalpayme.datas

data class ResponseData<T>(
    val status: String,
    val message: String = "Successful",
    val data: T? = null
)