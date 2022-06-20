package com.frhnfath.githubuserapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.frhnfath.githubuserapp.ViewModelFactory
import com.frhnfath.githubuserapp.adapter.UserAdapter
import com.frhnfath.githubuserapp.databinding.ActivityFavoriteBinding
import com.frhnfath.githubuserapp.local.Favorite
import com.frhnfath.githubuserapp.response.UserResponse

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = obtainViewModel(this@FavoriteActivity)

        val adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFavorite.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_NAME, data.name)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel.getAllFavorites().observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(list: List<Favorite>): ArrayList<UserResponse> {
        val listFav = ArrayList<UserResponse>()
        for (user in list) {
            val mapuser = UserResponse(
                user.login,
                user.id,
                user.avatar_url
            )
            listFav.add(mapuser)
        }
        return listFav
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}