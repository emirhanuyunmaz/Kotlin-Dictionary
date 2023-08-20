package com.emirhanuyunmaz.kotlindictionary

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emirhanuyunmaz.kotlindictionary.databinding.RecyclerviewRowBinding
import com.emirhanuyunmaz.kotlindictionary.room_database_dictionary.Dictionary

class DictionaryAdapter(var dictionaryArrayList:ArrayList<Dictionary>) : RecyclerView.Adapter<DictionaryAdapter.DictionaryVH>() {

    val dictionaryColorsArray= arrayOf("#d55142","#5c9f86","#d7b694","#e5aead","#c19280")

    class DictionaryVH(var binding:RecyclerviewRowBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryVH {
        val viewBinding=RecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DictionaryVH(viewBinding)
    }

    override fun getItemCount(): Int {
        return dictionaryArrayList.size
    }

    override fun onBindViewHolder(holder: DictionaryVH, position: Int) {
        holder.itemView.setBackgroundColor(Color.parseColor(dictionaryColorsArray[position%5]))
        holder.binding.textViewEnglishWord.text=dictionaryArrayList[position].englishWord
        holder.binding.textViewTurkishWord.text=dictionaryArrayList[position].turkishWord
    }

}