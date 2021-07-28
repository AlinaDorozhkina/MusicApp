package ru.alinadorozhkina.musicapp.ui.fragments.bottomNavFragments

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alinadorozhkina.musicapp.databinding.TopTracksFragmentBinding
import ru.alinadorozhkina.musicapp.mvp.views.TopTrackView
import ru.alinadorozhkina.musicapp.mvp.presenter.TopTrackPresenter
import ru.alinadorozhkina.musicapp.ui.App
import ru.alinadorozhkina.musicapp.ui.activities.MainActivity
import ru.alinadorozhkina.musicapp.ui.adapters.TopTracksRVAdapter

class TopTracksFragment : MvpAppCompatFragment(), TopTrackView {

    private val presenter by moxyPresenter {
        TopTrackPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var ui: TopTracksFragmentBinding? = null
    private var adapter: TopTracksRVAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = TopTracksFragmentBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
//        ui?.bottomNav?.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.main_chart -> {
//                    //Item tapped
//                    true
//                }
//                R.id.favourite -> {
//                    //Item tapped
//                    true
//                }
//                R.id.settings -> {
//                    //Item tapped
//                    true
//                }
//                R.id.search -> {
//                    val searchView: SearchView = R.id.search.actionView as SearchView
//                    searchView.setFocusable(false)
//                    searchView.setQueryHint("Имя артиста")
//                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                        override fun onQueryTextSubmit(s: String): Boolean {
//                            Log.v("TAG", "onQueryTextSubmit$s")
//                            searchView.clearFocus()
//                            searchView.setQuery("", false)
//                            searchItem.collapseActionView()
//                            presenter.search(s)
//                            return true
//                        }
//
//                        override fun onQueryTextChange(s: String?): Boolean {
//                            Log.v("TAG", "onQueryTextChange$s")
//                            return false
//                        }
//                    })
//                    true
//                }
//                else -> false
//            }
//
//        }
//        ui?.bottomNav?.setOnItemReselectedListener { item ->




//        context.setSupportActionBar(view.findViewById(R.id.bottomAppBar))
//        setHasOptionsMenu(true)


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.bottom_app_bar, menu)
//        val searchItem = menu.findItem((R.id.search))
//
//        val searchView: SearchView = searchItem.actionView as SearchView
//        searchView.setFocusable(false)
//        searchView.setQueryHint("Имя артиста")
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(s: String): Boolean {
//                Log.v("TAG", "onQueryTextSubmit$s")
//                searchView.clearFocus()
//                searchView.setQuery("", false)
//                searchItem.collapseActionView()
//                presenter.search(s)
//                return true
//            }
//
//            override fun onQueryTextChange(s: String?): Boolean {
//                Log.v("TAG", "onQueryTextChange$s")
//                return false
//            }
//        })
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.favourite -> presenter.favouritesClicked()
//            R.id.settings -> presenter.settingsClicked()
//        }
//        return super.onOptionsItemSelected(item)
//    }

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

    companion object {
        fun newInstance() = TopTracksFragment()
    }

}