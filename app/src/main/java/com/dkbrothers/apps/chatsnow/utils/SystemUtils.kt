package com.dkbrothers.apps.chatsnow.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import loadPreference
import savePreference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.pow


fun registerNetworkCallback(context: Context) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val builder = NetworkRequest.Builder()
    connectivityManager!!.registerNetworkCallback(
        builder.build(),
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                // Network Available
                context.savePreference("isNetworkConnected",true)
            }


            override fun onLost(network: Network) {
                // Network Not Available
                context.savePreference("isNetworkConnected",false)
            }
        }
    )

}


fun openAppInGooglePlay(context: Context,urlApp: String){
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(urlApp)
        setPackage("com.android.vending")
    }
    context.startActivity(intent)
}


fun calculateIMC(context: Context): Double {
    //Paso las medidas a sistema metrico
    var height: Double = context.loadPreference("height", 0).toDouble()
    var weight: Double = context.loadPreference("weight", 0).toDouble()
    height = if (context.loadPreference("isUnitPies", false)) {
        //convert measurements
        (context.loadPreference(
            "pies",
            0
        ) * 12 + context.loadPreference(
            "pulgadas",
            0
        )) * .0254
    } else {
        //Convert to meters
        height / 100
    }
    if (!context.loadPreference("isUnitKg", true)) {
        //convert measurements
        weight *= 0.453592
    }
    return weight / height.pow(2.0) //
}


fun numRandom(desde: Int, hasta: Int): Int {
    return floor(Math.random() * (hasta - desde + 1) + desde).toInt()
}



/**
 * Metodo para convertir la primera letra en Mayuscula
 * */
fun String.firstCapitalLetter() : String {
    if(this.isEmpty())
        return this
    val strArray = this.trim().toLowerCase(Locale.getDefault())
        .split(" ").toTypedArray()
    val builder = StringBuilder()
    for (s in strArray) {
        if(s.length > 1){
            val oneLetter = s.substring(0,1)
            val cap = oneLetter
                .toUpperCase(Locale.getDefault())+ s.substring(1)
            builder.append("$cap ")
        }
    }
    return builder.toString()
}



/**
* Convierte text a string HTML
* */
fun String.toHtmlText() : Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}





/**
 * Cambiar el color de una palabra o frase que especifiques
 * @param textToChange text que quieres colorear
 * @param color color que le quieres poner al text especificado
 *
 * Ojo este metodo solo sirve para versiones Android 7 hacia arriba
 **/
fun String.changeColorOfText(textToChange:String, color:Int):SpannableStringBuilder {
    val spannable: Spannable = SpannableString(this)
    val str = spannable.toString()
    val iStart = str.indexOf(textToChange)
    val iEnd = iStart + textToChange.length
    val ssText = SpannableStringBuilder(this)
    ssText.setSpan(ForegroundColorSpan(color), iStart, iEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return ssText
}

/**
 * Valida que la sintaxis sea la de un correo
 **/
fun String.checkEmail():Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.clearText():String{
    return this.replace("'","")
        .replace("\"","").replace(",","")
}


/**
 *Este metodo convierte la fecha formato <yyyy-MM-dd'T'HH:mm> en formato
 * legible para el usuario <Dia Mes Año>
 *
 * Ejemplos de formatos
 * todo https://androidwave.com/format-datetime-in-android/
 * **/
fun String.toReadableFormat(newFormat:String):String{
    if(this.isEmpty())
        return ""
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm",
        Locale.getDefault()).parse(this)
    return SimpleDateFormat(newFormat,Locale.getDefault()).format(date!!.time)
}


fun getDayWeekToNumber(date:Date):Int{
    val c = Calendar.getInstance()
    c.time = date
    return c[Calendar.DAY_OF_WEEK]
}


fun agregarAnimacionAlCambiarColor(view: View, colorInicialId:Int, colorFinalId:Int){
    val color =
        arrayOf(
            ColorDrawable(ContextCompat.getColor(view.context,colorInicialId)),
            ColorDrawable(ContextCompat.getColor(view.context,colorFinalId))
        )
    val trans = TransitionDrawable(color)
    view.background = trans
    trans.startTransition(1500) // duration in miliseconds
}


