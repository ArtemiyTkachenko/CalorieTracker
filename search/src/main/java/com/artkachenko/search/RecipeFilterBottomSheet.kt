package com.artkachenko.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.artkachenko.core_api.network.models.FilterWrapper
import com.artkachenko.search.databinding.FragmentFilterBottomSheetBinding
import com.artkachenko.ui_utils.PRESETS
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFilterBottomSheet : BottomSheetDialogFragment(), RecipeFilterActions  {

    private val viewModel by viewModels<RecipeSearchViewModel>()

    private var binding: FragmentFilterBottomSheetBinding? = null

    private val filterAdapter = FilterAdapter(this)

    private val argPresets by lazy {
        arguments?.getParcelable<FilterWrapper>(PRESETS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            filters.adapter = filterAdapter
//            filterAdapter.setChecked(argPresets)
            apply.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun filterChecked(filter: Map.Entry<String, String>, isChecked: Boolean) {
        viewModel.processFilter(filter, isChecked)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {

        fun newInstance(filtersWrapper: FilterWrapper?): RecipeFilterBottomSheet {
            val bundle = Bundle().apply {
                putParcelable(PRESETS, filtersWrapper)
            }
            return RecipeFilterBottomSheet().apply {
                arguments = bundle
            }
        }
    }
}