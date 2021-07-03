package com.example.retrofitlearning.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlearning.R
import com.example.retrofitlearning.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*


class RecyclerViewAdapter(private val cryptoModelsArray: ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {


    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#ff0000","#00ff00","#0000ff","#9acd32","#00ffff","#ff00ff")

    class RowHolder( view: View) : RecyclerView.ViewHolder(view) {

        fun bind(cryptoModel : CryptoModel, colors: Array<String>, position: Int,listener : Listener) {

            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))
            itemView.currencyText.text = cryptoModel.currency
            itemView.priceText.text = cryptoModel.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_layout, parent, false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoModelsArray[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoModelsArray.count()
    }


}