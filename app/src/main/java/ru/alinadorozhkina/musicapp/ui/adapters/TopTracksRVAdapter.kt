package ru.alinadorozhkina.musicapp.ui.adapters

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.ItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITopTracksItemView
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITopTracksListPresenter
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Inject

class TopTracksRVAdapter(val presenter: ITopTracksListPresenter) :
    RecyclerView.Adapter<TopTracksRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
            this.vb.buttonPlay.setOnClickListener {
                    presenter.playClicked(pos)
            }
        })


    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(val vb: ItemViewBinding) : RecyclerView.ViewHolder(vb.root),
        ITopTracksItemView {

        override fun setTitle(title: String) = with(vb) {
            tvTitle.text = title
        }

        override fun setPosition(position: Int) = with(vb) {
            tvPosition.text = position.toString()
        }

        override fun setArtist(artist: String) = with(vb) {
            tvArtist.text = artist
        }

        override fun loadPoster(url: String) = with(vb) {
            imageLoader.load(url, ivPoster)
        }

//        override fun play(uri: Uri) {
//            player = MediaPlayer.create(App.instance, uri)
//            player.start()
//        }


        override var pos = -1

    }

}