package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.R
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel.MainViewModel

class FindViewByIdFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var tvFindViewByIdCounter: AppCompatTextView
    private lateinit var bFindViewByIdIncCounter: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_view_by_id, container, false)

        tvFindViewByIdCounter = view.findViewById(R.id.tvFindViewByIdCounter)
        bFindViewByIdIncCounter = view.findViewById(R.id.bFindViewByIdIncCounter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initObserver()
    }

    private fun initListener() {
        bFindViewByIdIncCounter.setOnClickListener {
            viewModel.incCounter()
        }
    }

    private fun initObserver() {
        viewModel.counter.observe(viewLifecycleOwner, Observer {
            tvFindViewByIdCounter.text = (it ?: 0).toString()
        })
    }

    companion object {
        fun newInstance() = FindViewByIdFragment()
    }
}
