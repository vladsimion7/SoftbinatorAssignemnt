package ro.vlad.simion.softbinatior.ui.pagination

import androidx.recyclerview.widget.DiffUtil
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel

class AnimalsDiffCallback(
    private val newList: List<AnimalModel>,
    private val oldList: List<AnimalModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
      return  oldList.size
    }

    override fun getNewListSize(): Int {
      return  newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem == oldItem
    }

}