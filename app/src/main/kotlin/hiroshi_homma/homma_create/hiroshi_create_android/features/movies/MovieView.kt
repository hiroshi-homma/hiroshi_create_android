package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import android.os.Parcel
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.KParcelable
import hiroshi_homma.homma_create.hiroshi_create_android.core.platform.parcelableCreator

data class MovieView(val id: Int, val poster: String) :
        KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::MovieView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(poster)
        }
    }
}
