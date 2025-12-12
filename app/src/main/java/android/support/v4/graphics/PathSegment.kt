package android.support.v4.graphics

import android.graphics.PointF
import android.support.annotation.NonNull
import android.support.v4.util.Preconditions

class PathSegment {
    private final PointF mEnd
    private final Float mEndFraction
    private final PointF mStart
    private final Float mStartFraction

    constructor(@NonNull PointF pointF, Float f, @NonNull PointF pointF2, Float f2) {
        this.mStart = (PointF) Preconditions.checkNotNull(pointF, "start == null")
        this.mStartFraction = f
        this.mEnd = (PointF) Preconditions.checkNotNull(pointF2, "end == null")
        this.mEndFraction = f2
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is PathSegment)) {
            return false
        }
        PathSegment pathSegment = (PathSegment) obj
        return Float.compare(this.mStartFraction, pathSegment.mStartFraction) == 0 && Float.compare(this.mEndFraction, pathSegment.mEndFraction) == 0 && this.mStart.equals(pathSegment.mStart) && this.mEnd.equals(pathSegment.mEnd)
    }

    @NonNull
    public final PointF getEnd() {
        return this.mEnd
    }

    public final Float getEndFraction() {
        return this.mEndFraction
    }

    @NonNull
    public final PointF getStart() {
        return this.mStart
    }

    public final Float getStartFraction() {
        return this.mStartFraction
    }

    public final Int hashCode() {
        return (((((this.mStartFraction != 0.0f ? Float.floatToIntBits(this.mStartFraction) : 0) + (this.mStart.hashCode() * 31)) * 31) + this.mEnd.hashCode()) * 31) + (this.mEndFraction != 0.0f ? Float.floatToIntBits(this.mEndFraction) : 0)
    }

    public final String toString() {
        return "PathSegment{start=" + this.mStart + ", startFraction=" + this.mStartFraction + ", end=" + this.mEnd + ", endFraction=" + this.mEndFraction + '}'
    }
}
