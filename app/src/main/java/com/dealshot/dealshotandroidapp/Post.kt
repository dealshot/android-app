package com.dealshot.dealshotandroidapp

import android.os.Parcel
import android.os.Parcelable
import java.sql.Time

data class Post(
    var taskName: String,
    val poster: User?, //consider store userid instead of user objects
    val taker: User?,
    var detail: String,
    var distance: Float,
    var time: String,
    var errandLocation: String,
    var dropoffLocation: String

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable<User>(User.javaClass.classLoader),
        parcel.readParcelable<User>(User.javaClass.classLoader),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskName)
        parcel.writeParcelable(poster,flags)
        parcel.writeParcelable(taker,flags)
        parcel.writeString(detail)
        parcel.writeFloat(distance)
        parcel.writeString(time)
        parcel.writeString(errandLocation)
        parcel.writeString(dropoffLocation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}