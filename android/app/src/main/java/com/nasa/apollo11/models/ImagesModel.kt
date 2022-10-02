package com.nasa.apollo11.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ImagesModel(
    val collection: Collection
)

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)

@Parcelize
data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
) : Parcelable


data class LinkX(
    val href: String,
    val prompt: String,
    val rel: String
)

data class Metadata(
    val total_hits: Int
)

@Parcelize
data class Data(
    val center: String,
    val date_created: String,
    val description: String,
    val description_508: String,
    val keywords: List<String>,
    val media_type: String,
    val nasa_id: String,
    val secondary_creator: String,
    val title: String
) : Parcelable

@Parcelize
data class Link(
    val href: String,
    val rel: String,
    val render: String
) : Parcelable