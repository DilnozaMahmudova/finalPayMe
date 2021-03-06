package uz.dilnoza.finalpayme.utils.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.putArgument(block:Bundle.()->Unit):Fragment{
    val bundle=arguments?: Bundle()
    block(bundle)
    arguments=bundle
    return this
}