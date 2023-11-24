package com.example.lesson5igor

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class CustomViewState: View.BaseSavedState {
    var columnHeights: FloatArray? = null
    var dateLabels: Array<String>? = null

    constructor(superState: Parcelable?) : super(superState)

    constructor(parcel: Parcel) : super(parcel) {
        columnHeights = parcel.createFloatArray()
        dateLabels = parcel.createStringArray()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeFloatArray(columnHeights)
        out.writeStringArray(dateLabels)
    }

    companion object CREATOR : Parcelable.Creator<CustomViewState> {
        override fun createFromParcel(parcel: Parcel): CustomViewState {
            return CustomViewState(parcel)
        }

        override fun newArray(size: Int): Array<CustomViewState?> {
            return arrayOfNulls(size)
        }
    }
}