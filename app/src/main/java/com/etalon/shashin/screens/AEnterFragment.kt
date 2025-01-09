package com.etalon.shashin.screens

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.etalon.shashin.R
import com.etalon.shashin.databinding.AEnterFrBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AEnterFragment : Fragment() {
    lateinit var bindingEnterfr: AEnterFrBinding
    val viewModelEnter: ViewModel by viewModels()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bindingEnterfr = DataBindingUtil.inflate<AEnterFrBinding>(
            inflater,
            R.layout.a_enter_fr, container, false
        )




        activity?.onBackPressedDispatcher?.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed(){

                }
            })

        return bindingEnterfr.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingEnterfr.butAminokistotu.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("Aminokistotu")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }

        bindingEnterfr.butVitaminsAndMinerals.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("VitaminsAndMinerals")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butWaterAndDrinks.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("WaterAndDrinks")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butGainers.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("Gainers")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butCreatinAndEnergetics.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("CreatinAndEnergetics")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butProteins.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("Proteins")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butProteinsBatonchic.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("ProteinsBatonchic")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }
        bindingEnterfr.butFatBurn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("FatBurn")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }

        bindingEnterfr.butAcceccuares.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModelEnter.insertCategoryVM("Acceccuares")
                exitAnimation(bindingEnterfr.linearButtons)
            }
        }


    }

    suspend fun exitAnimation(view1: View){
        delay(100)
        repeat(100) {
            delay(3)
            view1.alpha -= 0.01f

        }
        findNavController().navigate(R.id.action_AEnterFragment_to_BListFragment)
    }
}