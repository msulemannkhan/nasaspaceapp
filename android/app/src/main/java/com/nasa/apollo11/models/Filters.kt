package com.nasa.apollo11.models

class Filters : ArrayList<FiltersItem>()

data class FiltersItem(
    val display_image: String,
    val name: String
)