package com.example.android.kevkane87.matchedbetapp.savedbets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.databinding.ItemSavedBetBinding

class SavedBetsAdapter (val onClickListener: OnClickListener) : ListAdapter<MatchedBetDataItem,
            SavedBetsAdapter.ViewHolder>(BetDiffCallback) {


        //viewholder for bet items
        class ViewHolder (private val binding: ItemSavedBetBinding)
            : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: MatchedBetDataItem) {
                binding.bet = item
                binding.executePendingBindings()
            }
        }


        //callback for calculating the diff between two non-null items in a list
        companion object BetDiffCallback : DiffUtil.ItemCallback<MatchedBetDataItem>() {
            override fun areItemsTheSame(oldItem: MatchedBetDataItem, newItem: MatchedBetDataItem): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: MatchedBetDataItem, newItem: MatchedBetDataItem): Boolean {
                return oldItem.id == newItem.id
            }
        }

        //called when RecyclerView needs a new ViewHolder
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemSavedBetBinding.inflate(
                    LayoutInflater.from(parent.context), parent,
                    false))
        }

        //called by RecyclerView to display the data at the specified position
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bet = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(bet)
            }
            holder.bind(bet)
        }


        //click listener class
        class OnClickListener(val clickListener: (asteroid: MatchedBetDataItem) -> Unit) {
            fun onClick(bet: MatchedBetDataItem) = clickListener(bet)
        }
    }
