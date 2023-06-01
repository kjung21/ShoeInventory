package com.kryptopass.shoeinventory.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.kryptopass.shoeinventory.R
import com.kryptopass.shoeinventory.databinding.FragmentShoeListBinding
import com.kryptopass.shoeinventory.databinding.ItemShoeBinding
import com.kryptopass.shoeinventory.models.Shoe
import timber.log.Timber

class ShoeListFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var shoeListViewModel: ShoeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)

        Timber.i("ShoeListFragment: onCreateView called")

        shoeListViewModel = activityViewModels<ShoeListViewModel>().value
        with(binding) {
            shoeListViewModel = this.shoeListViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.i("ShoeListFragment: onViewCreated called")

        shoeListViewModel.shoes.observe(viewLifecycleOwner) { shoes: MutableList<Shoe> ->
            updateShoeList(shoes)
        }
        shoeListViewModel.addShoeEvent.observe(viewLifecycleOwner) { it: Boolean ->
            if (it) addShoeDetails()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.loginFragment) {
            findNavController().navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
            return true
        }
        return false
    }

    private fun addShoeDetails() {
        val destination = ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailsFragment()
        findNavController().navigate(destination)
        shoeListViewModel.addShoeEventCompleted()
    }

    private fun updateShoeList(shoes: MutableList<Shoe>) {
        val listLayout = binding.linearLayoutShoes
        listLayout.removeAllViews()

        for (shoe in shoes) {
            val item = ItemShoeBinding.inflate(layoutInflater)
            item.shoe = shoe

            listLayout.addView(item.root,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT))
        }
    }
}