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
import com.frhnfath.githubuserapp.activity.DetailActivity
import com.frhnfath.githubuserapp.adapter.UserAdapter
import com.frhnfath.githubuserapp.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var followingBinding: FragmentFollowingBinding? = null
    private val binding get() = followingBinding!!
    private lateinit var viewModel: FollowingFragmentViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followingBinding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inflate the layout for this fragment

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        Log.d("Following Fragment", "onViewCreated: $username")
        followingBinding = FragmentFollowingBinding.bind(view)
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()
        binding.apply {
            showProgressBar(true)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(requireContext())
            rvFollowing.adapter = userAdapter
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingFragmentViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                userAdapter.setList(it)
                showProgressBar(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        followingBinding = null
    }

    private fun showProgressBar(b: Boolean) {
        if (b) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

}