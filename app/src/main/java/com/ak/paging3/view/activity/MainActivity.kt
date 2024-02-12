package com.ak.paging3.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.ak.paging3.databinding.ActivityMainBinding
import com.ak.paging3.model.User
import com.ak.paging3.utils.isNetworkAvailable
import com.ak.paging3.view.adapter.AdapterItemClick
import com.ak.paging3.view.adapter.UsersListPagingAdapter
import com.ak.paging3.viewmodel.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

@AndroidEntryPoint
class MainActivity : BaseActivity(),AdapterItemClick<User> {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: UsersListPagingAdapter
    private val viewModel :UserDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersListPagingAdapter(this, this)
        binding.listUsers.adapter = adapter

        if (isNetworkAvailable(this)){
            initApiCall()
        }else{
            binding.txtNoData.visibility = View.VISIBLE
        }

        binding.lytSwipe.setOnRefreshListener {
            initApiCall()
            binding.lytSwipe.isRefreshing = false
        }
        binding.txtNoData.setOnClickListener {
            initApiCall()
        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                binding.progressDialog.isVisible = !binding.lytSwipe.isRefreshing
            else {
                binding.progressDialog.isVisible = false

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    binding.txtNoData.visibility = View.VISIBLE
                    binding.listUsers.visibility = View.GONE
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }


    private fun initApiCall(){
        binding.progressDialog.isVisible = true
        lifecycleScope.launch {
            viewModel.getAllUser().observe(this@MainActivity){
                it.let {
                    binding.txtNoData.visibility = View.GONE
                    binding.listUsers.visibility = View.VISIBLE
                    adapter.submitData(lifecycle,it)
                }
            }
        }
    }

    override fun onItemClick(id: User) {
        val intent = Intent(this,UserDetailsActivity::class.java)
        intent.putExtra("UserId",id.id)
        startActivity(intent)
    }


    

}