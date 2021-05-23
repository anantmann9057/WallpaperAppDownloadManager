package com.example.downloadcoroutines.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val USER_PREFS = "taw_prefs"
    private val IS_FIRST_TIME = "isFirstTime"
    private val RESUME_PREF = "resume_pref"

    private val timePref = "timePref"
    private val userToken = "userToken"
    private val userPhone = "userPhone"
    private val pincode = "pincode"
    private val user = "user"
    private val url = "url"
    private var appSharedPrefs: SharedPreferences =
        context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE)
    private var prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()


    /* ---------------- ------------------------*/
    fun setPinCode(pincode: String?) {
        prefsEditor.putString(this.pincode, pincode!!).apply()
    }

    fun getPinCode(): String? {
        return appSharedPrefs.getString(pincode, "")
    }


    /* ---------------- ------------------------*/
    fun setUserToken(userToken: String?) {
        prefsEditor.putString(this.userToken, userToken).apply()
    }

    fun getUserToken(): String? {
        return appSharedPrefs.getString(userToken, "")
    }

    /* ---------------- ------------------------*/
    fun setUserPhone(userPhone: String?) {
        prefsEditor.putString(this.userPhone, userPhone).apply()
    }

    fun getUserPhone(): String? {
        return appSharedPrefs.getString(userPhone, "")
    }
    /* ---------------- ------------------------*/

    fun setUrl(url: String?) {
        prefsEditor.putString(this.url, url).apply()
    }

    fun getUrl(): String? {
        return appSharedPrefs.getString(url, "")
    }

    /*fun setUserDetail(user: User?) {
        val gson = Gson()
        val type = object : TypeToken<User?>() {}.type
        val json = gson.toJson(user, type)
        prefsEditor!!.putString(this.user, json).commit()
    }

    fun getUserDetail(): User? {
        val model = appSharedPrefs!!.getString(user, "")
        val gson = Gson()
        val type = object : TypeToken<User?>() {}.type
        return gson.fromJson(model, type)
    }*/

    fun setTime(range: String?) {
        prefsEditor.putString(timePref, range).commit()
    }

    fun getTime(): String? {
        return appSharedPrefs.getString(timePref, "") // 0 high , 1 medium ,  2 low
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefsEditor.putBoolean(IS_FIRST_TIME, isFirstTime).apply()
    }

    fun isFirstTimeLaunch(): Boolean {
        return appSharedPrefs.getBoolean(IS_FIRST_TIME, true)
    }

    /* --- Resume sharing popUp preference ---- */
    fun setResumePrefChecked(isResumePrefChecked: Boolean) {
        prefsEditor.putBoolean(RESUME_PREF, isResumePrefChecked).apply()
    }

    fun getResumePrefChecked(): Boolean {
        return appSharedPrefs.getBoolean(RESUME_PREF, false)
    }

    fun clear() {
        val isFirstTimeLaunch = isFirstTimeLaunch()
        prefsEditor.clear().commit()
        setFirstTimeLaunch(isFirstTimeLaunch)
    }
}