package com.dicoding.githubconnectv20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FollowingFragment : Fragment() {
    private lateinit var rvFollowing : RecyclerView
    private lateinit var adapter : FragmentListAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var fragmentViewModel: FragmentViewModel

    companion object{
        private const val USERNAME = "username"

        @JvmStatic
        fun instance(username: String?) =
                FollowingFragment().apply {
                    arguments = Bundle().apply {
                        putString(USERNAME,username)
                    }
                }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_following, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel = ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory())
                .get(FragmentViewModel::class.java)

        rvFollowing = view.findViewById(R.id.recycler_following_fragment)
        adapter = FragmentListAdapter()
        adapter.notifyDataSetChanged()

        fragmentViewModel.getListFollowing().observe(this, { userItems ->

            if (userItems != null) {
                adapter.setData(userItems)

            }
        })

        val dataUsername = arguments?.getString(USERNAME)
        fragmentViewModel = FragmentViewModel()
        fragmentViewModel.setListFollowing(dataUsername.toString())

        layoutManager = LinearLayoutManager(activity)
        rvFollowing.layoutManager = layoutManager
        adapter = FragmentListAdapter()
        adapter.notifyDataSetChanged()
        rvFollowing.adapter = adapter


    }

}