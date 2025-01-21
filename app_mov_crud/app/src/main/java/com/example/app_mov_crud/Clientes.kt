package com.example.app_mov_crud

import android.os.Parcel
import android.os.Parcelable


class Clientes(
    var id: Int,
    var nombre: String,
    var email: String?,
    var telefono: String?,
    var fecha_registro: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        if(parcel.readString()== null) "" else parcel.readString()!!,
        parcel.readString() ,
        parcel.readString(),
        parcel.readString()
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeString(fecha_registro)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clientes> {
        override fun createFromParcel(parcel: Parcel): Clientes {
            return Clientes(parcel)
        }

        override fun newArray(size: Int): Array<Clientes?> {
            return arrayOfNulls(size)
        }
    }
}