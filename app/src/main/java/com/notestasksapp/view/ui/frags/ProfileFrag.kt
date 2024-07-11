package com.notestasksapp.view.ui.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler

class ProfileFrag : Fragment() {

    private lateinit var mView: View
    private lateinit var dbHandler: DBHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.frag_profile, container, false)

        initialization()

        return mView
    }

    private fun initialization() {
        dbHandler = DBHandler(requireContext())
    }

}