package ru.alinadorozhkina.musicapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.databinding.FragmentTopTracksBinding
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomImageCache
import ru.alinadorozhkina.musicapp.mvp.model.cache.room.RoomTopTrackCache
import ru.alinadorozhkina.musicapp.mvp.model.entity.room.db.DataBase
import ru.alinadorozhkina.musicapp.mvp.model.repo.RetrofitTopTracksRepo
import ru.alinadorozhkina.musicapp.mvp.model.view.TopTrackView
import ru.alinadorozhkina.musicapp.mvp.presenter.TopTrackPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.TopTracksRVAdapter
import ru.alinadorozhkina.musicapp.ui.image.GlideImageLoader
import ru.alinadorozhkina.musicapp.ui.network.AndroidNetworkStatus


class TopTracksFragment : MvpAppCompatFragment(), TopTrackView {

    private val presenter by moxyPresenter {
        TopTrackPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var ui: FragmentTopTracksBinding? = null

    private var adapter: TopTracksRVAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTopTracksBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    companion object {
        fun newInstance() = TopTracksFragment()
    }

    override fun setTopTrackAmount(total: Int) {
        ui?.tvTopAmount?.text = " TОП $total лучших треков"
    }

    override fun init() {
        ui?.rvTracks?.layoutManager = LinearLayoutManager(requireContext())
        ui?.rvTracks?.addItemDecoration(
            (DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            ))
        )
        adapter = TopTracksRVAdapter(presenter.topTrackListPresenter.apply {
            App.instance.appComponent.inject(this)
        }).apply {
            App.instance.appComponent.inject(this)
        }
        ui?.rvTracks?.adapter = adapter
    }

    override fun updateList() {
        ui?.rvTracks?.adapter?.notifyDataSetChanged()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

}