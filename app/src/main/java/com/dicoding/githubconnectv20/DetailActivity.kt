package com.dicoding.githubconnectv20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubconnectv20.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val mData = ArrayList<UserProfile>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_text_1,
                R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Users>(EXTRA_USER) as Users

        detailViewModel = ViewModelProvider(this,
                ViewModelProvider.NewInstanceFactory())
                .get(DetailViewModel::class.java)
        detailViewModel = DetailViewModel()
        detailViewModel.setUser(user.name)


        detailViewModel.getDetailUsers().observe(this, {
            binding.apply {
                Glide.with(this@DetailActivity)
                        .load(it.avatar)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgItemPhotoDetail)
                tvItemNameDetail.text = it.name
                tvItemUsernameDetail.text = it.username
                tvItemCompanyDetail.text = it.company
            }
        })

        initTabLayout()
        //initView()
        //bindViewModel()

    }

    private fun initTabLayout() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }



}

    /*private fun initView() {



    }

    private fun bindViewModel() {

            }*/


    /*private fun setData(items: ArrayList<UserProfile>) {
        mData.clear()
        mData.addAll(items)
    }*/

    /*private fun bind(userItems: UserProfile) {
        with(binding) {
            Glide.with(this.root)
                    .load(userItems.avatar)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhotoDetail)
            binding.tvItemNameDetail.text = userItems.name
            binding.tvItemUsernameDetail.text = userItems.username
            binding.tvItemCompanyDetail.text = userItems.company
        }
    }*/

