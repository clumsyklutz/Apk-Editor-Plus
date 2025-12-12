package android.support.v4.app

import android.app.Notification
import android.app.PendingIntent
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import android.util.Log
import android.util.SparseArray
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Set

@RequiresApi(16)
class NotificationCompatJellybean {
    static val EXTRA_ALLOW_GENERATED_REPLIES = "android.support.allowGeneratedReplies"
    static val EXTRA_DATA_ONLY_REMOTE_INPUTS = "android.support.dataRemoteInputs"
    private static val KEY_ACTION_INTENT = "actionIntent"
    private static val KEY_ALLOWED_DATA_TYPES = "allowedDataTypes"
    private static val KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput"
    private static val KEY_CHOICES = "choices"
    private static val KEY_DATA_ONLY_REMOTE_INPUTS = "dataOnlyRemoteInputs"
    private static val KEY_EXTRAS = "extras"
    private static val KEY_ICON = "icon"
    private static val KEY_LABEL = "label"
    private static val KEY_REMOTE_INPUTS = "remoteInputs"
    private static val KEY_RESULT_KEY = "resultKey"
    private static val KEY_SEMANTIC_ACTION = "semanticAction"
    private static val KEY_SHOWS_USER_INTERFACE = "showsUserInterface"
    private static val KEY_TITLE = "title"
    public static val TAG = "NotificationCompat"
    private static Class sActionClass
    private static Field sActionIconField
    private static Field sActionIntentField
    private static Field sActionTitleField
    private static Boolean sActionsAccessFailed
    private static Field sActionsField
    private static Field sExtrasField
    private static Boolean sExtrasFieldAccessFailed
    private static val sExtrasLock = Object()
    private static val sActionsLock = Object()

    private constructor() {
    }

    fun buildActionExtrasMap(List list) {
        SparseArray sparseArray = null
        Int size = list.size()
        for (Int i = 0; i < size; i++) {
            Bundle bundle = (Bundle) list.get(i)
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = SparseArray()
                }
                sparseArray.put(i, bundle)
            }
        }
        return sparseArray
    }

    private fun ensureActionReflectionReadyLocked() throws NoSuchFieldException, ClassNotFoundException {
        if (sActionsAccessFailed) {
            return false
        }
        try {
            if (sActionsField == null) {
                Class<?> cls = Class.forName("android.app.Notification$Action")
                sActionClass = cls
                sActionIconField = cls.getDeclaredField(KEY_ICON)
                sActionTitleField = sActionClass.getDeclaredField("title")
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT)
                Field declaredField = Notification.class.getDeclaredField("actions")
                sActionsField = declaredField
                declaredField.setAccessible(true)
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Unable to access notification actions", e)
            sActionsAccessFailed = true
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "Unable to access notification actions", e2)
            sActionsAccessFailed = true
        }
        return !sActionsAccessFailed
    }

    private fun fromBundle(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList(KEY_ALLOWED_DATA_TYPES)
        HashSet hashSet = HashSet()
        if (stringArrayList != null) {
            Iterator<String> it = stringArrayList.iterator()
            while (it.hasNext()) {
                hashSet.add(it.next())
            }
        }
        return RemoteInput(bundle.getString(KEY_RESULT_KEY), bundle.getCharSequence(KEY_LABEL), bundle.getCharSequenceArray(KEY_CHOICES), bundle.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), bundle.getBundle(KEY_EXTRAS), hashSet)
    }

    private static Array<RemoteInput> fromBundleArray(Array<Bundle> bundleArr) {
        if (bundleArr == null) {
            return null
        }
        Array<RemoteInput> remoteInputArr = new RemoteInput[bundleArr.length]
        for (Int i = 0; i < bundleArr.length; i++) {
            remoteInputArr[i] = fromBundle(bundleArr[i])
        }
        return remoteInputArr
    }

    public static NotificationCompat.Action getAction(Notification notification, Int i) {
        SparseArray sparseParcelableArray
        synchronized (sActionsLock) {
            try {
                Array<Object> actionObjectsLocked = getActionObjectsLocked(notification)
                if (actionObjectsLocked != null) {
                    Object obj = actionObjectsLocked[i]
                    Bundle extras = getExtras(notification)
                    return readAction(sActionIconField.getInt(obj), (CharSequence) sActionTitleField.get(obj), (PendingIntent) sActionIntentField.get(obj), (extras == null || (sparseParcelableArray = extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS)) == null) ? null : (Bundle) sparseParcelableArray.get(i))
                }
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification actions", e)
                sActionsAccessFailed = true
            }
            return null
        }
    }

    fun getActionCount(Notification notification) {
        Int length
        synchronized (sActionsLock) {
            Array<Object> actionObjectsLocked = getActionObjectsLocked(notification)
            length = actionObjectsLocked != null ? actionObjectsLocked.length : 0
        }
        return length
    }

    static NotificationCompat.Action getActionFromBundle(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle(KEY_EXTRAS)
        return new NotificationCompat.Action(bundle.getInt(KEY_ICON), bundle.getCharSequence("title"), (PendingIntent) bundle.getParcelable(KEY_ACTION_INTENT), bundle.getBundle(KEY_EXTRAS), fromBundleArray(getBundleArrayFromBundle(bundle, KEY_REMOTE_INPUTS)), fromBundleArray(getBundleArrayFromBundle(bundle, KEY_DATA_ONLY_REMOTE_INPUTS)), bundle2 != null ? bundle2.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES, false) : false, bundle.getInt(KEY_SEMANTIC_ACTION), bundle.getBoolean(KEY_SHOWS_USER_INTERFACE))
    }

    private static Array<Object> getActionObjectsLocked(Notification notification) {
        synchronized (sActionsLock) {
            if (!ensureActionReflectionReadyLocked()) {
                return null
            }
            try {
                return (Array<Object>) sActionsField.get(notification)
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification actions", e)
                sActionsAccessFailed = true
                return null
            }
        }
    }

    private static Array<Bundle> getBundleArrayFromBundle(Bundle bundle, String str) {
        Array<Parcelable> parcelableArray = bundle.getParcelableArray(str)
        if ((parcelableArray is Array<Bundle>) || parcelableArray == null) {
            return (Array<Bundle>) parcelableArray
        }
        Array<Bundle> bundleArr = (Array<Bundle>) Arrays.copyOf(parcelableArray, parcelableArray.length, Array<Bundle>.class)
        bundle.putParcelableArray(str, bundleArr)
        return bundleArr
    }

    static Bundle getBundleForAction(NotificationCompat.Action action) {
        Bundle bundle = Bundle()
        bundle.putInt(KEY_ICON, action.getIcon())
        bundle.putCharSequence("title", action.getTitle())
        bundle.putParcelable(KEY_ACTION_INTENT, action.getActionIntent())
        Bundle bundle2 = action.getExtras() != null ? Bundle(action.getExtras()) : Bundle()
        bundle2.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies())
        bundle.putBundle(KEY_EXTRAS, bundle2)
        bundle.putParcelableArray(KEY_REMOTE_INPUTS, toBundleArray(action.getRemoteInputs()))
        bundle.putBoolean(KEY_SHOWS_USER_INTERFACE, action.getShowsUserInterface())
        bundle.putInt(KEY_SEMANTIC_ACTION, action.getSemanticAction())
        return bundle
    }

    fun getExtras(Notification notification) {
        synchronized (sExtrasLock) {
            if (sExtrasFieldAccessFailed) {
                return null
            }
            try {
                if (sExtrasField == null) {
                    Field declaredField = Notification.class.getDeclaredField(KEY_EXTRAS)
                    if (!Bundle.class.isAssignableFrom(declaredField.getType())) {
                        Log.e(TAG, "Notification.extras field is not of type Bundle")
                        sExtrasFieldAccessFailed = true
                        return null
                    }
                    declaredField.setAccessible(true)
                    sExtrasField = declaredField
                }
                Bundle bundle = (Bundle) sExtrasField.get(notification)
                if (bundle == null) {
                    bundle = Bundle()
                    sExtrasField.set(notification, bundle)
                }
                return bundle
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Unable to access notification extras", e)
                sExtrasFieldAccessFailed = true
                return null
            } catch (NoSuchFieldException e2) {
                Log.e(TAG, "Unable to access notification extras", e2)
                sExtrasFieldAccessFailed = true
                return null
            }
        }
    }

    public static NotificationCompat.Action readAction(Int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle) {
        Boolean z
        Array<RemoteInput> remoteInputArrFromBundleArray
        Array<RemoteInput> remoteInputArrFromBundleArray2 = null
        if (bundle != null) {
            remoteInputArrFromBundleArray = fromBundleArray(getBundleArrayFromBundle(bundle, NotificationCompatExtras.EXTRA_REMOTE_INPUTS))
            remoteInputArrFromBundleArray2 = fromBundleArray(getBundleArrayFromBundle(bundle, EXTRA_DATA_ONLY_REMOTE_INPUTS))
            z = bundle.getBoolean(EXTRA_ALLOW_GENERATED_REPLIES)
        } else {
            z = false
            remoteInputArrFromBundleArray = null
        }
        return new NotificationCompat.Action(i, charSequence, pendingIntent, bundle, remoteInputArrFromBundleArray, remoteInputArrFromBundleArray2, z, 0, true)
    }

    private fun toBundle(RemoteInput remoteInput) {
        Bundle bundle = Bundle()
        bundle.putString(KEY_RESULT_KEY, remoteInput.getResultKey())
        bundle.putCharSequence(KEY_LABEL, remoteInput.getLabel())
        bundle.putCharSequenceArray(KEY_CHOICES, remoteInput.getChoices())
        bundle.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, remoteInput.getAllowFreeFormInput())
        bundle.putBundle(KEY_EXTRAS, remoteInput.getExtras())
        Set allowedDataTypes = remoteInput.getAllowedDataTypes()
        if (allowedDataTypes != null && !allowedDataTypes.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>(allowedDataTypes.size())
            Iterator it = allowedDataTypes.iterator()
            while (it.hasNext()) {
                arrayList.add((String) it.next())
            }
            bundle.putStringArrayList(KEY_ALLOWED_DATA_TYPES, arrayList)
        }
        return bundle
    }

    private static Array<Bundle> toBundleArray(Array<RemoteInput> remoteInputArr) {
        if (remoteInputArr == null) {
            return null
        }
        Array<Bundle> bundleArr = new Bundle[remoteInputArr.length]
        for (Int i = 0; i < remoteInputArr.length; i++) {
            bundleArr[i] = toBundle(remoteInputArr[i])
        }
        return bundleArr
    }

    fun writeActionAndGetExtras(Notification.Builder builder, NotificationCompat.Action action) {
        builder.addAction(action.getIcon(), action.getTitle(), action.getActionIntent())
        Bundle bundle = Bundle(action.getExtras())
        if (action.getRemoteInputs() != null) {
            bundle.putParcelableArray(NotificationCompatExtras.EXTRA_REMOTE_INPUTS, toBundleArray(action.getRemoteInputs()))
        }
        if (action.getDataOnlyRemoteInputs() != null) {
            bundle.putParcelableArray(EXTRA_DATA_ONLY_REMOTE_INPUTS, toBundleArray(action.getDataOnlyRemoteInputs()))
        }
        bundle.putBoolean(EXTRA_ALLOW_GENERATED_REPLIES, action.getAllowGeneratedReplies())
        return bundle
    }
}
