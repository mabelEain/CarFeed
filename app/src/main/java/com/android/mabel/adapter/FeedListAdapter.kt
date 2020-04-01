package com.android.mabel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mabel.R
import com.android.mabel.model.FeedModel
import com.android.mabel.util.loadImage
import kotlinx.android.synthetic.main.caritem_layout.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


class FeedListAdapter(var feeds: ArrayList<FeedModel>) :
    RecyclerView.Adapter<FeedListAdapter.FeedViewHolder>() {
    fun updateFeed(newFeed: List<FeedModel>) {

        feeds.clear()
        feeds.addAll(newFeed)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = FeedViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.caritem_layout, parent, false)
    )
    override fun getItemCount() = feeds.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feeds[position])
    }
    class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.imageView
        private val feedName = view.txt_title
        private val feedEmail = view.txt_ingress
        private val feedDate = view.txt_date
        fun bind(feed: FeedModel) {
            imageView.loadImage(feed.image)
            feedName.text = feed.title
            feedEmail.text = feed.ingress
            val parser = SimpleDateFormat("dd.MM.yyyy HH:MM")
            val formatter = SimpleDateFormat("dd MMMM yyyy, HH:MM")
            val output = formatter.format(parser.parse(feed.dateTime))
            feedDate.text = output

        }
    }
}