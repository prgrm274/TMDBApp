package com.programmer2704.movapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.databinding.ItemReviewBinding
import com.programmer2704.movapp.model.Revieww

class ReviewsAdapter(
    data: MutableList<Revieww.Result>
) : RecyclerView.Adapter<ReviewsAdapter.Holder>() {

    private var videos: MutableList<Revieww.Result> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val bind = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(videos[position])
    }

    class Holder(private val b: ItemReviewBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(result: Revieww.Result) {
            b.author.text = result.author
            b.content.text = result.content
            b.updatedAt.text = result.updatedAt
        }
    }

    fun addData(listItems: MutableList<Revieww.Result>) {
        val size = videos.size
        this.videos.addAll(listItems)
        val sizeNew = this.videos.size
        notifyItemRangeChanged(size, sizeNew)
    }
}