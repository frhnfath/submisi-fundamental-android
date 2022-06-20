package com.frhnfath.githubuserapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.frhnfath.githubuserapp.R
import com.frhnfath.githubuserapp.activity.DetailActivity
import com.frhnfath.githubuserapp.adapter.UserAdapter
import com.frhnfath.githubuserapp.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var followersBinding: FragmentFollowersBinding? = null
    private val binding get() = followersBinding!!
    private lateinit var viewModel: FollowersFragmentViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followersBinding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        Log.d("Followers Fragment", "onViewCreated: $username")
        followersBinding = FragmentFollowersBinding.bind(view)
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        binding.apply {
            showProgressBar(true)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = userAdapter
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersFragmentViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.getFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                userAdapter.setList(it)
                showProgressBar(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followersBinding = null
    }

    private fun showProgressBar(b: Boolean) {
        if (b) {
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }
}