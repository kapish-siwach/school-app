package com.first.schoolapp.freagments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.schoolapp.R
import com.first.schoolapp.databinding.FragmentAssignmentBinding

class AssignmentFragment : Fragment() {

    private lateinit var binding: FragmentAssignmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAssignmentBinding.inflate(inflater,container,false)
        return binding.root

    }


}