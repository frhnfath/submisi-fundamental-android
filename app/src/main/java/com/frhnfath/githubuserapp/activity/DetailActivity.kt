package com.frhnfath.githubuserapp.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.frhnfath.githubuserapp.R
import com.frhnfath.githubuserapp.adapter.SectionsPagerAdapter
import com.frhnfath.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = Bundle()

        supportActionBar?.elevation = 0f

        supportActionBar?.title = "Detail Page"

        viewModel = ViewModelProvider(this).get(
            DetailViewModel::class.java
        )
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        bundle.putString(EXTRA_USERNAME, username)
        viewModel.setUser(username!!)
        viewModel.user.observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .into(imgDetail)
                    tvDetailName.text = it.name
                    tvDetailUsername.text = it.login
                    repositoryNumber.text = it.public_repos.toString()
                    companyInfo.text = it.company
                    locationInfo.text = it.location
                    followingNumber.text = it.following.toString()
                    followersNumber.text = it.followers.toString()
                }
            }
        }

        var state: Boolean = false

        binding.favoriteSwitch.setOnClickListener {
            Log.d("Button", "Clicked")
            state = !state
            if (state) {
                if (avatar != null) {
                    viewModel.addFavorite(username, id, avatar)
                    Toast.makeText(this@DetailActivity, "$username added to Favorites", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "Added")
                }
            } else {
                viewModel.deleteFavorite(username)
                Log.d("Delete", "Success")
                Toast.makeText(this@DetailActivity, "$username has been deleted from Favorites", Toast.LENGTH_SHORT).show()
            }
            binding.favoriteSwitch.isChecked = state
        }

        CoroutineScope(Dispatchers.IO).launch {
            val detect = viewModel.checkFavorite(username)
            withContext(Dispatchers.Main) {
                state = detect > 0
                binding.favoriteSwitch.isChecked = state
            }
        }

        val viewPager = binding.viewPager
        val tabs = binding.tabs
        Log.d("Detail Activity", "onCreate: $bundle")
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
        }
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_COMPANY = "extra_company"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}

