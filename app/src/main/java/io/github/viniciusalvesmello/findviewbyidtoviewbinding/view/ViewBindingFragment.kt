package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.databinding.FragmentViewBindingBinding
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class ViewBindingFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentViewBindingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewBindingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.bViewBindingCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            binding.tvViewBindingCounter.text = (it ?: 0).toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ViewBindingFragment()
    }
}
