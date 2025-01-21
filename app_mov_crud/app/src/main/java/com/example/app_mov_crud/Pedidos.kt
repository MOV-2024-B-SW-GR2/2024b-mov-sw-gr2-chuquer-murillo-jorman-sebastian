package com.example.app_mov_crud

import android.os.Parcel
import android.os.Parcelable

class Pedidos (
    var id: Int,
    var descripcion: String,
    var monto: Double,
    var cantidad: Int
):Parcelable{
    constructor(parcel: Parcel): this(
        parcel.readInt(),
        if(parcel.readString()==null) "" else parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(descripcion)
        parcel.writeDouble(monto)
        parcel.writeInt(cantidad)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR: Parcelable.Creator<Pedidos>{
        override fun createFromParcel(parcel: Parcel): Pedidos {
            return Pedidos(parcel)
        }

        override fun newArray(size: Int): Array<Pedidos?> {
            return arrayOfNulls(size)
        }
    }

}