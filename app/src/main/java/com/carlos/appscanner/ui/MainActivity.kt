package com.carlos.appscanner.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.carlos.appscanner.R
import com.carlos.appscanner.databinding.ActivityMainBinding
import com.carlos.appscanner.model.Data
import com.carlos.appscanner.model.SQLiteHelper
import com.google.zxing.integration.android.IntentIntegrator
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteHelper: SQLiteHelper

    //variable de tipo DataAdapter y luego la inicializo
    private lateinit var adapter : DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppScanner)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.txtResultado
        binding.txtFecha

        sqliteHelper = SQLiteHelper(this)

        //llamamos al boton de scannear
        binding.btnScanner.setOnClickListener {
            initScanner()

        }

        //llamar al boton  sincronizar
        binding.btnSincronizar.setOnClickListener {
            setupAdapter()
            getData()
            Toast.makeText(this, "Sincronizado", Toast.LENGTH_SHORT).show()
        }
    }

    //ya lo infiere al poner el with(binding)
    private fun setupAdapter() = with(binding){
        adapter = DataAdapter()
        rvData.adapter = adapter
    }

    private fun loadData() = with(binding){
        val datos = listOf(
            Data(txtResultado.text.toString(),txtFecha.text.toString())
        )
        adapter.updateListData(datos)

    }

    //funcion para iniciar el scanner
    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Elaborado por Medlog")
        integrator.setCameraId(0)
        //integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    //obtener la fecha y la hora
    private fun getFechaHora() {
        val fecha_hora = SimpleDateFormat("dd MMM yyyy - HH:mm:ss", Locale.forLanguageTag("es_ES"))
        binding.txtFecha.text = fecha_hora.format(Date())
    }

    private fun getData(){
        val listData = sqliteHelper.getAllData()
        adapter.updateListData(listData)
    }
    private fun agregarData() = with(binding){
        val resultado = txtResultado.text.toString()
        val fecha = txtFecha.text.toString()
        val d = Data(nombre = resultado, fecha = fecha)
        val status = sqliteHelper.insertData(d)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                binding.txtResultado.text = result.contents

                    getFechaHora()
                    //Configurar el adaptador
                    setupAdapter()
                    //Cargar la Data
                    loadData()
                    // obtenerData
                    getData()
                    //agregar
                    agregarData()

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}