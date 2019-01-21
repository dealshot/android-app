package com.dealshot.dealshotandroidapp

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class User(
    var nickName:String,
    val userId: String,
    var password: String,
    var loginStatus: String,
    val registerDate: String,
    val posts: Array<Post>,
    val takes: Array<Post>,
    var rating: Float
    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArray(Post),
        parcel.createTypedArray(Post),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nickName)
        parcel.writeString(userId)
        parcel.writeString(password)
        parcel.writeString(loginStatus)
        parcel.writeString(registerDate)
        parcel.writeTypedArray(posts, flags)
        parcel.writeTypedArray(takes, flags)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
