package com.vandoc.iptv.util.mapper

import com.vandoc.iptv.data.model.local.*
import com.vandoc.iptv.data.model.response.*

/**
 * @author Ichvandi
 * Created on 27/08/2022 at 12:06.
 */
fun CategoryResponse.toLocalModel(): Category = Category(
    id,
    name
)

fun CountryResponse.toLocalModel(): Country = Country(
    id,
    idLanguage,
    idRegion,
    name,
    flag
)

fun LanguageResponse.toLocalModel(): Language = Language(
    id,
    name
)

fun RegionResponse.toLocalModel(): Region = Region(
    id,
    name
)

fun SubdivisionResponse.toLocalModel(): Subdivision = Subdivision(
    id,
    idCountry,
    name
)
