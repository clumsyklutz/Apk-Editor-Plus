package android.support.v4.app

import android.app.Activity
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.util.SparseIntArray
import android.view.FrameMetrics
import android.view.Window
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.Iterator

class FrameMetricsAggregator {
    public static val ANIMATION_DURATION = 256
    public static val ANIMATION_INDEX = 8
    public static val COMMAND_DURATION = 32
    public static val COMMAND_INDEX = 5
    private static val DBG = false
    public static val DELAY_DURATION = 128
    public static val DELAY_INDEX = 7
    public static val DRAW_DURATION = 8
    public static val DRAW_INDEX = 3
    public static val EVERY_DURATION = 511
    public static val INPUT_DURATION = 2
    public static val INPUT_INDEX = 1
    private static val LAST_INDEX = 8
    public static val LAYOUT_MEASURE_DURATION = 4
    public static val LAYOUT_MEASURE_INDEX = 2
    public static val SWAP_DURATION = 64
    public static val SWAP_INDEX = 6
    public static val SYNC_DURATION = 16
    public static val SYNC_INDEX = 4
    private static val TAG = "FrameMetrics"
    public static val TOTAL_DURATION = 1
    public static val TOTAL_INDEX = 0
    private FrameMetricsBaseImpl mInstance

    @RequiresApi(24)
    class FrameMetricsApi24Impl extends FrameMetricsBaseImpl {
        private static val NANOS_PER_MS = 1000000
        private static val NANOS_ROUNDING_VALUE = 500000
        Int mTrackingFlags
        private static HandlerThread sHandlerThread = null
        private static Handler sHandler = null
        Array<SparseIntArray> mMetrics = new SparseIntArray[9]
        private ArrayList mActivities = ArrayList()
        Window.OnFrameMetricsAvailableListener mListener = new Window.OnFrameMetricsAvailableListener() { // from class: android.support.v4.app.FrameMetricsAggregator.FrameMetricsApi24Impl.1
            @Override // android.view.Window.OnFrameMetricsAvailableListener
            fun onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, Int i) {
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 1) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[0], frameMetrics.getMetric(8))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 2) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[1], frameMetrics.getMetric(1))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 4) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[2], frameMetrics.getMetric(3))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 8) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[3], frameMetrics.getMetric(4))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 16) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[4], frameMetrics.getMetric(5))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 64) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[6], frameMetrics.getMetric(7))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 32) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[5], frameMetrics.getMetric(6))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 128) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[7], frameMetrics.getMetric(0))
                }
                if ((FrameMetricsApi24Impl.this.mTrackingFlags & 256) != 0) {
                    FrameMetricsApi24Impl.this.addDurationItem(FrameMetricsApi24Impl.this.mMetrics[8], frameMetrics.getMetric(2))
                }
            }
        }

        FrameMetricsApi24Impl(Int i) {
            this.mTrackingFlags = i
        }

        @Override // android.support.v4.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        fun add(Activity activity) {
            if (sHandlerThread == null) {
                HandlerThread handlerThread = HandlerThread("FrameMetricsAggregator")
                sHandlerThread = handlerThread
                handlerThread.start()
                sHandler = Handler(sHandlerThread.getLooper())
            }
            for (Int i = 0; i <= 8; i++) {
                if (this.mMetrics[i] == null && (this.mTrackingFlags & (1 << i)) != 0) {
                    this.mMetrics[i] = SparseIntArray()
                }
            }
            activity.getWindow().addOnFrameMetricsAvailableListener(this.mListener, sHandler)
            this.mActivities.add(WeakReference(activity))
        }

        Unit addDurationItem(SparseIntArray sparseIntArray, Long j) {
            if (sparseIntArray != null) {
                Int i = (Int) ((500000 + j) / 1000000)
                if (j >= 0) {
                    sparseIntArray.put(i, sparseIntArray.get(i) + 1)
                }
            }
        }

        @Override // android.support.v4.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public Array<SparseIntArray> getMetrics() {
            return this.mMetrics
        }

        @Override // android.support.v4.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public Array<SparseIntArray> remove(Activity activity) {
            Iterator it = this.mActivities.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                WeakReference weakReference = (WeakReference) it.next()
                if (weakReference.get() == activity) {
                    this.mActivities.remove(weakReference)
                    break
                }
            }
            activity.getWindow().removeOnFrameMetricsAvailableListener(this.mListener)
            return this.mMetrics
        }

        @Override // android.support.v4.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public Array<SparseIntArray> reset() {
            Array<SparseIntArray> sparseIntArrayArr = this.mMetrics
            this.mMetrics = new SparseIntArray[9]
            return sparseIntArrayArr
        }

        @Override // android.support.v4.app.FrameMetricsAggregator.FrameMetricsBaseImpl
        public Array<SparseIntArray> stop() {
            for (Int size = this.mActivities.size() - 1; size >= 0; size--) {
                WeakReference weakReference = (WeakReference) this.mActivities.get(size)
                Activity activity = (Activity) weakReference.get()
                if (weakReference.get() != null) {
                    activity.getWindow().removeOnFrameMetricsAvailableListener(this.mListener)
                    this.mActivities.remove(size)
                }
            }
            return this.mMetrics
        }
    }

    class FrameMetricsBaseImpl {
        FrameMetricsBaseImpl() {
        }

        fun add(Activity activity) {
        }

        public Array<SparseIntArray> getMetrics() {
            return null
        }

        public Array<SparseIntArray> remove(Activity activity) {
            return null
        }

        public Array<SparseIntArray> reset() {
            return null
        }

        public Array<SparseIntArray> stop() {
            return null
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface MetricType {
    }

    constructor() {
        this(1)
    }

    constructor(Int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.mInstance = FrameMetricsApi24Impl(i)
        } else {
            this.mInstance = FrameMetricsBaseImpl()
        }
    }

    fun add(@NonNull Activity activity) {
        this.mInstance.add(activity)
    }

    @Nullable
    public Array<SparseIntArray> getMetrics() {
        return this.mInstance.getMetrics()
    }

    @Nullable
    public Array<SparseIntArray> remove(@NonNull Activity activity) {
        return this.mInstance.remove(activity)
    }

    @Nullable
    public Array<SparseIntArray> reset() {
        return this.mInstance.reset()
    }

    @Nullable
    public Array<SparseIntArray> stop() {
        return this.mInstance.stop()
    }
}
