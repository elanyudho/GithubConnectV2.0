package com.dicoding.githubconnectv20

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {


    var nameFollower: String? = null
    var nameFollowing: String? = null

    fun getNameFollower(name: String?): String?{
        nameFollower = name
        return nameFollower

    }
    fun getNameFollowing(name: String?): String?{
        nameFollowing = name
        return nameFollowing
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.instance(nameFollower)
            1 -> fragment = FollowerFragment.instance(nameFollowing)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }


}