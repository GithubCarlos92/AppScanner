package com.carlos.appscanner.model

import java.util.*

data class Data (
    var nombre: String = "",
    var fecha: String = "")
{
    companion object{
        fun getAutoCodigo():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }


}