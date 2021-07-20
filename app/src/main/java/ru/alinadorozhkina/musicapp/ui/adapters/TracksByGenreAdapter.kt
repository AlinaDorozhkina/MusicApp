package ru.alinadorozhkina.musicapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.alinadorozhkina.musicapp.databinding.GenreItemViewBinding
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.presenter.list.ITracksByGenreListPresenter
import ru.alinadorozhkina.musicapp.mvp.views.list.list.ITracksByGenreItemView
import javax.inject.Inject

class TracksByGenreAdapter(val presenter: ITracksByGenreListPresenter):
    RecyclerView.Adapter<TracksByGenreAdapter.GenreViewHolder>() {

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksByGenreAdapter.GenreViewHolder =
        GenreViewHolder(
            GenreItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        presenter.bindView(holder.apply {
            pos = position
        })

    override fun getItemCount(): Int = presenter.getCount()

    inner class GenreViewHolder(val vb: GenreItemViewBinding): RecyclerView.ViewHolder(vb.root),
        ITracksByGenreItemView{
        override fun init() {
            TODO("Not yet implemented")
        }

        override fun loadPicture(url: String) {
            imageLoader.load(url, vb.genreCover)
        }

        override fun setTGenre(genre: String) = with(vb){
        genreTitle.text = genre
        }

        override var pos = -1

    }
}