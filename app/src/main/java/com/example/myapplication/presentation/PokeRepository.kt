package com.example.myapplication.presentation

interface PokeRepository {

    fun getPokeList() : List<Pokemon>
}