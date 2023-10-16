package com.etalon.shashin.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContentProviderCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.etalon.shashin.R
import com.etalon.shashin.database.SportPit
import com.etalon.shashin.databinding.CDetailBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CDetail : Fragment() {
    lateinit var bindingDetail: CDetailBinding
    val viewModel: ViewModel by activityViewModels()
    val args : CDetailArgs by navArgs()
    lateinit var detailSportpit: SportPit

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bindingDetail = DataBindingUtil.inflate<CDetailBinding>(
            inflater,
            R.layout.c_detail, container, false
        )



        activity?.onBackPressedDispatcher?.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })

        return bindingDetail.root
    }

    @SuppressLint("SimpleDateFormat", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailSportpit = args.sportpit

            showUpdates()

            val dateText = detailSportpit.date
            val locale = Locale("ru", "RU")
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss");

            val date: Date = Date(dateText);
            val time: String = simpleDateFormat.format(date);
            bindingDetail.textViewDateDetail.text = time



        bindingDetail.buttonAddDetail.setOnClickListener {
            dialogAdd()
        }
        bindingDetail.buttonMinusDetail.setOnClickListener {
            dialogMinus()
        }

        bindingDetail.buttonBackToListFromDetail.setOnClickListener {

            findNavController().navigate(R.id.action_CDetail_to_BListFragment)
        }
        bindingDetail.textViewPriceOneDetail.setOnClickListener {
            updatePrice()
        }



    }

    private fun updatePrice(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(com.etalon.shashin.R.layout.allertdialognewprice)

        var etNewPrice: EditText? =
            dialog.findViewById<EditText>(com.etalon.shashin.R.id.edittextNewPrice)

        var buttonOk: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btnokNewPrice)
        var buttonCancel: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btncnNewPrice)
        dialog.show()

        buttonOk?.setOnClickListener {
            if (etNewPrice?.text!!.isNotEmpty()){
                detailSportpit.priceforone.let { it1 ->
                    viewModel.updatePrice(
                        etNewPrice.text.toString().toDouble(),
                        detailSportpit.itemName
                    )
                }

                detailSportpit.priceforone = etNewPrice.text.toString().toDouble()

                showUpdates()
            } else {
                Toast.makeText(requireContext(), "Нужно заполнить все поля", Toast.LENGTH_LONG)
                    .show()
            }
            dialog.dismiss()
        }
        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun dialogAdd() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(com.etalon.shashin.R.layout.allertdialogplus)

        var etAmount: EditText? =
            dialog.findViewById<EditText>(com.etalon.shashin.R.id.edittextamounPlus)
        var buttonOk: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btnokPlus)
        var buttonCancel: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btncnPlus)
        dialog.show()

        buttonOk?.setOnClickListener {
            if (etAmount?.text!!.isNotEmpty()) {

                detailSportpit.amount.let { it1 ->
                    viewModel.updateAmount(
                        it1.plus(etAmount.text.toString().toDouble()),
                        detailSportpit.itemName
                    )
                }


                detailSportpit.amount = detailSportpit.amount.plus(etAmount.text.toString().toDouble())

                showUpdates()

            } else {
                Toast.makeText(requireContext(), "Нужно заполнить все поля", Toast.LENGTH_LONG)
                    .show()
            }
            dialog.dismiss()
        }
        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun dialogMinus() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(com.etalon.shashin.R.layout.allertdialogminus)

        var etAmount: EditText? =
            dialog.findViewById<EditText>(com.etalon.shashin.R.id.edittextamountMinus)

        var buttonOk: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btnokMinus)
        var buttonCancel: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btncnMinus)
        dialog.show()

        buttonOk?.setOnClickListener {
            if (etAmount?.text!!.isNotEmpty()) {
                detailSportpit.amount.let { it1 ->
                    viewModel.updateAmount(
                        it1.minus(etAmount.text.toString().toDouble()),
                        detailSportpit.itemName
                    )
                }

                detailSportpit.amount = detailSportpit.amount.minus(etAmount.text.toString().toDouble())
                showUpdates()


            } else {
                Toast.makeText(requireContext(), "Нужно заполнить все поля", Toast.LENGTH_LONG)
                    .show()
            }
            dialog.dismiss()
        }
        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showUpdates(){
        bindingDetail.textViewTittleDetail.text = detailSportpit.itemName

        bindingDetail.textViewPriceOneDetail.text = detailSportpit.priceforone.toString()+" грн"

        bindingDetail.textViewPriceOneDetailzakupka.text = detailSportpit.pricezakupka.toString()+" грн"

        bindingDetail.textViewPriceAllDetailzakupka.text =  detailSportpit.amount.times(detailSportpit.pricezakupka).toFloat().toString()+" грн"

        bindingDetail.textViewAmountDetail.text = detailSportpit.amount.toString()

        bindingDetail.textViewsrok.text = detailSportpit.srokgodnosti.toString()

        bindingDetail.textViewPriceAllDetail.text = detailSportpit.amount.times(detailSportpit.priceforone).toFloat().toString()+" грн"


    }



}