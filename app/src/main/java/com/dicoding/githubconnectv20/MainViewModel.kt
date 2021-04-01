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

class MainViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setListUsers(user: String) {
        val listItems = ArrayList<Users>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$user"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray)
            {
                try{ //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val userItems = Users()
                        userItems.name = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")
                        listItems.add(userItems)
                    }
                    listUsers.postValue(listItems)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?)
            {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getListUsers(): LiveData<ArrayList<Users>> {
        return listUsers
    }
}