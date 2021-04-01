package com.dicoding.githubconnectv20

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel: ViewModel() {

   private val detailUserProfile = MutableLiveData<UserProfile>()

    fun setUser(user: String?) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$user"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                 //parsing json
                    val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val userProfile = UserProfile()
                        userProfile.username = responseObject.getString("login")
                        userProfile.name = responseObject.getString("name")
                        userProfile.avatar = responseObject.getString("avatar_url")
                        userProfile.company = responseObject.getString("company")
                    detailUserProfile.postValue(userProfile)
            }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?)
            {
                Log.d("onFailure", error?.message.toString())
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d("MainViewModel", errorMessage)
            }

        })
    }

    fun getDetailUsers(): LiveData<UserProfile> {
        return detailUserProfile
    }
}