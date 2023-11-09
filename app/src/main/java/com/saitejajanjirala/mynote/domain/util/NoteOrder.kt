package com.saitejajanjirala.mynote.domain.util

sealed class NoteOrder(val orderType: OrderType){
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)
    fun copyOrderType(orderType: OrderType) : NoteOrder{
        return when(this){
            is Date -> Date(orderType)
            is Title -> Title(orderType)
        }
    }
}
