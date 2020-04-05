package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.R
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_kotin_synthetic.*

class KotlinSyntheticFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_kotin_synthetic, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initObserver()
    }

    private fun initListener() {
        bKotlinSyntheticCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            tvKotlinSyntheticCounter.text = (it ?: 0).toString()
        })
    }

    companion object {
        fun newInstance() = KotlinSyntheticFragment()
    }
}
