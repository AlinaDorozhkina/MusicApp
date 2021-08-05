package ru.alinadorozhkina.musicapp.ui.fragments.tabsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.alinadorozhkina.musicapp.R

private const val ALBUM_VALUE = "album value"
class AlbumInfoFragment : Fragment() {

    lateinit var bottomsheet: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomsheet = view.findViewById(R.id.bottom_sheet_container)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    companion object {
        fun newInstance (albumTitle: String): AlbumInfoFragment {
            val args = Bundle()
            args.putString(ALBUM_VALUE, albumTitle)
            val f = AlbumInfoFragment()
            f.arguments = args
            return f
        }

    }
}