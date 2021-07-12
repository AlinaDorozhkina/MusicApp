package ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.databinding.FragmentTracksByGenreBinding
import ru.alinadorozhkina.musicapp.mvp.presenter.TracksByGenrePresenter
import ru.alinadorozhkina.musicapp.mvp.views.TracksByGenreView
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.TracksByGenreAdapter

class TracksByGenreFragment : MvpAppCompatFragment(), TracksByGenreView {
    private var ui: FragmentTracksByGenreBinding? = null

    private var adapter: TracksByGenreAdapter? = null

    private val presenter by moxyPresenter {
        TracksByGenrePresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTracksByGenreBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun init() {
        ui?.rvTracksByGenre?.layoutManager = GridLayoutManager(requireActivity(), 2)
        adapter = TracksByGenreAdapter(presenter.tracksByGenreListPresenter.apply {
            App.instance.appComponent.inject(this)
        }).apply {
            App.instance.appComponent.inject(this)
        }
        ui?.rvTracksByGenre?.adapter = adapter
    }

    override fun updateList() {
        ui?.rvTracksByGenre?.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    companion object {
        fun newInstance() = TracksByGenreFragment()
    }
}