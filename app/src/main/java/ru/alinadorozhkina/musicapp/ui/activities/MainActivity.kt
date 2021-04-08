package ru.alinadorozhkina.musicapp.ui.activities

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.ActivityMainBinding
import ru.alinadorozhkina.musicapp.mvp.model.view.MainView
import ru.alinadorozhkina.musicapp.mvp.presenter.MainPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.navigation.AndroidScreens

class MainActivity : MvpAppCompatActivity(), MainView {
    private var ui: ActivityMainBinding? = null
    val navigator = AppNavigator(this, R.id.container)
    private val presenter by moxyPresenter { MainPresenter(App.instance.router, AndroidScreens()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }
}