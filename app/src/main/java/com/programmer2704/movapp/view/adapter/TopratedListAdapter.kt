package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.databinding.ItemMovieBinding
//import com.programmer2704.movapp.model.Movie
import com.programmer2704.movapp.view.activity.DescriptionActivity
import com.squareup.picasso.Picasso

class TopratedListAdapter(private val activity: FragmentActivity?): ListAdapter<MovieEntity, TopratedListAdapter.Holder>(TopratedComparator()) {
    class TopratedComparator : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem == newItem
    }

    inner class Holder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieEntity) {
            binding.apply {
                Picasso.get()
                    .load(Constants.MOVIE_PHOTO_URL + movie.poster_path)
                    .fit()
                    .into(binding.movieImage)
                binding.root.setOnClickListener {
                    val intent = Intent(activity, DescriptionActivity::class.java)
                    intent.putExtra("id", movie.id)
                    activity?.startActivity(intent)
                    activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val bind = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.bind(current)
        }
    }
}