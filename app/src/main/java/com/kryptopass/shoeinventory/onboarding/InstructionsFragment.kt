package com.kryptopass.shoeinventory.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kryptopass.shoeinventory.databinding.FragmentInstructionsBinding
import timber.log.Timber

class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionsBinding.inflate( inflater, container, false)

        Timber.i("InstructionsFragment: onCreateView called")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("InstructionsFragment: onViewCreated called")

        binding.buttonAccept.setOnClickListener {
            val action = InstructionsFragmentDirections.actionInstructionsFragmentToShoeListFragment()
            findNavController().navigate(action)
        }
    }
}