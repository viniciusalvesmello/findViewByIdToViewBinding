package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.databinding.FragmentDataBindingBinding
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class DataBindingFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentDataBindingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBindingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = mainViewModel
        }
        return binding.root
    }

    companion object {
        fun newInstance() = DataBindingFragment()
    }
}
