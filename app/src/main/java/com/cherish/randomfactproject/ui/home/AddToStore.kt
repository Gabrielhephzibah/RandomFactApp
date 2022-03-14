package com.cherish.randomfactproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.remote.ResponseManager
import com.cherish.randomfactproject.databinding.FragmentAddToStoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddToStore : Fragment() {
    private var _binding: FragmentAddToStoreBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddToStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = binding.name.text
        val category = binding.category.text
        val url = binding.image.text
        val price = binding.price.text
        val description = binding.desc.text

        binding.button.setOnClickListener {
            addStoreItem(
                StoreRequest(
                    category.toString(),
                    description.toString(),
                    url.toString(),
                    price.toString().toDouble(),
                    name.toString(),
                )
            )
        }

    }


    private fun addStoreItem(item: StoreRequest) {
        homeViewModel.addStoreItem(item).observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResponseManager.Failure -> println("ERROR")
                is ResponseManager.Loading -> {
                    if (it.status) {
                        println("LOADING")
                    } else {
                        println("Stop loading")
                    }
                }
                is ResponseManager.Success -> {
                    print(it.data)
                }
            }
        })
    }


}