package ru.alinadorozhkina.musicapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistInfoBinding
import ru.alinadorozhkina.musicapp.databinding.FragmentSearchBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.repo.ITrackListRepo
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistPresenter
import ru.alinadorozhkina.musicapp.mvp.presenter.SearchPresenter
import ru.alinadorozhkina.musicapp.mvp.views.list.SearchView
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import javax.inject.Inject

private const val SEARCH_VALUE = "search value"

class SearchFragment : MvpAppCompatFragment(), SearchView {

    private var ui: FragmentSearchBinding? = null
    private var adapter: ArtistTracksRVAdapter? = null

    private val presenter by moxyPresenter {
        val artist = arguments?.getString(SEARCH_VALUE)
        SearchPresenter(artist).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui?.inputLayout?.setEndIconOnClickListener {
            //сделать get запрос в ретрофите
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }


    companion object {
        fun newInstance(name: String): SearchFragment {
            val args = Bundle()
            args.putString(SEARCH_VALUE, name)
            val f = SearchFragment()
            f.arguments = args
            return f
        }

    }

    override fun setArtistName(name: String?) {
        ui?.artistName?.text = name
    }

    override fun setArtistPicture(bitmap: Bitmap) {

    }

    override fun init() {
        ui?.rvArtistTracks?.layoutManager = LinearLayoutManager(requireContext())
        ui?.rvArtistTracks?.addItemDecoration(
            (DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            ))
        )
        adapter = ArtistTracksRVAdapter(presenter.trackListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        ui?.rvArtistTracks?.adapter = adapter
    }

    override fun updateList() {
        ui?.rvArtistTracks?.adapter?.notifyDataSetChanged()
    }
}