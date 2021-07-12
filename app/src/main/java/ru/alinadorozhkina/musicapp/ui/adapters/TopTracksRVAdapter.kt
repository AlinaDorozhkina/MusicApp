package ru.alinadorozhkina.musicapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.helper.getColorRes
import ru.alinadorozhkina.musicapp.databinding.ItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITopTracksListPresenter
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITopTracksItemView
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Inject

class TopTracksRVAdapter(val presenter: ITopTracksListPresenter) :
    RecyclerView.Adapter<TopTracksRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>
    @Inject
    lateinit var context: App


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
        })


    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(val vb: ItemViewBinding) : RecyclerView.ViewHolder(vb.root),
        ITopTracksItemView {

        private var flag = false

        override fun init() = with(vb) {
            buttonPlay.setOnClickListener {
                presenter.playClicked(pos, this@ViewHolder)
            }
            buttonStop.setOnClickListener {
                presenter.stopClicked()
            }

            buttonFavourites.setOnClickListener {
                presenter.favouritesClicked(pos)
                // поменять изображение
            }
        }

        override fun setTitle(title: String) = with(vb) {
            tvTitle.text = title
            tvTitle.isSelected = true
        }

        override fun setPosition(position: Int) = with(vb) {
            tvPosition.text = position.toString()
            cardContainer.background = getColorRes(context, position)
            //cardContainer.setCardBackgroundColor(getColorRes(context, position))
        }

        override fun setArtist(artist: String) = with(vb) {
            tvTitle.isSelected = true
            tvArtist.text = artist
            tvArtist.isSelected = true
        }

        override fun loadPoster(url: String) = with(vb) {
            imageLoader.load(url, ivPoster)
        }

        override fun seekbarMax(duration: Int) = with(vb) {
            seekbar.max = duration
        }

        override fun seekbarProgress(progress: Int) {
            Log.v("progress", " seekbarprogress $progress")
            vb.seekbar.progress = progress
        }

        override var pos = -1

    }

}