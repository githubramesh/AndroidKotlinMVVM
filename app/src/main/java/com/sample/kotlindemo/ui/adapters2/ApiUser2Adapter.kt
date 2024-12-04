package com.sample.kotlindemo.ui.adapters2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.kotlindemo.data.model.ApiUser
import com.sample.kotlindemo.databinding.ItemLayout2Binding

class ApiUser2Adapter(private val users: ArrayList<ApiUser>) :
    RecyclerView.Adapter<ApiUser2Adapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemLayout2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ApiUser) {
            binding.apply {
                textViewUserName.text = user.name
                textViewUserEmail.text = user.email
                Glide.with(imageViewAvatar.context)
                    .load(user.avatar)
                    .into(imageViewAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemLayout2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<ApiUser>) {
        users.addAll(list)
    }
}