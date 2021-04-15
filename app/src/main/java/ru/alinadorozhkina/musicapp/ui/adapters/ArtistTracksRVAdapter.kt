package ru.alinadorozhkina.musicapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.TrackItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.model.view.list.ITrackListItemView
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITrackListItemPresenter

class ArtistTracksRVAdapter(
    val presenter: ITrackListItemPresenter,
    val imageLoader: IImageLoader<ImageView>
): RecyclerView.Adapter<ArtistTracksRVAdapter.ViewHolder>() {

    inner class ViewHolder(val vb: TrackItemViewBinding): RecyclerView.ViewHolder(vb.root),
        ITrackListItemView{
        override fun setTitle(text: String) = with(vb) {
            tvTitleArtistTrack.text= "Трек: $text"
        }

        override fun setAlbum(title: String) = with(vb){
            tvAlbum.text="Альбом: $title"
        }

        override fun loadCover(url: String) = with(vb){
            imageLoader.load(url, ivAlbum)
        }

        override var pos = -1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(TrackItemViewBinding.inflate(
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