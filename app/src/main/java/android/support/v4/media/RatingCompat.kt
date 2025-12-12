package android.support.v4.media

import android.media.Rating
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.RestrictTo
import android.util.Log
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class RatingCompat implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.RatingCompat.1
        @Override // android.os.Parcelable.Creator
        public final RatingCompat createFromParcel(Parcel parcel) {
            return RatingCompat(parcel.readInt(), parcel.readFloat())
        }

        @Override // android.os.Parcelable.Creator
        public final Array<RatingCompat> newArray(Int i) {
            return new RatingCompat[i]
        }
    }
    public static val RATING_3_STARS = 3
    public static val RATING_4_STARS = 4
    public static val RATING_5_STARS = 5
    public static val RATING_HEART = 1
    public static val RATING_NONE = 0
    private static val RATING_NOT_RATED = -1.0f
    public static val RATING_PERCENTAGE = 6
    public static val RATING_THUMB_UP_DOWN = 2
    private static val TAG = "Rating"
    private Object mRatingObj
    private final Int mRatingStyle
    private final Float mRatingValue

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Style {
    }

    RatingCompat(Int i, Float f) {
        this.mRatingStyle = i
        this.mRatingValue = f
    }

    fun fromRating(Object obj) {
        RatingCompat ratingCompatNewUnratedRating
        if (obj == null || Build.VERSION.SDK_INT < 19) {
            return null
        }
        Int ratingStyle = ((Rating) obj).getRatingStyle()
        if (((Rating) obj).isRated()) {
            switch (ratingStyle) {
                case 1:
                    ratingCompatNewUnratedRating = newHeartRating(((Rating) obj).hasHeart())
                    break
                case 2:
                    ratingCompatNewUnratedRating = newThumbRating(((Rating) obj).isThumbUp())
                    break
                case 3:
                case 4:
                case 5:
                    ratingCompatNewUnratedRating = newStarRating(ratingStyle, ((Rating) obj).getStarRating())
                    break
                case 6:
                    ratingCompatNewUnratedRating = newPercentageRating(((Rating) obj).getPercentRating())
                    break
                default:
                    return null
            }
        } else {
            ratingCompatNewUnratedRating = newUnratedRating(ratingStyle)
        }
        ratingCompatNewUnratedRating.mRatingObj = obj
        return ratingCompatNewUnratedRating
    }

    fun newHeartRating(Boolean z) {
        return RatingCompat(1, z ? 1.0f : 0.0f)
    }

    fun newPercentageRating(Float f) {
        if (f >= 0.0f && f <= 100.0f) {
            return RatingCompat(6, f)
        }
        Log.e(TAG, "Invalid percentage-based rating value")
        return null
    }

    fun newStarRating(Int i, Float f) {
        Float f2
        switch (i) {
            case 3:
                f2 = 3.0f
                break
            case 4:
                f2 = 4.0f
                break
            case 5:
                f2 = 5.0f
                break
            default:
                Log.e(TAG, "Invalid rating style (" + i + ") for a star rating")
                return null
        }
        if (f >= 0.0f && f <= f2) {
            return RatingCompat(i, f)
        }
        Log.e(TAG, "Trying to set out of range star-based rating")
        return null
    }

    fun newThumbRating(Boolean z) {
        return RatingCompat(2, z ? 1.0f : 0.0f)
    }

    fun newUnratedRating(Int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return RatingCompat(i, RATING_NOT_RATED)
            default:
                return null
        }
    }

    @Override // android.os.Parcelable
    public final Int describeContents() {
        return this.mRatingStyle
    }

    public final Float getPercentRating() {
        return (this.mRatingStyle == 6 && isRated()) ? this.mRatingValue : RATING_NOT_RATED
    }

    public final Object getRating() {
        if (this.mRatingObj == null && Build.VERSION.SDK_INT >= 19) {
            if (isRated()) {
                switch (this.mRatingStyle) {
                    case 1:
                        this.mRatingObj = Rating.newHeartRating(hasHeart())
                        break
                    case 2:
                        this.mRatingObj = Rating.newThumbRating(isThumbUp())
                        break
                    case 3:
                    case 4:
                    case 5:
                        this.mRatingObj = Rating.newStarRating(this.mRatingStyle, getStarRating())
                        break
                    case 6:
                        this.mRatingObj = Rating.newPercentageRating(getPercentRating())
                        break
                    default:
                        return null
                }
            } else {
                this.mRatingObj = Rating.newUnratedRating(this.mRatingStyle)
            }
        }
        return this.mRatingObj
    }

    public final Int getRatingStyle() {
        return this.mRatingStyle
    }

    public final Float getStarRating() {
        switch (this.mRatingStyle) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.mRatingValue
                }
            default:
                return RATING_NOT_RATED
        }
    }

    public final Boolean hasHeart() {
        return this.mRatingStyle == 1 && this.mRatingValue == 1.0f
    }

    public final Boolean isRated() {
        return this.mRatingValue >= 0.0f
    }

    public final Boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f
    }

    public final String toString() {
        return "Rating:style=" + this.mRatingStyle + " rating=" + (this.mRatingValue < 0.0f ? "unrated" : String.valueOf(this.mRatingValue))
    }

    @Override // android.os.Parcelable
    public final Unit writeToParcel(Parcel parcel, Int i) {
        parcel.writeInt(this.mRatingStyle)
        parcel.writeFloat(this.mRatingValue)
    }
}
