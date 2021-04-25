package ru.alinadorozhkina.musicapp.ui.adapters

import android.os.CountDownTimer
import android.util.Log
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
//            this.vb.buttonPlay.setOnClickListener {
//                presenter.playClicked(pos, this)
//            }
        })


    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(val vb: ItemViewBinding) : RecyclerView.ViewHolder(vb.root),
        ITopTracksItemView {
        override fun init() = with(vb) {
            buttonPlay.setOnClickListener {
                presenter.playClicked(pos, this@ViewHolder)
            }
            buttonStop.setOnClickListener {
                presenter.stopClicked()
            }
        }

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

        override fun seekbarMax(duration: Int) = with(vb) {
            seekbar.max = duration / 1000
//            val timer = object: CountDownTimer(duration.div(duration).toLong(), 0) {
//                override fun onTick(millisUntilFinished: Long) {
//                    seekbar.progress = millisUntilFinished.toInt()
//                }
//
//                override fun onFinish() {
//                    seekbar.progress = 0
//                }
//            }
//            timer.start()
        }

        override fun seekbarProgress(progress: Int) {
            Log.v("progress", " seekbarprogress $progress")
            vb.seekbar.progress = progress
        }

        override var pos = -1

    }

}