package com.first.schoolapp.freagments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.first.schoolapp.R
import com.first.schoolapp.adaptor.ItemsAdaptor
import com.first.schoolapp.adaptor.UpdatesAdaptor
import com.first.schoolapp.databinding.FragmentHomeBinding
import com.first.schoolapp.viewmodel.ItemsViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var itemsViewModel: ItemsViewModel
    private lateinit var itemsAdaptor: ItemsAdaptor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list
        itemsAdaptor = ItemsAdaptor(emptyList()) { service ->
            itemsViewModel.onServiceClicked(service)
        }


        val recyclerView: RecyclerView = view.findViewById(R.id.menuView)
        recyclerView.adapter = itemsAdaptor
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)


        itemsViewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)


        itemsViewModel.items.observe(viewLifecycleOwner) { items ->
            itemsAdaptor.updateData(items)
        }


        itemsViewModel.navigateTo.observe(viewLifecycleOwner) { fragmentClass ->
            fragmentClass?.let {
                navigateToFragment(it)
            }
        }





    }

    private fun navigateToFragment(fragmentClass: Class<out Fragment>) {
        val fragment= fragmentClass.newInstance()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}