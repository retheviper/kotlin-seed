package com.retheviper.kotlin.seed.dsl

import com.retheviper.kotlin.seed.context.SeedParserContext
import com.retheviper.kotlin.seed.plant.Planter

inline fun <reified T> seed(init: SeedParserContext.() -> Unit = {}): Planter<T> {
    val context = SeedParserContext().apply(init)
    return Planter(context)
}