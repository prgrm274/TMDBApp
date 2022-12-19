package com.programmer2704.movapp.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import android.view.MenuItem
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer2704.movapp.R
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.data.local.MovieEntity
import com.programmer2704.movapp.databinding.ActivityDescriptionBinding
import com.programmer2704.movapp.tools.Usage
import com.programmer2704.movapp.view.adapter.ReviewsAdapter
import com.programmer2704.movapp.view.adapter.VideosAdapter
import com.programmer2704.movapp.view.viewmodel.DescriptionViewmodel
import com.programmer2704.movapp.view.viewmodel.FavViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private lateinit var b: ActivityDescriptionBinding
    private val vm: DescriptionViewmodel by viewModels()

    private val favoritesViewModel: FavViewmodel by viewModels()
    private var posterPath: String? = null
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getIntExtra("id", 0)
        val isFavorite = intent.getBooleanExtra("isFavorite", false)

        b.btnFavorite.text = if (isFavorite) {
            b.btnFavorite.setBackgroundColor(resources.getColor(R.color.colorAccent))
            b.btnFavorite.setTextColor(Color.GRAY)
            getString(R.string.remove_favorite)
        } else {
            b.btnFavorite.setBackgroundColor(resources.getColor(R.color.colorGreen))
            getString(R.string.add_favorite)
        }

        movieId.let {
            if (Usage.isOnline(this)) {
                vm.fetchDetails(it)

            } else {
                Toast.makeText(
                    this,
                    "No internet Present, Please enable internet connection",
                    Toast.LENGTH_SHORT
                ).show()
                b.progressCircular.visibility = View.GONE
            }

            println("https://api.themoviedb.org/3/movie/$it/videos?api_key=${Constants.API_KEY}")
            println("https://api.themoviedb.org/3/movie/$it?api_key=${Constants.API_KEY}&append_to_response=videos")

            vm.movieDetails.observe(this, Observer { movie ->
                b.progressCircular.visibility = View.GONE
                b.btnFavorite.visibility = View.VISIBLE

                Picasso.get().load(Constants.MOVIE_PHOTO_URL + movie.poster_path)
                    .placeholder(R.drawable.tmdb_green)
                    .into(b.posterImage)
                // Using poster as screen background
                Picasso.get().load(Constants.MOVIE_PHOTO_URL + movie.poster_path)
                    .placeholder(R.drawable.tmdb_blue)
                    .into(b.bgPosterImage)
                Picasso.get().load(Constants.MOVIE_PHOTO_URL + movie.backdrop_path)
                    .placeholder(R.drawable.tmdb_blue)
                    .into(b.backdropImage)
                posterPath = movie.poster_path
                b.title.text = movie.original_title
                b.overview.text = movie.overview
                if (movie.revenue == 0 || movie.revenue == null)
                    b.revenue.visibility = View.GONE
                else
                    b.revenue.text = "Revenue: $ ${movie.revenue}"

                if (movie.runtime == 0 || movie.runtime == null)
                    b.runtime.visibility = View.GONE
                else
                    b.runtime.text = "${movie.runtime} mins"

                if (movie.budget == 0 || movie.budget == null)
                    b.budget.visibility = View.GONE
                else
                    b.budget.text = "Budget: $ ${movie.budget}"

                b.originalLanguage.text = "Language: ${movie.original_language}"

                b.taglineText.text = movie.tagline
            })

            vm.fetchVideos(it)
            vm.videos.observe(this) { videos ->
                videosAdapter = VideosAdapter(videos, this)
                b.rvVideos.layoutManager = LinearLayoutManager(this)
                b.rvVideos.adapter = videosAdapter
                videosAdapter.addData(videos)
            }

            vm.fetchReviews(it)
            vm.reviews.observe(this) { result->
                reviewsAdapter = ReviewsAdapter(result)
                b.rvReviews.layoutManager = LinearLayoutManager(this)
                b.rvReviews.adapter = reviewsAdapter
                reviewsAdapter.addData(result)
            }
        }

        b.btnFavorite.setOnClickListener {
            if (b.btnFavorite.text.equals(getString(R.string.add_favorite))) {
                favoritesViewModel.insert(MovieEntity(id = movieId, poster_path = posterPath))
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
                b.btnFavorite.visibility = View.GONE
            } else if (b.btnFavorite.text.equals(getString(R.string.remove_favorite))) {
                favoritesViewModel.delete(MovieEntity(id = movieId, poster_path = posterPath))
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
                b.btnFavorite.visibility = View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}