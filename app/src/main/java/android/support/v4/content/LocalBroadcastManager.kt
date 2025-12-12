package android.support.v4.content

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.support.annotation.NonNull
import android.support.v4.provider.FontsContractCompat
import android.util.Log
import java.util.ArrayList
import java.util.HashMap
import java.util.Set

class LocalBroadcastManager {
    private static val DEBUG = false
    static val MSG_EXEC_PENDING_BROADCASTS = 1
    private static val TAG = "LocalBroadcastManager"
    private static LocalBroadcastManager mInstance
    private static val mLock = Object()
    private final Context mAppContext
    private final Handler mHandler
    private val mReceivers = HashMap()
    private val mActions = HashMap()
    private val mPendingBroadcasts = ArrayList()

    final class BroadcastRecord {
        final Intent intent
        final ArrayList receivers

        BroadcastRecord(Intent intent, ArrayList arrayList) {
            this.intent = intent
            this.receivers = arrayList
        }
    }

    final class ReceiverRecord {
        Boolean broadcasting
        Boolean dead
        final IntentFilter filter
        final BroadcastReceiver receiver

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter
            this.receiver = broadcastReceiver
        }

        public final String toString() {
            StringBuilder sb = StringBuilder(128)
            sb.append("Receiver{")
            sb.append(this.receiver)
            sb.append(" filter=")
            sb.append(this.filter)
            if (this.dead) {
                sb.append(" DEAD")
            }
            sb.append("}")
            return sb.toString()
        }
    }

    private constructor(Context context) {
        this.mAppContext = context
        this.mHandler = Handler(context.getMainLooper()) { // from class: android.support.v4.content.LocalBroadcastManager.1
            @Override // android.os.Handler
            fun handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        LocalBroadcastManager.this.executePendingBroadcasts()
                        break
                    default:
                        super.handleMessage(message)
                        break
                }
            }
        }
    }

    @NonNull
    fun getInstance(@NonNull Context context) {
        LocalBroadcastManager localBroadcastManager
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = LocalBroadcastManager(context.getApplicationContext())
            }
            localBroadcastManager = mInstance
        }
        return localBroadcastManager
    }

    final Unit executePendingBroadcasts() {
        Array<BroadcastRecord> broadcastRecordArr
        while (true) {
            synchronized (this.mReceivers) {
                Int size = this.mPendingBroadcasts.size()
                if (size <= 0) {
                    return
                }
                broadcastRecordArr = new BroadcastRecord[size]
                this.mPendingBroadcasts.toArray(broadcastRecordArr)
                this.mPendingBroadcasts.clear()
            }
            for (BroadcastRecord broadcastRecord : broadcastRecordArr) {
                Int size2 = broadcastRecord.receivers.size()
                for (Int i = 0; i < size2; i++) {
                    ReceiverRecord receiverRecord = (ReceiverRecord) broadcastRecord.receivers.get(i)
                    if (!receiverRecord.dead) {
                        receiverRecord.receiver.onReceive(this.mAppContext, broadcastRecord.intent)
                    }
                }
            }
        }
    }

    public final Unit registerReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = ReceiverRecord(intentFilter, broadcastReceiver)
            ArrayList arrayList = (ArrayList) this.mReceivers.get(broadcastReceiver)
            if (arrayList == null) {
                arrayList = ArrayList(1)
                this.mReceivers.put(broadcastReceiver, arrayList)
            }
            arrayList.add(receiverRecord)
            for (Int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i)
                ArrayList arrayList2 = (ArrayList) this.mActions.get(action)
                if (arrayList2 == null) {
                    arrayList2 = ArrayList(1)
                    this.mActions.put(action, arrayList2)
                }
                arrayList2.add(receiverRecord)
            }
        }
    }

    public final Boolean sendBroadcast(@NonNull Intent intent) {
        String str
        ArrayList arrayList
        synchronized (this.mReceivers) {
            String action = intent.getAction()
            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver())
            Uri data = intent.getData()
            String scheme = intent.getScheme()
            Set<String> categories = intent.getCategories()
            Boolean z = (intent.getFlags() & 8) != 0
            if (z) {
                Log.v(TAG, "Resolving type " + strResolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent)
            }
            ArrayList arrayList2 = (ArrayList) this.mActions.get(intent.getAction())
            if (arrayList2 != null) {
                if (z) {
                    Log.v(TAG, "Action list: " + arrayList2)
                }
                ArrayList arrayList3 = null
                Int i = 0
                while (i < arrayList2.size()) {
                    ReceiverRecord receiverRecord = (ReceiverRecord) arrayList2.get(i)
                    if (z) {
                        Log.v(TAG, "Matching against filter " + receiverRecord.filter)
                    }
                    if (!receiverRecord.broadcasting) {
                        Int iMatch = receiverRecord.filter.match(action, strResolveTypeIfNeeded, scheme, data, categories, TAG)
                        if (iMatch >= 0) {
                            if (z) {
                                Log.v(TAG, "  Filter matched!  match=0x" + Integer.toHexString(iMatch))
                            }
                            arrayList = arrayList3 == null ? ArrayList() : arrayList3
                            arrayList.add(receiverRecord)
                            receiverRecord.broadcasting = true
                        } else {
                            if (z) {
                                switch (iMatch) {
                                    case FontsContractCompat.FontRequestCallback.FAIL_REASON_SECURITY_VIOLATION /* -4 */:
                                        str = "category"
                                        break
                                    case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR /* -3 */:
                                        str = "action"
                                        break
                                    case -2:
                                        str = "data"
                                        break
                                    case -1:
                                        str = "type"
                                        break
                                    default:
                                        str = "unknown reason"
                                        break
                                }
                                Log.v(TAG, "  Filter did not match: " + str)
                            }
                            arrayList = arrayList3
                        }
                    } else if (z) {
                        Log.v(TAG, "  Filter's target already added")
                        arrayList = arrayList3
                    } else {
                        arrayList = arrayList3
                    }
                    i++
                    arrayList3 = arrayList
                }
                if (arrayList3 != null) {
                    for (Int i2 = 0; i2 < arrayList3.size(); i2++) {
                        ((ReceiverRecord) arrayList3.get(i2)).broadcasting = false
                    }
                    this.mPendingBroadcasts.add(BroadcastRecord(intent, arrayList3))
                    if (!this.mHandler.hasMessages(1)) {
                        this.mHandler.sendEmptyMessage(1)
                    }
                    return true
                }
            }
            return false
        }
    }

    public final Unit sendBroadcastSync(@NonNull Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts()
        }
    }

    public final Unit unregisterReceiver(@NonNull BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList arrayList = (ArrayList) this.mReceivers.remove(broadcastReceiver)
            if (arrayList == null) {
                return
            }
            for (Int size = arrayList.size() - 1; size >= 0; size--) {
                ReceiverRecord receiverRecord = (ReceiverRecord) arrayList.get(size)
                receiverRecord.dead = true
                for (Int i = 0; i < receiverRecord.filter.countActions(); i++) {
                    String action = receiverRecord.filter.getAction(i)
                    ArrayList arrayList2 = (ArrayList) this.mActions.get(action)
                    if (arrayList2 != null) {
                        for (Int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                            ReceiverRecord receiverRecord2 = (ReceiverRecord) arrayList2.get(size2)
                            if (receiverRecord2.receiver == broadcastReceiver) {
                                receiverRecord2.dead = true
                                arrayList2.remove(size2)
                            }
                        }
                        if (arrayList2.size() <= 0) {
                            this.mActions.remove(action)
                        }
                    }
                }
            }
        }
    }
}
