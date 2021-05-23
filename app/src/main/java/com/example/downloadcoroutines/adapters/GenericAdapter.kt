package com.example.downloadcoroutines.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadcoroutines.BR
import com.example.downloadcoroutines.R
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class GenericAdapter @JvmOverloads constructor(
    private var mObjects: ArrayList<Any>,
    onItemClickListener: OnItemClickListener<Any>? = null,
    layoutValue: Int = 0
) : RecyclerView.Adapter<GenericAdapter.MyViewHolder>() {
    fun getmObjects(): List<Any> {
        return mObjects
    }

    var layoutValue: Int
    private val onItemClickListener: OnItemClickListener<Any>? = onItemClickListener
    private var emptyTextView: TextView? = null
    private var emptyViewText = R.string.app_name
    private val fragment = null
    private var isUserPhotos = false
    fun setEmptyTextView(emptyTextView: TextView?, @StringRes emptyViewText: Int) {
        this.emptyTextView = emptyTextView
        this.emptyViewText = emptyViewText
    }

    fun setUserPhotos(isUserPhotos: Boolean) {
        this.isUserPhotos = isUserPhotos
    }

    public fun notifyAdapter(mObjects: ArrayList<Any>) {
        this.mObjects = mObjects
        notifyDataSetChanged()
    }

    interface OnItemClickListener<Any> {
        fun onItemClick(view: View?, position: Int, `object`: kotlin.Any)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.setVariable(BR.model, getItem(position))
        holder.binding.setVariable(BR.itemClickListener, onItemClickListener)
        holder.binding.setVariable(BR.index, position)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutValue
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    fun add(`object`: Any) {
        mObjects.add(`object`)
        notifyItemInserted(itemCount - 1)
    }

    fun addAll(objects: List<Any>?) {
        val posStart = mObjects.size
        mObjects.addAll(objects!!)
        notifyItemRangeInserted(posStart, itemCount - 1)
    }

    /**
     * Remove all elements from the list.
     */
    fun clear() {
        val size = itemCount
        mObjects.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    fun getItem(position: Int): Any {
        return mObjects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    fun getPosition(item: Any): Int {
        return mObjects.indexOf(item)
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    fun insert(`object`: Any, index: Int) {
        mObjects.add(index, `object`)
        notifyItemInserted(index)
    }

    fun insert(objects: List<Any>?, index: Int) {
        val posStart = mObjects.size
        mObjects.addAll(index, objects!!)
        notifyItemRangeInserted(posStart, itemCount - 1)
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    fun remove(`object`: Any) {
        val position = getPosition(`object`)
        mObjects.remove(`object`)
        notifyItemRemoved(position)
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    fun sort(comparator: Comparator<in Any>?) {
        Collections.sort(mObjects, comparator)
        notifyItemRangeChanged(0, itemCount)
    }

    fun addItem(position: Int, model: Any) {
        mObjects.add(position, model)
        notifyItemInserted(position)
    }

    fun setItem(position: Int, model: Any) {
        mObjects[position] = model
        notifyItemChanged(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = mObjects.removeAt(fromPosition)
        mObjects.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun update(models: List<Any>, replaceExisting: Boolean) {
        /*removal of objects creates problem with pagination logic, so this is skipped.*/
//        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models, replaceExisting)
        applyAndAnimateMovedItems(models)

        //Collections.sort(models);
    }

    private fun applyAndAnimateRemovals(newModels: List<Any>) {
        for (mObject in mObjects) {
            if (!newModels.contains(mObject)) {
                remove(mObject)
            }
        }
    }

    private fun applyAndAnimateAdditions(
        newModels: List<Any>,
        replaceExisting: Boolean
    ) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            val index = mObjects.indexOf(model)
            if (!mObjects.contains(model)) {
                addItem(i, model)
            } else {
                if (replaceExisting) {
                    setItem(index, model)
                }
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<Any>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = mObjects.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    class MyViewHolder(var binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.executePendingBindings()
        }
    }

    init {
        this.layoutValue = layoutValue
    }
}