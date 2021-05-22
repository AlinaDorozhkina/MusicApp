package ru.alinadorozhkina.musicapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.get
import com.google.android.material.chip.Chip
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentSettingsBinding
import ru.alinadorozhkina.musicapp.databinding.TopTracksFragmentBinding
import ru.alinadorozhkina.musicapp.mvp.model.view.SettingsView
import ru.alinadorozhkina.musicapp.mvp.presenter.SettingsPresenter
import ru.alinadorozhkina.musicapp.mvp.presenter.TopTrackPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.activities.MainActivity
import ru.alinadorozhkina.musicapp.ui.activities.ThemeHolder

class SettingsFragment : MvpAppCompatFragment(), SettingsView {

    private var ui: FragmentSettingsBinding? = null
    private val presenter by moxyPresenter {
        SettingsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).also { it ->
        ui = it
        ui?.chipGroup?.setOnCheckedChangeListener { chipGroup, id ->
            when (id) {
                R.id.pink_theme -> ThemeHolder.theme =R.style.Theme_MusicApp_Pink
                R.id.blue_theme -> ThemeHolder.theme = R.style.Theme_MusicApp_Blue
                R.id.dark_theme -> ThemeHolder.theme = R.style.Theme_MusicApp_Night
            }
            requireActivity().recreate()
        }
    }.root

    companion object {
        fun newInstance() = SettingsFragment()
    }
}