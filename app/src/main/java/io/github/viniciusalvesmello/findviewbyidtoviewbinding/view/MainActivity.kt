package io.github.viniciusalvesmello.findviewbyidtoviewbinding.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.github.viniciusalvesmello.findviewbyidtoviewbinding.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    private fun initListener() {
        bFindViewById.setOnClickListener {
            initFragment(FindViewByIdFragment.newInstance())
        }
        bDataBinding.setOnClickListener {
            initFragment(DataBindingFragment.newInstance())
        }
        bKotlinSynthetic.setOnClickListener {
            initFragment(KotlinSyntheticFragment.newInstance())
        }
        bViewBinding.setOnClickListener {
            initFragment(ViewBindingFragment.newInstance())
        }
        bViewBindingAndDataBinding.setOnClickListener {
            initFragment(ViewBindingAndDataBindingFragment.newInstance())
        }
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}
