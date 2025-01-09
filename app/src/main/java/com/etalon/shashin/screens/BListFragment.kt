package com.etalon.shashin.screens


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.etalon.shashin.Adapter
import com.etalon.shashin.R
import com.etalon.shashin.SportpitListener
import com.etalon.shashin.database.SportPit
import com.etalon.shashin.databinding.BListBinding

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

var click = 1

class BListFragment : Fragment() {
    lateinit var bListBinding: BListBinding

    val viewModel: ViewModel by activityViewModels()
    var adapter: Adapter? = null


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bListBinding = DataBindingUtil.inflate<BListBinding>(
            inflater,
            R.layout.b_list, container, false
        )

        activity?.onBackPressedDispatcher?.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_BListFragment_to_AEnterFragment)
                }
            })

        return bListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            click = 1
        }



        adapter = Adapter(SportpitListener { itemelement ->

            viewModel.onClickDetailSportPit(itemelement)

        })

        bListBinding.recyclerView.adapter = adapter

        bListBinding.lifecycleOwner = requireActivity()

        val category = viewModel.getCategoryVM()

        initCategoryList(category)

        swipeToDeleteHelper.attachToRecyclerView(bListBinding.recyclerView)

        viewModel.choosendetailSportpit.onEach {
            findNavController().navigate(BListFragmentDirections.actionBListFragmentToCDetail(it))
        }.launchIn(
            viewLifecycleOwner.lifecycleScope
        )


        bListBinding.buttonAddList.setOnClickListener {

            dialogNewSportpit(category)

        }

        bListBinding.buttonExit.setOnClickListener {
            requireActivity().finish()
            System.exit(0)
        }

        bListBinding.buttonBackList.setOnClickListener {
            findNavController().navigate(R.id.action_BListFragment_to_AEnterFragment)
        }

    }

    private fun dialogNewSportpit(category: String) {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(com.etalon.shashin.R.layout.allertdialog)
        //  dialog.setTitle("Title")
        var etName: EditText? = dialog.findViewById<EditText>(com.etalon.shashin.R.id.et)
        var etAmount: EditText? =
            dialog.findViewById<EditText>(com.etalon.shashin.R.id.edittextamounSportpit)
        var etPriceForOne: EditText? = dialog.findViewById<EditText>(com.etalon.shashin.R.id.et3)

        var etPriceZakupka: EditText? = dialog.findViewById<EditText>(com.etalon.shashin.R.id.etzakupka)
        var srokGodnosti: EditText? = dialog.findViewById<EditText>(com.etalon.shashin.R.id.etsrokgodnosti)

        var buttonOk: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btnok)
        var buttonCancel: Button? = dialog.findViewById<Button>(com.etalon.shashin.R.id.btncn)
        dialog.show()

        buttonOk?.setOnClickListener {
            if (etName?.text!!.isNotEmpty() && etAmount?.text!!.isNotEmpty() && etPriceForOne?.text!!.isNotEmpty()&& etPriceZakupka?.text!!.isNotEmpty()&& srokGodnosti?.text!!.isNotEmpty()) {
                val sportPit = SportPit(
                    0,
                    category,
                    etName.text.toString(),
                    etAmount.text.toString().toDouble(),
                    etPriceForOne.text.toString().toDouble(),
                    etPriceZakupka.text.toString().toDouble(),
                    srokGodnosti.text.toString(),
                    System.currentTimeMillis()
                )
                viewModel.insertSportPitVM(sportPit)
                dialog.dismiss()

            } else {
                Toast.makeText(requireContext(), resources.getString(R.string.nugnozapolnitvsepola), Toast.LENGTH_LONG)
                    .show()
            }
        }
        buttonCancel?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun initCategoryList(category: String) {
        when (category) {
            "Aminokistotu" -> {
                viewModel.listAminokislotu.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            "VitaminsAndMinerals" -> {
                viewModel.listVitaminsAndMinerals.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
            "WaterAndDrinks" -> {
                viewModel.listWaterAndDrinks.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            "Gainers" -> {
                viewModel.listGainers.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
            "CreatinAndEnergetics" -> {
                viewModel.listCreatinAndEnergetics.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            "Proteins" -> {
                viewModel.listProteins.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
            "ProteinsBatonchic" -> {
                viewModel.listProteinsBatonchic.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            "FatBurn" -> {
                viewModel.listFatBurn.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            }
            "Acceccuares" -> {
                viewModel.listAcceccuares.onEach {
                    adapter?.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    private val swipeToDeleteHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val pos = viewHolder.adapterPosition
            val builder = AlertDialog.Builder(
                requireContext(),
                R.style.AlertDialogThemeee
            )
            builder.setTitle("Удалить позицию ${pos+1}?")
                .setCancelable(false)
                .setPositiveButton(
                    "Удалить"
                ) { dialog, which ->
                    val category = viewModel.getCategoryVM()

                    when (category) {
                        "Aminokistotu" -> {
                            val name = viewModel.listAminokislotu.value.get(pos)?.itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "VitaminsAndMinerals" -> {

                            val name = viewModel.listVitaminsAndMinerals.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "WaterAndDrinks" -> {

                            val name = viewModel.listWaterAndDrinks.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "Gainers" -> {

                            val name = viewModel.listGainers.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "CreatinAndEnergetics" -> {

                            val name = viewModel.listCreatinAndEnergetics.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "Proteins" -> {

                            val name = viewModel.listProteins.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "ProteinsBatonchic" -> {

                            val name = viewModel.listProteinsBatonchic.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "FatBurn" -> {

                            val name = viewModel.listFatBurn.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }
                        "Acceccuares" -> {

                            val name = viewModel.listAcceccuares.value.get(pos).itemName
                            viewLifecycleOwner.lifecycleScope.launch {
                                Toast.makeText(
                                    requireContext(),
                                    name + " удалён",
                                    Toast.LENGTH_LONG
                                ).show()
                                if (name != null) {
                                    viewModel.deleteSportPitByName(name)
                                }
                                return@launch
                            }
                        }


                    }
                }
                .setNegativeButton(
                    "Отмена"
                ) { dialog, which ->
                    bListBinding.recyclerView.getAdapter()!!
                        .notifyItemChanged(viewHolder.adapterPosition)
                }
            val alert = builder.create()
            alert.setOnShowListener {
                val btnPositive = alert.getButton(Dialog.BUTTON_POSITIVE)
                btnPositive.textSize = 25f
                btnPositive.setTextColor(resources.getColor(R.color.red))//()
                val btnNeuteral = alert.getButton(Dialog.BUTTON_NEGATIVE)
                btnNeuteral.textSize = 25f
                btnNeuteral.setTextColor(resources.getColor(R.color.black))
                val layoutParams: LinearLayout.LayoutParams =
                    btnPositive.layoutParams as LinearLayout.LayoutParams  // центрируем кнопки
                layoutParams.weight = 10F
                btnPositive.layoutParams = layoutParams
                btnNeuteral.layoutParams = layoutParams
            }
            alert.show()
            val textViewId =
                alert.context.resources.getIdentifier("android:id/alertTitle", null, null)
            val tv = alert.findViewById<View>(textViewId) as TextView
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER)                            // заголовок по центру
            tv.setTextColor(resources.getColor(R.color.black))
        }
    })
}