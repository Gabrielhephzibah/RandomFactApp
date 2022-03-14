package com.cherish.randomfactproject.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cherish.randomfactproject.data.model.StoreItemList
import com.cherish.randomfactproject.databinding.StoreItemLayoutBinding

class HomeAdapter(private val onClick:(StoreItemList)-> Unit) : ListAdapter<StoreItemList, HomeAdapter.StoreViewHolder>(PojoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
       val view = StoreItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
       val item = getItem(position)
        holder.bind(item)
    }




    inner class StoreViewHolder(private val binding: StoreItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(itemList : StoreItemList){
        binding.title.text = itemList.name?.take(10)
        Glide.with(binding.wrapper)
            .load(itemList.imageUrl)
            .into(binding.image)
        binding.cardView.setOnClickListener{
            val item = getItem(adapterPosition)
            onClick(item)
        }

    }
    }


    class PojoDiffCallBack : DiffUtil.ItemCallback<StoreItemList>(){
        override fun areItemsTheSame(oldItem: StoreItemList, newItem: StoreItemList): Boolean {
          return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: StoreItemList, newItem: StoreItemList): Boolean {
            return oldItem == newItem
        }

    }



}
