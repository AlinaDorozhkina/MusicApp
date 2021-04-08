package ru.alinadorozhkina.musicapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_artist_tracks.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.api.ApiHolder
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistBinding
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistTracksBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTrackListRepo
import ru.alinadorozhkina.musicapp.mvp.model.view.TrackLisView
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistTracksPresenter
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.image.GlideImageLoader

private const val ARTIST_VALUE = "artist value"
class ArtistTracksFragment : MvpAppCompatFragment(), TrackLisView {

    companion object {
        fun newInstance(url: String): ArtistTracksFragment {
            val args = Bundle()
            args.putString(ARTIST_VALUE, url)
            val f = ArtistTracksFragment()
            f.arguments = args
            return f
        }
    }

    private var ui: FragmentArtistTracksBinding? = null
    private val presenter by  moxyPresenter { ArtistTracksPresenter(
        RetrofitTrackListRepo(ApiHolder.api),
        AndroidSchedulers.mainThread(),
        url
    ) }
    private var adapter: ArtistTracksRVAdapter? = null

    private val url: String?
        get() = arguments?.getString(ARTIST_VALUE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentArtistTracksBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun init() = with(ui) {
        rv_artist_tracks.layoutManager = LinearLayoutManager(requireContext())
        rv_artist_tracks.addItemDecoration((DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)))
        adapter = ArtistTracksRVAdapter(presenter.trackListPresenter, GlideImageLoader() )
        rv_artist_tracks.adapter=adapter
    }

    override fun updateList() {
        ui?.rvArtistTracks?.adapter?.notifyDataSetChanged()
    }
}