package ru.alinadorozhkina.musicapp.ui.activities

import android.os.Bundle
import android.util.Log
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.helper.BackButtonListener
import ru.alinadorozhkina.helper.Prefs
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.ActivityMainBinding
import ru.alinadorozhkina.musicapp.mvp.views.MainView
import ru.alinadorozhkina.musicapp.mvp.presenter.MainPresenter
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {
    private var ui: ActivityMainBinding? = null

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    val navigator = AppNavigator(this, R.id.container)

    @Inject
    lateinit var prefs: Prefs

    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        val theme = prefs.themeColor
        Log.v("THEME", theme)
        when (theme) {
            "pink" -> setTheme(R.style.Theme_MusicApp_Pink)
            "blue" -> setTheme(R.style.Theme_MusicApp_Blue)
            "night" -> setTheme(R.style.Theme_MusicApp_Night)
        }

        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        ui = null
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }

}
