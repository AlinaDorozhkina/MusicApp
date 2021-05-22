package ru.alinadorozhkina.musicapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistInfoBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Artist
import ru.alinadorozhkina.musicapp.mvp.model.view.ArtistView
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter

private const val ARTIST_VALUE = "artist value"

class ArtistInfoFragment : MvpAppCompatFragment(), ArtistView {

    private var adapter: ArtistTracksRVAdapter? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance(artist: Artist): ArtistInfoFragment {
            val args = Bundle()
            args.putParcelable(ARTIST_VALUE, artist)
            val f = ArtistInfoFragment()
            f.arguments = args
            return f
        }
    }

    private var ui: FragmentArtistInfoBinding? = null

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
    ) = FragmentArtistInfoBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    override fun setArtistName(name: String)  {
        ui?.artistName?.text = name
    }

    override fun setArtistPicture(bitmap: Bitmap) {
        ui?.artistPicture?.setImageBitmap(bitmap)
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

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}