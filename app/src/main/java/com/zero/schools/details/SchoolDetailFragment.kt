package com.zero.schools.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.zero.schools.R
import com.zero.schools.databinding.FragmentSchoolDetailBinding
import com.zero.schools.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDetailFragment : Fragment() {
    private val viewModel by viewModels<SchoolDetailViewModel>()
    private val args: SchoolDetailFragmentArgs by navArgs()

    private var _binding: FragmentSchoolDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSchoolDetail(args.schoolId)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.loader.observe(viewLifecycleOwner) { binding.loading.isVisible = it }
        viewModel.schoolDetail.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    setupUi(it)
                } ?: kotlin.run {
                    requireActivity().showToast(getString(R.string.empty_details))
                }
            } else {
                requireActivity().showToast(
                    result.exceptionOrNull()?.message ?: getString(R.string.generic_error)
                )
            }
        }
    }

    private fun setupUi(it: SchoolDetail) {
        with(binding) {
            schoolName.text = String.format(getString(R.string.school_name), it.name)
            number.text =
                String.format(getString(R.string.school_number_of_tester), it.numberOfTestTaker)
            avgScore.text = String.format(getString(R.string.school_avg_score), it.avgScore)
        }
    }
}