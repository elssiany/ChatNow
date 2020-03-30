
import android.content.Context
import com.dkbrothers.apps.chatsnow.R


fun Context.savePreference(llave: String, valor: String) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(llave, valor)
    editor.apply()
}

fun Context.savePreference(llave: String, valor: Boolean) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
            Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(llave, valor)
    editor.apply()
}
fun Context.savePreference(llave: String, valor: Long) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
        Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putLong(llave, valor)
    editor.apply()
}


fun Context.savePreference(llave: String, valor: Int) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
            Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(llave, valor)
    editor.apply()
}


fun Context.loadPreference(llave: String, defValue: String): String {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getString(llave, defValue)!!
}

fun Context.loadPreference(llave: String, defValue: Boolean): Boolean {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(llave, defValue)
}

fun Context.loadPreference(llave: String): String {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getString(llave, "")!!
}

fun Context.loadPreference(llave: String, defValue: Long): Long {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getLong(llave, defValue)
}

fun Context.loadPreference(llave: String, defValue: Int): Int {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getInt(llave, defValue)
}


fun Context.deletePreference(llave: String) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove(llave)
    editor.apply()
}