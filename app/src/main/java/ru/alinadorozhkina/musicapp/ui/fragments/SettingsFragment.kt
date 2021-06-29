package ru.alinadorozhkina.musicapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.helper.BackButtonListener
import ru.alinadorozhkina.helper.Prefs
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentSettingsBinding
import ru.alinadorozhkina.musicapp.mvp.views.SettingsView
import ru.alinadorozhkina.musicapp.mvp.presenter.SettingsPresenter
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Inject


class SettingsFragment : MvpAppCompatFragment(), SettingsView, BackButtonListener {

    private var ui: FragmentSettingsBinding? = null
    private val presenter by moxyPresenter {
        SettingsPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }
    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui?.chipGroup?.setOnCheckedChangeListener { _, id ->
            Log.v("Settings", id.toString())
            when (id) {
                R.id.pink_theme -> presenter.pinkTheme()
                R.id.blue_theme -> presenter.blueTheme()
                R.id.dark_theme -> presenter.nightTheme()
            }
        }

        ui?.confirmButton?.setOnClickListener {
            requireActivity().recreate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ui = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun backPressed(): Boolean {
        requireActivity().recreate()
        return presenter.backPressed()
    }

}