package apps.ranganathan.configlibrary.utils

import android.content.SharedPreferences

/*simple class for SharePreferences
* base Prefs's functions
*/
open class PrefsManger(private val sharedPreferences: SharedPreferences) {


    /*insert*/
    fun putData(key: String, value: Any) {
        when (value){
            is String ->sharedPreferences.edit().putString(key, value).apply()
            is Int ->sharedPreferences.edit().putInt(key, value).apply()
            is Float ->sharedPreferences.edit().putFloat(key, value).apply()
            is Long ->sharedPreferences.edit().putLong(key, value).apply()
            is Boolean ->sharedPreferences.edit().putBoolean(key, value).apply()
        }
    }

    /*retrieve*/
    fun getData(key: String,type: Any) : Any? {

        return when (type){
            is String ->sharedPreferences.getString(key, "")
            is Int ->sharedPreferences.getInt(key,0)
            is Float ->sharedPreferences.getFloat(key, 0f)
            is Long ->sharedPreferences.getLong(key, 0)
            is Boolean ->sharedPreferences.getBoolean(key, false)
            else ->{
                sharedPreferences.getString(key, "")
            }
        }


    }

    /*insert string*/
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /*insert string*/
    fun putint(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
}