package mustafaozhan.github.com.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Int,

    @ColumnInfo(name = "cover_image_url")
    @Json(name = "cover_image_url")
    val coverImageUrl: String,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    val name: String,

    @ColumnInfo(name = "video_url")
    @Json(name = "video_url")
    val videoUrl: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(coverImageUrl)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(videoUrl)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}
