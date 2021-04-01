package com.dicoding.githubconnectv20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class FollowerFragment : Fragment() {


    private lateinit var rvFollower : RecyclerView
    private lateinit var adapter : FragmentListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var fragmentViewModel: FragmentViewModel
    companion object{
        private const val USERNAME = "username"

        @JvmStatic
        fun instance(username: String?) =
                FollowerFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME,username)
                    }
                }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_follower, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollower = view.findViewById(R.id.recycler_follower_fragment)
        adapter = FragmentListAdapter()
        adapter.notifyDataSetChanged()

        fragmentViewModel = ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory())
                .get(FragmentViewModel::class.java)


        fragmentViewModel.getListFollower().observe(this, { userItems ->

            if (userItems != null) {
                adapter.setData(userItems)

            }
        })

        val dataUsername = arguments?.getString(USERNAME)
        fragmentViewModel = FragmentViewModel()
        fragmentViewModel.setListFollower(dataUsername.toString())

        layoutManager = LinearLayoutManager(activity)
        rvFollower.layoutManager = layoutManager
        adapter = FragmentListAdapter()
        adapter.notifyDataSetChanged()
        rvFollower.adapter = adapter

    }


}