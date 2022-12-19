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

class UpcomingAdapter(
    data: MutableList<MovieEntity>,
    activity: FragmentActivity?
) : RecyclerView.Adapter<UpcomingAdapter.Holder>() {

    private var movies: MutableList<MovieEntity> = data
    private var activity: FragmentActivity? = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(movies.get(position), activity)
    }

    class Holder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var image: ImageView = v.findViewById(R.id.movieImage)

        fun bind(movie: MovieEntity, activity: FragmentActivity?) {
            Picasso.get()
                .load(Constants.MOVIE_PHOTO_URL + movie.poster_path)
                .fit()
                .into(image)
            view.setOnClickListener {
                val intent = Intent(activity, DescriptionActivity::class.java)
                intent.putExtra("id", movie.id)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    fun addData(listItems: MutableList<MovieEntity>) {
        val size = movies.size
        this.movies.addAll(listItems)
        val sizeNew = this.movies.size
        notifyItemRangeChanged(size, sizeNew)
    }
}