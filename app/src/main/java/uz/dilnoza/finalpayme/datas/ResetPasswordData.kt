package uz.dilnoza.finalpayme.datas

data class ResetPasswordData(val phoneNumber: String, val password: String, val code: String)