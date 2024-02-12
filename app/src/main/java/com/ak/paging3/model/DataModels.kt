package com.ak.paging3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Ajithkumar Vadamalai on 14/12/23.
 */

@Parcelize
data class UserListResponse(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
):Parcelable

@Parcelize
data class User(
    val address: Address,
    val age: Int,
    val bank: Bank,
    val birthDate: String,
    val bloodGroup: String,
    val company: Company,
    val domain: String,
    val ein: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val hair: Hair,
    val height: Int,
    val id: Int,
    val image: String,
    val ip: String,
    val lastName: String,
    val macAddress: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val ssn: String,
    val university: String,
    val userAgent: String,
    val username: String,
    val weight: Double
):Parcelable{
    val fullName:String
        get() = "$firstName $lastName $maidenName"
    val dob:String
        get() = "$age, $birthDate"
    val fullHomeAddress:String
        get() = "${address.address}, ${address.city}, ${address.state}, ${address.postalCode}."
}

@Parcelize
data class Address(
    val address: String,
    val city: String,
    val coordinates: Coordinates,
    val postalCode: String,
    val state: String
):Parcelable

@Parcelize
data class Bank(
    val cardExpire: String,
    val cardNumber: String,
    val cardType: String,
    val currency: String,
    val iban: String
):Parcelable

@Parcelize
data class Company(
    val address: Address,
    val department: String,
    val name: String,
    val title: String
):Parcelable{
    val fullCompanyAddress:String
        get() = "${address.address}, ${address.city}, ${address.state}, ${address.postalCode}."
}

@Parcelize
data class Hair(
    val color: String,
    val type: String
):Parcelable

@Parcelize
data class Coordinates(
    val lat: Double,
    val lng: Double
):Parcelable


