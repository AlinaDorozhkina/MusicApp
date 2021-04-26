package ru.alinadorozhkina.musicapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_artist.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistPresenter
import ru.alinadorozhkina.musicapp.ui.App

private const val ARTIST_VALUE = "artist value"

class ArtistFragment : MvpAppCompatFragment(), ArtistView {

    companion object {
        fun newInstance(artist: Artist): ArtistFragment {
            val args = Bundle()
            args.putParcelable(ARTIST_VALUE, artist)
            val f = ArtistFragment()
            f.arguments = args
            return f
        }
    }

    private var ui: FragmentArtistBinding? = null

    private val presenter by moxyPresenter {
        val artist = arguments?.getParcelable<Artist>(ARTIST_VALUE) as Artist
        ArtistPresenter(artist).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentArtistBinding.inflate(inflater, container, false).also {
        ui = it
        ui?.btnShowTrackList?.setOnClickListener {
            presenter.buttonTrackListClicked()
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    override fun setArtistName(name: String) = with(ui) {
        tv_artist_name?.text = name
    }

    override fun setArtistPicture(bitmap: Bitmap) {
        ui?.artistPicture?.setImageBitmap(bitmap)
    }

}
