package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.movapp.R
import com.programmer2704.movapp.data.local.GenreEntity
import com.programmer2704.movapp.view.activity.DescriptionActivity

class GenreAdapter(
    data: MutableList<GenreEntity>,
    private var activity: FragmentActivity?)
    : RecyclerView.Adapter<GenreAdapter.Holder>() {

    private var genres: MutableList<GenreEntity> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.genre_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(genres.get(position), activity)
    }

    class Holder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v

        fun bind(genre: GenreEntity, activity: FragmentActivity?) {
            view.setOnClickListener {
                val intent = Intent(activity, DescriptionActivity::class.java)
                intent.putExtra("id", genre.id)
                intent.putExtra("name", genre.name)
                activity?.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    fun addData(listItems: MutableList<GenreEntity>) {
        val size = genres.size
        this.genres.addAll(listItems)
        val sizeNew = this.genres.size
        notifyItemRangeChanged(size, sizeNew)
    }
}