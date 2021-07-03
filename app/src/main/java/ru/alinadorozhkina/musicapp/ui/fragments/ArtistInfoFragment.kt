package ru.alinadorozhkina.musicapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_main_player.*
import kotlinx.android.synthetic.main.layout_main_player.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentArtistInfoBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.ArtistTrack
import ru.alinadorozhkina.musicapp.mvp.model.image.IImageLoader
import ru.alinadorozhkina.musicapp.mvp.presenter.ArtistPresenter
import ru.alinadorozhkina.musicapp.mvp.views.ArtistView
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.adapters.ArtistTracksRVAdapter
import javax.inject.Inject


private const val ARTIST_VALUE = "artist value"

class ArtistInfoFragment : MvpAppCompatFragment(), ArtistView {

    private var adapter: ArtistTracksRVAdapter? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    private var ui: FragmentArtistInfoBinding? = null

    private val presenter by moxyPresenter {
        val artist = arguments?.getString(ARTIST_VALUE) ?: RuntimeException(" ошибка, ")
        ArtistPresenter(artist.toString()).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
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
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        ui?.inputLayout?.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                ui?.inputLayout?.setEndIconOnClickListener {
                    presenter.loadTracks(s.toString())
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    override fun setArtistName(name: String) {
        ui?.artistName?.text = name
    }

    override fun setArtistPicture(bitmap: Bitmap) {
        ui?.toolbarArtistImage?.setImageBitmap(bitmap)
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

    override fun playArtistTrack(track: ArtistTrack) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                button_stop.isEnabled = false
                bottomSheet.tv_song_title.text = track.title
                bottomSheet.tv_album_title.text = track.album.title
                bottomSheet.tv_artist_name.text = track.artist.name
                imageLoader.load(track.album.cover, bottomSheet.bottom_sheet_poster)
                bottomSheet.button_play.setOnClickListener {
                    presenter.buttonPlayClicked(track.preview)
                    button_play.isEnabled = false
                    button_stop.isEnabled = true
                }
                bottomSheet.button_stop.setOnClickListener {
                    presenter.stopClicked()
                    button_play.isEnabled = true
                    button_stop.isEnabled = false
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottomSheet.tv_song_title.text = track.title
                bottomSheet.tv_album_title.text = track.album.title
                bottomSheet.tv_artist_name.text = track.artist.name
            }
        })
    }

    override fun seekbarMax(duration: Int) {
        seekbar.max = duration
    }

    override fun seekbarProgress(progress: Int) {
        seekbar.progress = progress
    }

    override fun updatePicture(url: String) {
        ui?.toolbarArtistImage?.let { imageLoader.load(url, it) }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    companion object {
        fun newInstance(artist: String): ArtistInfoFragment {
            val args = Bundle()
            args.putString(ARTIST_VALUE, artist)
            val f = ArtistInfoFragment()
            f.arguments = args
            return f
        }
    }
}