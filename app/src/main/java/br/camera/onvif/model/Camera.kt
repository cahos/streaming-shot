package br.camera.onvif.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
Criado em 29/11/2020
Evalton Gomes
* */

@Parcelize
data class  Camera(
    val url: String = " ",
    val ip: String = " ",
    val chave: String = " ",
    val login: String = " "
):Parcelable