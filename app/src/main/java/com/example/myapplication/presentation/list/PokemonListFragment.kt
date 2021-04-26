package com.example.myapplication.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.api.PokeApi
import com.example.myapplication.presentation.api.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PokemonListFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView

    private val adapter = PokemonAdapter(listOf(), ::onClickedPokemon)

    private val layoutManager= LinearLayoutManager(context)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.pokemon_recyclerview)

        recyclerview.apply {
            layoutManager = this@PokemonListFragment.layoutManager
            adapter = this@PokemonListFragment.adapter
        }


        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val pokeApi: PokeApi = retrofit.create(PokeApi::class.java)

        pokeApi.getPokemonList().enqueue(object: Callback<PokemonResponse>{
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                //TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    val pokemonResponse : PokemonResponse = response.body()!!
                    adapter.updateList(pokemonResponse.results)
                }
            }
         })
    }
    private fun onClickedPokemon(pokemon : Pokemon) {
       findNavController().navigate(R.id.navigateToPokemonDetailFragment)

    }

}