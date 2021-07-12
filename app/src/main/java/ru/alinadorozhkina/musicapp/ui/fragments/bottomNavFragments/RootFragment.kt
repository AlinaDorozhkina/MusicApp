package ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.alinadorozhkina.musicapp.R
import ru.alinadorozhkina.musicapp.databinding.FragmentRootBinding
import ru.alinadorozhkina.musicapp.di.LocalCiceroneHolder
import ru.alinadorozhkina.musicapp.mvp.navigation.IScreens
import ru.alinadorozhkina.musicapp.ui.App
import javax.inject.Inject

class RootFragment :Fragment() {

    private var ui: FragmentRootBinding? = null
    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.bottom_container, childFragmentManager)
    }
    @Inject lateinit var ciceroneHolder: LocalCiceroneHolder

    @Inject lateinit var screens: IScreens

    private val cicerone: Cicerone<Router>
        get() = ciceroneHolder.getCicerone()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRootBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.bottom_container) == null) {
          cicerone.router.replaceScreen(screens.topList())
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui?.bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_chart -> {
                    cicerone.router.replaceScreen(screens.topList())
                    true
                }
                R.id.favourite ->{
                    true
                }
                R.id.genre -> {
                    cicerone.router.replaceScreen(screens.genre())
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ui = null
    }

    companion object {
        fun newInstance() = RootFragment()
    }
}