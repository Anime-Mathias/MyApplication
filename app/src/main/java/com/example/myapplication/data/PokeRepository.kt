package com.example.myapplication.data

import com.example.myapplication.presentation.list.Pokemon

interface PokeRepository {

    fun getPokeList() : List<Pokemon>
}