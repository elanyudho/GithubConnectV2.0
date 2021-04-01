package com.dicoding.githubconnectv20

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FragmentViewModel : ViewModel() {
    private val listFollower = MutableLiveData<ArrayList<RvUser>>()
    private val listFollowing = MutableLiveData<ArrayList<RvUser>>()

    fun setListFollower(user: String?) {
        val listItems = ArrayList<RvUser>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$user/followers"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray)
            {
                try{ //parsing json
                    val result = String(responseBody)
                    Log.d("ViewModel", result.toString())
                    val responseObject = JSONArray(result)
                    for (i in 0 until responseObject.length()){
                        val item = responseObject.getJSONObject(i)
                        val userItems = RvUser()
                        userItems.name = item.getString("name")
                        userItems.avatar = item.getString("avatar_url")
                    }
                    listFollower.postValue(listItems)
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

    fun getListFollower(): LiveData<ArrayList<RvUser>> {
        return listFollower
    }

    fun setListFollowing(user: String?) {
        val listItems = ArrayList<RvUser>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$user/following"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray)
            {
                try{ //parsing json
                    val result = String(responseBody)
                    Log.d("ViewModel", result.toString())
                    val responseObject = JSONArray(result)
                    for (i in 0 until responseObject.length()){
                        val item = responseObject.getJSONObject(i)
                        val userItems = RvUser()
                        userItems.name = item.getString("name")
                        userItems.avatar = item.getString("avatar_url")

                        listItems.add(userItems)
                    }
                    listFollower.postValue(listItems)
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

    fun getListFollowing(): LiveData<ArrayList<RvUser>> {
        return listFollowing
    }
}