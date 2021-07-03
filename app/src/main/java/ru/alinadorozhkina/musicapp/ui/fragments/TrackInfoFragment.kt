package ru.alinadorozhkina.musicapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.databinding.FragmentTrackInfoBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.views.TrackInfoView
import ru.alinadorozhkina.musicapp.mvp.presenter.TrackInfoPresenter
import ru.alinadorozhkina.musicapp.ui.App

private const val TRACK_VALUE = "artist value"

class TrackInfoFragment : MvpAppCompatFragment(), TrackInfoView {

    private var ui: FragmentTrackInfoBinding? = null
    private val presenter by moxyPresenter {
        val track = arguments?.getParcelable<Track>(TRACK_VALUE) as Track
        TrackInfoPresenter(track).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTrackInfoBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    companion object {
        fun newInstance(track: Track): TrackInfoFragment {
            val args = Bundle()
            args.putParcelable(TRACK_VALUE, track)
            val f = TrackInfoFragment()
            f.arguments = args
            return f
        }
    }
}