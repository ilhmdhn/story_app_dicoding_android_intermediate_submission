package com.ilhmdhn.storyapp.view.story.paging

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilhmdhn.storyapp.databinding.ListStoryBinding
import com.ilhmdhn.storyapp.data.remote.response.ListStoryItem
import com.ilhmdhn.storyapp.view.story.DetailStoryActivity

class StoryAdapter: PagingDataAdapter<ListStoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        Log.d("data masuk", data.toString())
        if (data!=null){
            holder.bind(data)
        }
    }

    class ListViewHolder(private val binding: ListStoryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem){
            with(binding){
                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .into(binding.ivItemPhoto)
                tvItemName.text = data.name

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.DATA_DETAIL, data)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}