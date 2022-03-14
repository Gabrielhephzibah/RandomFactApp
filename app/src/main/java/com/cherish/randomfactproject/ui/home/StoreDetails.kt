package com.cherish.randomfactproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.cherish.randomfactproject.databinding.FragmentStoreDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreDetails : Fragment() {
   private var _binding : FragmentStoreDetailsBinding? = null
    private val binding  get() = _binding!!
    private val args: StoreDetailsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentStoreDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.name.text = args.name
        binding.price.text = args.price.toString()
        binding.description.text = args.description
        Glide.with(requireActivity())
            .load(args.url)
            .into(binding.image)

    }


}