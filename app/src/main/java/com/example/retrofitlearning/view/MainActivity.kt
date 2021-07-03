package com.example.retrofitlearning.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlearning.R
import com.example.retrofitlearning.adapter.RecyclerViewAdapter
import com.example.retrofitlearning.model.CryptoModel
import com.example.retrofitlearning.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null

    //Disposable: Basically the things you will not need at some point and you need them to dispose, I use CompositeDisposable so I can keep all my disposables in the same place
    private var compositeDisposable: CompositeDisposable? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadData()


    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))




        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {

                        cryptoModels = ArrayList(it)

                        for (model:CryptoModel in cryptoModels!!){


                                recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!,this@MainActivity)
                                recyclerView.adapter = recyclerViewAdapter


                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()            }

        }*/

    }

    private fun handleResponse(cryptoList: List<CryptoModel>){

        cryptoModels = ArrayList(cryptoList)

        for (model:CryptoModel in cryptoModels!!){


            recyclerViewAdapter = RecyclerViewAdapter(cryptoModels!!,this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter

    }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"${cryptoModel.currency} = ${cryptoModel.price}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()


        compositeDisposable?.clear()

    }
}