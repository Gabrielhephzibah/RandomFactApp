package com.cherish.randomfactproject.ui.home

import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.cherish.randomfactproject.R
import com.cherish.randomfactproject.data.model.StoreItemList
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.remote.ResponseManager


import com.cherish.randomfactproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mnn()
        val x = null
        val l = listOf(x)
        print(l)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mnn()
        val x = null
        val l = listOf(x)
        print(l)
        homeAdapter = HomeAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToStoreDetails(
                it.id.toString(),
                it.description,
                it.name,
                it.category,
                it.price.toString(),
                it.imageUrl
            )
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = homeAdapter
        getItemFromDb()

        binding.addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addToStore)
        }

    }
/** This method get all item from store
 *
 * **/



    private fun getAllStore() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getAllStoreData().collect { result ->
                    when (result) {
                        is ResponseManager.Success -> {
                            if (result.data.isNotEmpty()) {
                                val response = result.data.map {
                                    StoreItemList(
                                        it.myId,
                                        it.title,
                                        it.description,
                                        it.price,
                                        it.image,
                                        it.category
                                    )
                                }
                                homeAdapter.submitList(response)
                            }

                        }
                        is ResponseManager.Failure -> {
                           result.throwable?.printStackTrace()
                            print(result.throwable?.localizedMessage)
                        }
                        is ResponseManager.Loading -> {
                            if (result.status) {
                                binding.progressBar.visibility = View.VISIBLE
                            } else {
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }

            }
        }

    }

    private fun getItemFromDb(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.getDataFromDb().collect { result ->
                    when (result) {
                        is ResponseManager.Success -> {
                            if (result.data.isNotEmpty()) {
                                println("This is from DB :::: ${result.data}")
                                val response = result.data.map {
                                    StoreItemList(
                                        it.myId,
                                        it.title,
                                        it.description,
                                        it.price,
                                        it.image,
                                        it.category
                                    )
                                }
                                homeAdapter.submitList(response)
                            }else{
                                println("This is from network :::: ")
                                getAllStore()
                            }
                        }
                        is ResponseManager.Failure -> {
                            result.throwable?.printStackTrace()
                            print(result.throwable?.localizedMessage)
                        }
                        is ResponseManager.Loading -> {
                            if (result.status) {
                                binding.progressBar.visibility = View.VISIBLE
                            } else {
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mnn() = runBlocking {
        launch {
            delay(1L)
            println("B")
        }
        println("A")
    }

}