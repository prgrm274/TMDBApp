package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.data.local.TopratedEntity
import com.programmer2704.movapp.databinding.ItemMovieBinding
import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.view.activity.DescriptionActivity
import com.squareup.picasso.Picasso

class TopratedPagingDataAdapter(val activity: FragmentActivity?) :
    PagingDataAdapter<TopratedEntity, TopratedPagingDataAdapter.Holder>(TOPRATED_COMPARATOR) {

    companion object {
        private val TOPRATED_COMPARATOR = object : DiffUtil.ItemCallback<TopratedEntity>() {
            override fun areItemsTheSame(oldItem: TopratedEntity, newItem: TopratedEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TopratedEntity, newItem: TopratedEntity) =
                oldItem == newItem
        }
    }

    class Holder(private val b: ItemMovieBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(toprated: TopratedEntity, activity: FragmentActivity?) {
            Picasso.get()
                .load(Constants.MOVIE_PHOTO_URL + toprated.poster_path)
                .fit()
                .into(b.movieImage)
            b.root.setOnClickListener {
                val intent = Intent(activity, DescriptionActivity::class.java)
                intent.putExtra("id", toprated.id)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { item ->
            holder.bind(item, activity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val b = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(b)
    }
}