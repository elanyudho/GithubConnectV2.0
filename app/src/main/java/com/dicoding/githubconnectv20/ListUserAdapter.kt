package com.dicoding.githubconnectv20


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubconnectv20.databinding.ItemRowUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val mData = ArrayList<Users>()

    fun setData(items: ArrayList<Users>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){

        val btnFavorite: ImageView = itemView.findViewById(R.id.icon_favorite)
        fun bind(userItems: Users) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(userItems.avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(imgItemPhoto)
                binding.tvItemName.text = userItems.name
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val view = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(mData[position])
        holder.btnFavorite.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Added to favorite users", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener{ onItemClickCallback.onItemClicked(mData[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}