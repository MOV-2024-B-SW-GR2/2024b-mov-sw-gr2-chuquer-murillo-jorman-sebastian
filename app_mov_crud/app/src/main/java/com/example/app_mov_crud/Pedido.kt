package com.example.app_mov_crud

import android.os.Parcel
import android.os.Parcelable

class Pedido (
    var id: Int,
    var descripcion: String,
    var monto: Double,
    var cantidad: Int,
    var cliente_id: Int
):Parcelable{
    constructor(parcel: Parcel): this(
        parcel.readInt(),
        if(parcel.readString()==null) "" else parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    )
    constructor(descripcion: String, monto: Double, cantidad: Int, cliente_id: Int): this(0, descripcion, monto, cantidad, cliente_id)



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(descripcion)
        parcel.writeDouble(monto)
        parcel.writeInt(cantidad)
        parcel.writeInt(cliente_id)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR: Parcelable.Creator<Pedido>{
        override fun createFromParcel(parcel: Parcel): Pedido {
            return Pedido(parcel)
        }

        override fun newArray(size: Int): Array<Pedido?> {
            return arrayOfNulls(size)
        }
    }

}