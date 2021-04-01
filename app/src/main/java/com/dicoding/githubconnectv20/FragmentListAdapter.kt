package com.dicoding.githubconnectv20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FragmentListAdapter: RecyclerView.Adapter<FragmentListAdapter.ListViewHolder>() {
    private val mData = ArrayList<RvUser>()

    fun setData(items: ArrayList<RvUser>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val btnFavorite: ImageView = itemView.findViewById(R.id.icon_favorite)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val userItems = mData[position]
        Glide.with(holder.itemView.context)
                .load(userItems.avatar)
                .apply(RequestOptions().override(55,55))
                .into(holder.imgPhoto)
        holder.tvName.text = userItems.name

        holder.btnFavorite.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Added to favorite users", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = mData.size
}