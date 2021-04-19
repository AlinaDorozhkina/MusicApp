package ru.alinadorozhkina.musicapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.musicapp.databinding.ItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITopTracksItemView
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITopTracksListPresenter
import javax.inject.Inject

class TopTracksRVAdapter(val presenter: ITopTracksListPresenter) :
    RecyclerView.Adapter<TopTracksRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

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

        override var pos = -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
        })

    override fun getItemCount(): Int = presenter.getCount()

}