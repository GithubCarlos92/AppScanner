package com.carlos.appscanner.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carlos.appscanner.R
import com.carlos.appscanner.databinding.ItemDatoBinding
import com.carlos.appscanner.model.Data

//declaramos en el constructor la lista que vamos a manipular
//implementar los metodos del adaptador
class DataAdapter(var datos: List<Data> = listOf())
    :RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {

    private var variableList: ArrayList<Data> = ArrayList()

    //por el momento sin usar este arrayList
    fun addItems(items:ArrayList<Data>){
        this.variableList = items
    }

    //declaramos el view holder(clase interna)
    //XML Y LOS DATOS
    inner class DataAdapterViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        //item_datos = Item + Binding
        private val binding: ItemDatoBinding = ItemDatoBinding.bind(itemView)

        fun bind(data:Data) = with(binding){
          //  var x:Int = data.codigo
            txtX.text = data.nombre
            txtY.text = data.fecha

        }
    }

    fun updateListData(datos: List<Data>){
        this.datos = datos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_dato,parent,false)
        return DataAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {

        //val data:Data = datos[position]
        holder.bind(datos[position])

    }
    //retorna cuantos elementos tiene la lista
    override fun getItemCount(): Int {
    return datos.size
    }
}