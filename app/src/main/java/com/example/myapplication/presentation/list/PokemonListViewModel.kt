package com.example.myapplication.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.PokeRepository
import com.example.myapplication.presentation.Singletons
import com.example.myapplication.presentation.api.PokemonListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel() : ViewModel() {

    val pokeList : MutableLiveData<PokemonModel> = MutableLiveData()

    init {
        callApi()
    }

    private fun callApi() {
        pokeList.value= PokemonLoader
        Singletons.pokeApi.getPokemonList().enqueue(object : Callback<PokemonListResponse> {
            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                pokeList.value = PokemonError
            }

            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val pokemonResponse: PokemonListResponse = response.body()!!
                    pokeList.value = PokemonSuccess(pokemonResponse.results)
                } else {
                    pokeList.value = PokemonError
                }

                }
        })
    }


}