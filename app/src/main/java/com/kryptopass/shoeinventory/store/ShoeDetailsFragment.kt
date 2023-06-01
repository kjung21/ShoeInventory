package com.kryptopass.shoeinventory.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kryptopass.shoeinventory.R
import com.kryptopass.shoeinventory.databinding.FragmentShoeDetailsBinding
import timber.log.Timber

class ShoeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailsBinding
    private val shoeDetailsViewModel: ShoeDetailsViewModel by viewModels()
    private val shoeListViewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_details, container, false)

        Timber.i("ShoeDetailsFragment: onCreateView called")

        with (binding) {
            shoeDetailsViewModel = this.shoeDetailsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("ShoeDetailsFragment: onViewCreated called")

        this.shoeDetailsViewModel.validationFailedEvent.observe(viewLifecycleOwner) {
            if (it) onValidationFailed()
        }
        this.shoeDetailsViewModel.saveEvent.observe(viewLifecycleOwner) {
            if (it) onAdd()
        }
        this.shoeDetailsViewModel.cancelEvent.observe(viewLifecycleOwner) {
            if (it) onCancel()
        }
    }

    private fun onValidationFailed() {
        shoeDetailsViewModel.onValidationFailedEventCompleted()
        Toast.makeText(requireContext(), getString(R.string.error_details), Toast.LENGTH_SHORT)
            .show()
    }

    private fun onAdd() {
        shoeDetailsViewModel.onSaveEventCompleted()

        val shoe = shoeDetailsViewModel.shoe.value
        shoe?.let {
            shoeListViewModel.addShoe(it)
            findNavController().navigateUp()
        }
    }

    private fun onCancel() {
        shoeDetailsViewModel.onCancelEventCompleted()
        findNavController().navigateUp()
    }
}