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
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomImageCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomTrackListCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTrackListRepo
import ru.alinadorozhkina.musicapp.mvp.model.view.TrackLisView
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistTracksPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.image.GlideImageLoader
import ru.alinadorozhkina.musicapp.ui.network.AndroidNetworkStatus

private const val ARTIST_VALUE = "artist value"
class ArtistTracksFragment : MvpAppCompatFragment(), TrackLisView {

    companion object {
        fun newInstance(artist: Artist): ArtistTracksFragment {
            val args = Bundle()
            args.putParcelable(ARTIST_VALUE, artist)
            val f = ArtistTracksFragment()
            f.arguments = args
            return f
        }
    }

    private var ui: FragmentArtistTracksBinding? = null
    private val presenter by  moxyPresenter {
        val artist = arguments?.getParcelable<Artist>(ARTIST_VALUE) as Artist
        ArtistTracksPresenter(
        RetrofitTrackListRepo(ApiHolder.api,
            AndroidNetworkStatus(App.instance),
            RoomTrackListCache(
            DataBase.getInstance())),
        AndroidSchedulers.mainThread(),
        artist
    ) }
    private var adapter: ArtistTracksRVAdapter? = null

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
        adapter = ArtistTracksRVAdapter(presenter.trackListPresenter, GlideImageLoader(RoomImageCache(
            DataBase.getInstance(), App.instance.cacheDir), AndroidNetworkStatus(requireContext())) )
        rv_artist_tracks.adapter=adapter
    }

    override fun updateList() {
        ui?.rvArtistTracks?.adapter?.notifyDataSetChanged()
    }
}