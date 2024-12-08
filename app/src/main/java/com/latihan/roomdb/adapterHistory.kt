package com.latihan.roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.latihan.roomdb.database.historyBelanja

class adapterHistory (private val historyBelanja: MutableList<historyBelanja>): RecyclerView.Adapter<adapterHistory.ListViewHolder>()
{

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var _tvItemBarang = itemView.findViewById<TextView>(R.id.nama_item_history)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlah_item_history)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tv_judul_history)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_history, parent, false
        )
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(holder: adapterHistory.ListViewHolder, position: Int) {
        var history = historyBelanja[position]

        holder._tvTanggal.setText(history.tanggal)
        holder._tvItemBarang.setText(history.item)
        holder._tvJumlahBarang.setText(history.jumlah)


    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }


    fun isiData(history: List<historyBelanja>){
        historyBelanja.clear()
        historyBelanja.addAll(history)
        notifyDataSetChanged()
    }


}