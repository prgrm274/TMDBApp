package com.programmer2704.movapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.programmer2704.movapp.constant.Constants
import com.programmer2704.movapp.databinding.ItemVideoBinding
import com.programmer2704.movapp.model.VideoData
import com.programmer2704.movapp.view.activity.DescriptionActivity

class VideosAdapter(
    data: MutableList<VideoData>,
    val activity: FragmentActivity?
) : RecyclerView.Adapter<VideosAdapter.Holder>() {

    private var videos: MutableList<VideoData> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val bind = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bind)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(videos[position], activity)
    }

    class Holder(private val b: ItemVideoBinding): RecyclerView.ViewHolder(b.root) {
        fun bind(video: VideoData, activity: FragmentActivity?) {
            b.videoName.text = video.name
            b.root.setOnClickListener {
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                activity?.startActivity(
                    YouTubeStandalonePlayer.createVideoIntent(
                        activity,
                        Constants.API_KEY,
                        video.key,
                        0,
                        true,
                        false
                    )
                )
            }
        }
    }

    fun addData(listItems: MutableList<VideoData>) {
        val size = videos.size
        this.videos.addAll(listItems)
        val sizeNew = this.videos.size
        notifyItemRangeChanged(size, sizeNew)
    }
}