package android.support.v7.widget

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.database.DataSetObservable
import android.os.AsyncTask
import android.text.TextUtils
import android.util.Log
import android.util.Xml
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlSerializer

class ActivityChooserModel extends DataSetObservable {
    static val ATTRIBUTE_ACTIVITY = "activity"
    static val ATTRIBUTE_TIME = "time"
    static val ATTRIBUTE_WEIGHT = "weight"
    static val DEBUG = false
    private static val DEFAULT_ACTIVITY_INFLATION = 5
    private static val DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f
    public static val DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml"
    public static val DEFAULT_HISTORY_MAX_LENGTH = 50
    private static val HISTORY_FILE_EXTENSION = ".xml"
    private static val INVALID_INDEX = -1
    static val TAG_HISTORICAL_RECORD = "historical-record"
    static val TAG_HISTORICAL_RECORDS = "historical-records"
    private OnChooseActivityListener mActivityChoserModelPolicy
    final Context mContext
    final String mHistoryFileName
    private Intent mIntent
    static val LOG_TAG = ActivityChooserModel.class.getSimpleName()
    private static val sRegistryLock = Object()
    private static val sDataModelRegistry = HashMap()
    private val mInstanceLock = Object()
    private val mActivities = ArrayList()
    private val mHistoricalRecords = ArrayList()
    private ActivitySorter mActivitySorter = DefaultSorter()
    private Int mHistoryMaxSize = 50
    Boolean mCanReadHistoricalData = true
    private Boolean mReadShareHistoryCalled = false
    private Boolean mHistoricalRecordsChanged = true
    private Boolean mReloadActivities = false

    public interface ActivityChooserModelClient {
        Unit setActivityChooserModel(ActivityChooserModel activityChooserModel)
    }

    class ActivityResolveInfo implements Comparable {
        public final ResolveInfo resolveInfo
        public Float weight

        constructor(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo
        }

        @Override // java.lang.Comparable
        public final Int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight)
        }

        public final Boolean equals(Object obj) {
            if (this == obj) {
                return true
            }
            return obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight)
        }

        public final Int hashCode() {
            return Float.floatToIntBits(this.weight) + 31
        }

        public final String toString() {
            StringBuilder sb = StringBuilder()
            sb.append("[")
            sb.append("resolveInfo:").append(this.resolveInfo.toString())
            sb.append("; weight:").append(BigDecimal(this.weight))
            sb.append("]")
            return sb.toString()
        }
    }

    public interface ActivitySorter {
        Unit sort(Intent intent, List list, List list2)
    }

    final class DefaultSorter implements ActivitySorter {
        private static val WEIGHT_DECAY_COEFFICIENT = 0.95f
        private val mPackageNameToActivityMap = HashMap()

        DefaultSorter() {
        }

        @Override // android.support.v7.widget.ActivityChooserModel.ActivitySorter
        public final Unit sort(Intent intent, List list, List list2) {
            Float f
            Map map = this.mPackageNameToActivityMap
            map.clear()
            Int size = list.size()
            for (Int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) list.get(i)
                activityResolveInfo.weight = 0.0f
                map.put(ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo)
            }
            Int size2 = list2.size() - 1
            Float f2 = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT
            Int i2 = size2
            while (i2 >= 0) {
                HistoricalRecord historicalRecord = (HistoricalRecord) list2.get(i2)
                ActivityResolveInfo activityResolveInfo2 = (ActivityResolveInfo) map.get(historicalRecord.activity)
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight = (historicalRecord.weight * f2) + activityResolveInfo2.weight
                    f = WEIGHT_DECAY_COEFFICIENT * f2
                } else {
                    f = f2
                }
                i2--
                f2 = f
            }
            Collections.sort(list)
        }
    }

    class HistoricalRecord {
        public final ComponentName activity
        public final Long time
        public final Float weight

        constructor(ComponentName componentName, Long j, Float f) {
            this.activity = componentName
            this.time = j
            this.weight = f
        }

        constructor(String str, Long j, Float f) {
            this(ComponentName.unflattenFromString(str), j, f)
        }

        public final Boolean equals(Object obj) {
            if (this == obj) {
                return true
            }
            if (obj != null && getClass() == obj.getClass()) {
                HistoricalRecord historicalRecord = (HistoricalRecord) obj
                if (this.activity == null) {
                    if (historicalRecord.activity != null) {
                        return false
                    }
                } else if (!this.activity.equals(historicalRecord.activity)) {
                    return false
                }
                return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight)
            }
            return false
        }

        public final Int hashCode() {
            return (((((this.activity == null ? 0 : this.activity.hashCode()) + 31) * 31) + ((Int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight)
        }

        public final String toString() {
            StringBuilder sb = StringBuilder()
            sb.append("[")
            sb.append("; activity:").append(this.activity)
            sb.append("; time:").append(this.time)
            sb.append("; weight:").append(BigDecimal(this.weight))
            sb.append("]")
            return sb.toString()
        }
    }

    public interface OnChooseActivityListener {
        Boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent)
    }

    final class PersistHistoryAsyncTask extends AsyncTask {
        PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public final Void doInBackground(Object... objArr) throws IOException {
            FileOutputStream fileOutputStreamOpenFileOutput
            XmlSerializer xmlSerializerNewSerializer
            List list = (List) objArr[0]
            String str = (String) objArr[1]
            try {
                fileOutputStreamOpenFileOutput = ActivityChooserModel.this.mContext.openFileOutput(str, 0)
                xmlSerializerNewSerializer = Xml.newSerializer()
            } catch (FileNotFoundException e) {
                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + str, e)
            }
            try {
                try {
                    try {
                        try {
                            xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null)
                            xmlSerializerNewSerializer.startDocument("UTF-8", true)
                            xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS)
                            Int size = list.size()
                            for (Int i = 0; i < size; i++) {
                                HistoricalRecord historicalRecord = (HistoricalRecord) list.remove(0)
                                xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD)
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_ACTIVITY, historicalRecord.activity.flattenToString())
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_TIME, String.valueOf(historicalRecord.time))
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(historicalRecord.weight))
                                xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD)
                            }
                            xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS)
                            xmlSerializerNewSerializer.endDocument()
                            ActivityChooserModel.this.mCanReadHistoricalData = true
                            if (fileOutputStreamOpenFileOutput != null) {
                                try {
                                    fileOutputStreamOpenFileOutput.close()
                                } catch (IOException e2) {
                                }
                            }
                        } catch (IllegalStateException e3) {
                            Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e3)
                            ActivityChooserModel.this.mCanReadHistoricalData = true
                            if (fileOutputStreamOpenFileOutput != null) {
                                try {
                                    fileOutputStreamOpenFileOutput.close()
                                } catch (IOException e4) {
                                }
                            }
                        }
                    } catch (IllegalArgumentException e5) {
                        Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e5)
                        ActivityChooserModel.this.mCanReadHistoricalData = true
                        if (fileOutputStreamOpenFileOutput != null) {
                            try {
                                fileOutputStreamOpenFileOutput.close()
                            } catch (IOException e6) {
                            }
                        }
                    }
                } catch (IOException e7) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e7)
                    ActivityChooserModel.this.mCanReadHistoricalData = true
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close()
                        } catch (IOException e8) {
                        }
                    }
                }
                return null
            } catch (Throwable th) {
                ActivityChooserModel.this.mCanReadHistoricalData = true
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close()
                    } catch (IOException e9) {
                    }
                }
                throw th
            }
        }
    }

    private constructor(Context context, String str) {
        this.mContext = context.getApplicationContext()
        if (TextUtils.isEmpty(str) || str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = str
        } else {
            this.mHistoryFileName = str + HISTORY_FILE_EXTENSION
        }
    }

    private fun addHistoricalRecord(HistoricalRecord historicalRecord) {
        Boolean zAdd = this.mHistoricalRecords.add(historicalRecord)
        if (zAdd) {
            this.mHistoricalRecordsChanged = true
            pruneExcessiveHistoricalRecordsIfNeeded()
            persistHistoricalDataIfNeeded()
            sortActivitiesIfNeeded()
            notifyChanged()
        }
        return zAdd
    }

    private fun ensureConsistentState() {
        Boolean zLoadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded()
        pruneExcessiveHistoricalRecordsIfNeeded()
        if (zLoadActivitiesIfNeeded) {
            sortActivitiesIfNeeded()
            notifyChanged()
        }
    }

    fun get(Context context, String str) {
        ActivityChooserModel activityChooserModel
        synchronized (sRegistryLock) {
            activityChooserModel = (ActivityChooserModel) sDataModelRegistry.get(str)
            if (activityChooserModel == null) {
                activityChooserModel = ActivityChooserModel(context, str)
                sDataModelRegistry.put(str, activityChooserModel)
            }
        }
        return activityChooserModel
    }

    private fun loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false
        }
        this.mReloadActivities = false
        this.mActivities.clear()
        List<ResolveInfo> listQueryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0)
        Int size = listQueryIntentActivities.size()
        for (Int i = 0; i < size; i++) {
            this.mActivities.add(ActivityResolveInfo(listQueryIntentActivities.get(i)))
        }
        return true
    }

    private fun persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw IllegalStateException("No preceding call to #readHistoricalData")
        }
        if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false
            if (TextUtils.isEmpty(this.mHistoryFileName)) {
                return
            }
            PersistHistoryAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ArrayList(this.mHistoricalRecords), this.mHistoryFileName)
        }
    }

    private fun pruneExcessiveHistoricalRecordsIfNeeded() {
        Int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize
        if (size <= 0) {
            return
        }
        this.mHistoricalRecordsChanged = true
        for (Int i = 0; i < size; i++) {
            this.mHistoricalRecords.remove(0)
        }
    }

    private fun readHistoricalDataIfNeeded() throws IOException {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false
        }
        this.mCanReadHistoricalData = false
        this.mReadShareHistoryCalled = true
        readHistoricalDataImpl()
        return true
    }

    private fun readHistoricalDataImpl() throws IOException {
        try {
            FileInputStream fileInputStreamOpenFileInput = this.mContext.openFileInput(this.mHistoryFileName)
            try {
                try {
                    XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser()
                    xmlPullParserNewPullParser.setInput(fileInputStreamOpenFileInput, "UTF-8")
                    for (Int next = 0; next != 1 && next != 2; next = xmlPullParserNewPullParser.next()) {
                    }
                    if (!TAG_HISTORICAL_RECORDS.equals(xmlPullParserNewPullParser.getName())) {
                        throw XmlPullParserException("Share records file does not start with historical-records tag.")
                    }
                    List list = this.mHistoricalRecords
                    list.clear()
                    while (true) {
                        Int next2 = xmlPullParserNewPullParser.next()
                        if (next2 == 1) {
                            if (fileInputStreamOpenFileInput != null) {
                                try {
                                    fileInputStreamOpenFileInput.close()
                                    return
                                } catch (IOException e) {
                                    return
                                }
                            }
                            return
                        }
                        if (next2 != 3 && next2 != 4) {
                            if (!TAG_HISTORICAL_RECORD.equals(xmlPullParserNewPullParser.getName())) {
                                throw XmlPullParserException("Share records file not well-formed.")
                            }
                            list.add(HistoricalRecord(xmlPullParserNewPullParser.getAttributeValue(null, ATTRIBUTE_ACTIVITY), Long.parseLong(xmlPullParserNewPullParser.getAttributeValue(null, ATTRIBUTE_TIME)), Float.parseFloat(xmlPullParserNewPullParser.getAttributeValue(null, ATTRIBUTE_WEIGHT))))
                        }
                    }
                } catch (IOException e2) {
                    Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e2)
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close()
                        } catch (IOException e3) {
                        }
                    }
                } catch (XmlPullParserException e4) {
                    Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, e4)
                    if (fileInputStreamOpenFileInput != null) {
                        try {
                            fileInputStreamOpenFileInput.close()
                        } catch (IOException e5) {
                        }
                    }
                }
            } catch (Throwable th) {
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close()
                    } catch (IOException e6) {
                    }
                }
                throw th
            }
        } catch (FileNotFoundException e7) {
        }
    }

    private fun sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords))
        return true
    }

    fun chooseActivity(Int i) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null
            }
            ensureConsistentState()
            ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) this.mActivities.get(i)
            ComponentName componentName = ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name)
            Intent intent = Intent(this.mIntent)
            intent.setComponent(componentName)
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, Intent(intent))) {
                    return null
                }
            }
            addHistoricalRecord(HistoricalRecord(componentName, System.currentTimeMillis(), DEFAULT_HISTORICAL_RECORD_WEIGHT))
            return intent
        }
    }

    fun getActivity(Int i) {
        ResolveInfo resolveInfo
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            resolveInfo = ((ActivityResolveInfo) this.mActivities.get(i)).resolveInfo
        }
        return resolveInfo
    }

    fun getActivityCount() {
        Int size
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            size = this.mActivities.size()
        }
        return size
    }

    fun getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            List list = this.mActivities
            Int size = list.size()
            for (Int i = 0; i < size; i++) {
                if (((ActivityResolveInfo) list.get(i)).resolveInfo == resolveInfo) {
                    return i
                }
            }
            return -1
        }
    }

    fun getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            if (this.mActivities.isEmpty()) {
                return null
            }
            return ((ActivityResolveInfo) this.mActivities.get(0)).resolveInfo
        }
    }

    fun getHistoryMaxSize() {
        Int i
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize
        }
        return i
    }

    fun getHistorySize() {
        Int size
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            size = this.mHistoricalRecords.size()
        }
        return size
    }

    fun getIntent() {
        Intent intent
        synchronized (this.mInstanceLock) {
            intent = this.mIntent
        }
        return intent
    }

    fun setActivitySorter(ActivitySorter activitySorter) {
        synchronized (this.mInstanceLock) {
            if (this.mActivitySorter == activitySorter) {
                return
            }
            this.mActivitySorter = activitySorter
            if (sortActivitiesIfNeeded()) {
                notifyChanged()
            }
        }
    }

    fun setDefaultActivity(Int i) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState()
            ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo) this.mActivities.get(i)
            ActivityResolveInfo activityResolveInfo2 = (ActivityResolveInfo) this.mActivities.get(0)
            addHistoricalRecord(HistoricalRecord(ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : DEFAULT_HISTORICAL_RECORD_WEIGHT))
        }
    }

    fun setHistoryMaxSize(Int i) {
        synchronized (this.mInstanceLock) {
            if (this.mHistoryMaxSize == i) {
                return
            }
            this.mHistoryMaxSize = i
            pruneExcessiveHistoricalRecordsIfNeeded()
            if (sortActivitiesIfNeeded()) {
                notifyChanged()
            }
        }
    }

    fun setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == intent) {
                return
            }
            this.mIntent = intent
            this.mReloadActivities = true
            ensureConsistentState()
        }
    }

    fun setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener
        }
    }
}
