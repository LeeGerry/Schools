package com.zero.schools.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zero.schools.databinding.FragmentSchoolListBinding
import com.zero.schools.list.adapter.SchoolsAdapter
import com.zero.schools.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolListFragment : Fragment() {

    private val viewModel by viewModels<SchoolListViewModel>()

    private var _binding: FragmentSchoolListBinding? = null
    private val binding get() = _binding!!

    private val adapter: SchoolsAdapter by lazy {
        SchoolsAdapter {
            SchoolListFragmentDirections.actionSchoolListFragmentToSchoolDetailFragment(it).apply {
                findNavController().navigate(this)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolListBinding.inflate(inflater, container, false)
        binding.schoolRecyclerView.adapter = adapter
        viewModel.loader.observe(viewLifecycleOwner) { binding.loading.isVisible = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EspressoIdlingResource.increment()
        viewModel.schoolList.observe(viewLifecycleOwner) { result ->
            when (result.isSuccess) {
                true -> adapter.setData(result.getOrNull() ?: emptyList())
                false -> Toast.makeText(
                    requireActivity(),
                    "error: ${result.exceptionOrNull()?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            EspressoIdlingResource.decrement()
        }

    }
}