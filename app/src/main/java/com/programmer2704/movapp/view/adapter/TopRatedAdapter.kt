package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.data.local.TopRatedEntry
import com.programmer2704.movapp.databinding.ItemMovieBinding
import com.programmer2704.movapp.view.activity.DescriptionActivity
import com.squareup.picasso.Picasso

class TopRatedAdapter(val activity: FragmentActivity) : PagedListAdapter<TopRatedEntry,
        RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    class TopRatedViewHolder(
        private val v: ItemMovieBinding,
        val activity: FragmentActivity,
    ) : RecyclerView.ViewHolder(v.root) {
        fun bind(movie: TopRatedEntry) {
            Picasso.get()
                .load(Constants.MOVIE_PHOTO_URL + movie.posterPath)
                .fit()
                .into(v.movieImage)
            v.root.setOnClickListener {
                val intent = Intent(activity, DescriptionActivity::class.java)
                intent.putExtra("id", movie.movieId)
                activity.startActivity(intent)
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bind = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopRatedViewHolder(bind, activity)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie: TopRatedEntry? = getItem(position)

        if (movie != null) {
            val movieViewHolder = holder as TopRatedViewHolder
            movieViewHolder.bind(movie)
        } else {
            notifyItemRemoved(position)
        }

    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<TopRatedEntry>() {
            override fun areItemsTheSame(oldItem: TopRatedEntry, newItem: TopRatedEntry): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(
                oldItem: TopRatedEntry,
                newItem: TopRatedEntry
            ): Boolean =
                oldItem == newItem
        }
    }
}