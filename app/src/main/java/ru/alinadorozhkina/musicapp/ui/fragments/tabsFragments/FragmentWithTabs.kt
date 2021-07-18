package ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentWithTabsBinding
import ru.alinadorozhkina.musicapp.mvp.model.entity.Track
import ru.alinadorozhkina.musicapp.mvp.presenter.FragmentWithTabsPresenter
import ru.alinadorozhkina.musicapp.mvp.views.FragmentTabsView
import ru.alinadorozhkina.musicapp.ui.App

private const val TRACK_VALUE = "track_value"

class FragmentWithTabs : MvpAppCompatFragment(), FragmentTabsView {

    private val presenter by moxyPresenter { FragmentWithTabsPresenter(track).apply {
        App.instance.appComponent.inject(this)
    } }
    private val track: Track?
        get() = arguments?.getParcelable(TRACK_VALUE)
    private var ui: FragmentWithTabsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWithTabsBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun init() {
        track?.let {
            val fragments = arrayOf(
                TrackInfoFragment.newInstance(it),
                ArtistInfoFragment.newInstance(it.artist.name),
                AlbumInfoFragment.newInstance(it.album.title)
            )

            ui?.viewPager?.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int = fragments.size

                override fun createFragment(position: Int): Fragment = fragments[position]
            }

            ui?.let { ui ->
                ui.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_baseline_music_note_24)
                ui.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_singer)
                ui.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_baseline_album_24)
                TabLayoutMediator(ui.tabLayout, ui.viewPager) { tab, position ->
                    when (position) {
                        0 -> tab.text = "Трек"
                        1 -> tab.text = "Артист"
                        2 -> tab.text = "Альбом"
                        else -> tab.text = "Трек"
                    }
                }.attach()
            }
        }
    }

    override fun setImageOnToolbar(decodeByteArray: Bitmap) {
        ui?.toolbarArtistImage?.setImageBitmap(decodeByteArray)
    }

    companion object {
        fun newInstance(track: Track): FragmentWithTabs {
            val bundle = Bundle()
            bundle.putParcelable(TRACK_VALUE, track)
            val f = FragmentWithTabs()
            f.arguments = bundle
            return f
        }
    }
}