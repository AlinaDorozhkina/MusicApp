package ru.alinadorozhkina.musicapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.musicapp.databinding.TrackItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITrackListItemView
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter
import javax.inject.Inject

class ArtistTracksRVAdapter(val presenter: ITrackListItemPresenter) :
    RecyclerView.Adapter<ArtistTracksRVAdapter.ViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    inner class ViewHolder(val vb: TrackItemViewBinding) : RecyclerView.ViewHolder(vb.root),
        ITrackListItemView {
        override fun setTitle(text: String) = with(vb) {
            tvTitleArtistTrack.text = "Трек: $text"
            tvTitleArtistTrack.isSelected = true
        }

        override fun setAlbum(title: String) = with(vb) {
            tvAlbum.text = "Альбом: $title"
            tvAlbum.isSelected = true
        }

        override fun loadCover(url: String) = with(vb) {
            imageLoader.load(url, ivAlbum)
        }

        override var pos = -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            TrackItemViewBinding.inflate(
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