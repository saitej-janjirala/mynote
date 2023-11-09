package com.saitejajanjirala.mynote.domain.util

sealed class OrderType{
    object Ascending : OrderType()
    object Descending : OrderType()
}
