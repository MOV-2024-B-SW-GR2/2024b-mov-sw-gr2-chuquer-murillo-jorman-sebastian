package com.example.app_mov_crud

import android.os.Parcel
import android.os.Parcelable

class Cliente(
    var id: Int = 0,
    var nombre: String,
    var email: String?,
    var telefono: String?,
    var fecha_registro: String?,
    var latitud: Double,
    var longitud: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )
    constructor(nombre: String, email: String?, telefono: String?, fecha_registro: String?, latitud: Double, longitud: Double) : this(
        0,
        nombre,
        email,
        telefono,
        fecha_registro,
        latitud,
        longitud
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeString(fecha_registro)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }
}