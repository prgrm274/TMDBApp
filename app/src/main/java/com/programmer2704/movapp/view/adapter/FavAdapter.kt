package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.R
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.view.activity.DescriptionActivity
import com.squareup.picasso.Picasso

class FavAdapter internal constructor(
    activity: FragmentActivity?
) : RecyclerView.Adapter<FavAdapter.MovieHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(activity)
    private var movies = emptyList<MovieEntity>()
    private var activity: FragmentActivity? = activity

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.movieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val current = movies[position]
        Picasso.get().load(Constants.MOVIE_PHOTO_URL + current.poster_path).centerCrop().fit().into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, DescriptionActivity::class.java)
            intent.putExtra("id", current.id)
            intent.putExtra("isFavorite", true)
            activity?.startActivity(intent)
            activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    internal fun setMovieEntityList(entities: List<MovieEntity>) {
        this.movies = entities
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size
}