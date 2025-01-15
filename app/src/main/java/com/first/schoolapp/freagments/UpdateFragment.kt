package com.first.schoolapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.schoolapp.adaptor.UpdatesAdaptor
import com.first.schoolapp.databinding.FragmentUpdateBinding
import com.first.schoolapp.entity.UpdateData

import com.first.schoolapp.viewmodel.UpdatesViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var updateAdapter: UpdatesAdaptor
    private lateinit var updateViewModel: UpdatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        updateViewModel = ViewModelProvider(this)[UpdatesViewModel::class.java]

        // Setup RecyclerView
        binding.addUpdateRecycler.layoutManager = LinearLayoutManager(context)
        updateAdapter = UpdatesAdaptor(listOf(), updateViewModel)
        binding.addUpdateRecycler.adapter = updateAdapter

        // Observe updates
        updateViewModel.allUpdates.observe(viewLifecycleOwner) { updates ->
            updateAdapter.updateData(updates)
        }

        // Submit update
        binding.submitUpdate.setOnClickListener {
            val updateText = binding.addUpdate.text.toString()
            if (updateText.isNotEmpty()) {
                val update = UpdateData(update = updateText)
                updateViewModel.addUpdate(update)
                Toast.makeText(context, "Update added successfully", Toast.LENGTH_SHORT).show()
                binding.addUpdate.text.clear()
            }
        }
}
}
