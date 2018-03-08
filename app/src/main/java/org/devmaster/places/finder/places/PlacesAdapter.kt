package org.devmaster.places.finder.places

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.devmaster.places.finder.databinding.PlaceItemViewBinding
import org.devmaster.places.finder.domain.Place

class PlacesAdapter : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    private val mPlaces = mutableListOf<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceViewHolder(PlaceItemViewBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.mBinding.place = mPlaces[position]
        holder.mBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return mPlaces.size
    }

    fun addAll(places: Iterable<Place>) {
        mPlaces.addAll(places)
    }

    fun clear() {
        mPlaces.clear()
    }

    class PlaceViewHolder (val mBinding: PlaceItemViewBinding)
        : RecyclerView.ViewHolder(mBinding.root)
}