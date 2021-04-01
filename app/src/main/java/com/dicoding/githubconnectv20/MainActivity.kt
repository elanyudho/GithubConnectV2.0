package com.dicoding.githubconnectv20

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubconnectv20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecylerView()
    }

    private fun showRecylerView(){
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        setDataToAdapter()

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedItem(data)

            }
        })
    }

    private fun setDataToAdapter(){
        mainViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        mainViewModel.getListUsers().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                mainViewModel.setListUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
    private fun showSelectedItem(user: Users){
        Toast.makeText(this, "Show " + user.name + " profile", Toast.LENGTH_SHORT).show()
        val moveToIntentDetail = Intent(this@MainActivity, DetailActivity::class.java)
        moveToIntentDetail.putExtra(DetailActivity.EXTRA_USER,user)
        startActivity(moveToIntentDetail)

        /*val mListFollowerFragment = FollowerFragment()
        val nameUser = user.name
        mListFollowerFragment.userName = nameUser

        val mListFollowingFragment = FollowingFragment()
        mListFollowingFragment.userName = nameUser*/

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.getNameFollower(user.name)
        sectionsPagerAdapter.getNameFollowing(user.name)

    }


}