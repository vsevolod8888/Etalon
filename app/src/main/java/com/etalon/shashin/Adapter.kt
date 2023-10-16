package com.etalon.shashin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.etalon.shashin.database.SportPit
import com.etalon.shashin.databinding.HolderBinding

class Adapter(val clickListener: SportpitListener) :
    ListAdapter<SportPit, Holder>(SleepNightDiffCallback()) {
    var selectedItemPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.from(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemSportpit = getItem(position)

        holder.itemView.setOnClickListener {

            clickListener.onClick(itemSportpit)
            selectItemPosition(position)
        }
        holder.tpBind(
            itemSportpit, position
        )
    }

    fun selectItemPosition(itemPos: Int) {
        selectedItemPosition = itemPos
        notifyDataSetChanged()
    }
}

class Holder(val binding: HolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun tpBind(
        item: SportPit, position: Int

    ) {
        binding.numberPosition.text = (position+1).toString()
        binding.amount.text = item.amount.toString()
        binding.nameItem.text = item.itemName
        binding.priceOne.text = "${item.priceforone} грн"
        binding.priceAll.text = "${item.priceforone.times(item.amount).toFloat()} грн"
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HolderBinding.inflate(layoutInflater, parent, false)
            return Holder(binding)
        }
    }

}


class SleepNightDiffCallback : DiffUtil.ItemCallback<SportPit>() {
    override fun areItemsTheSame(
        oldItem: SportPit,
        newItem: SportPit
    ): Boolean {
        return oldItem.ID == newItem.ID
    }

    override fun areContentsTheSame(
        oldItem: SportPit,
        newItem: SportPit
    ): Boolean {
        return oldItem == newItem
    }
}

class SportpitListener(val clickListener: (sportpitDetail: SportPit) -> Unit) {
    fun onClick(sportpitDetail: SportPit) = clickListener(sportpitDetail)
}