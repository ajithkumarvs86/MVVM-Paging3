package com.ak.paging3.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ak.paging3.R
import com.ak.paging3.databinding.LayoutUserRowBinding
import com.ak.paging3.model.User


/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */


class UsersListPagingAdapter(private val context: Context, private val itemClick: AdapterItemClick<User>): PagingDataAdapter<User,UsersListPagingAdapter.UsersListViewHolder>(UserComparator) {


    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bindData(getItem(position),context,itemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding = LayoutUserRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UsersListViewHolder(binding)
    }

    class UsersListViewHolder(binding: LayoutUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private val userImage = binding.imgUser
        private val fullName = binding.txtFullName
        private val mail = binding.txtEmail
        private val mobile = binding.txtMobile
        private val container = binding.lytContainer
        private val university = binding.txtUniversity
            fun bindData(data: User?,context: Context,itemClick: AdapterItemClick<User>) {

                data?.let {
                    Glide.with(context)
                        .load(data.image)
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_broken_image)
                        .into(userImage)

                    fullName.text = data.fullName
                    mail.text = data.email
                    mobile.text = data.phone
                    university.text  = data.university
                    container.setOnClickListener {
                        itemClick.onItemClick(data)
                    }
                }
            }
    }

    object UserComparator: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}