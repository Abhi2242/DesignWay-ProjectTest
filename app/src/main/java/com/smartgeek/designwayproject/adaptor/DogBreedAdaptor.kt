package com.smartgeek.designwayproject.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartgeek.designwayproject.R
import com.smartgeek.designwayproject.activities.dogpages.DogBreedDetailsActivity
import com.smartgeek.designwayproject.model.dogbread.DogBreedItem

class DogBreedAdaptor(private val dogList: List<DogBreedItem>): RecyclerView.Adapter<DogBreedAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.breadData = dogList[position]

        holder.breadName.text = dogList[position].name
//        holder.breadGroup.text = dogList[position].breed_group
//        holder.breadOrigin.text = dogList[position].origin

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, DogBreedDetailsActivity::class.java)
            intent.putExtra(DogBreedDetailsActivity.ARG_PROFILE_ID, holder.breadData!!.reference_image_id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var breadData: DogBreedItem? = null
        val breadName: TextView = itemView.findViewById(R.id.tv_display_name)
//        val breadGroup: TextView = itemView.findViewById(R.id.tv_display_group)
//        val breadOrigin: TextView = itemView.findViewById(R.id.tv_display_origin)

    }
}