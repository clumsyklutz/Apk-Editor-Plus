package android.support.v4.media

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.BadParcelableException
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.Parcel
import android.os.Parcelable
import android.os.RemoteException
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.annotation.RestrictTo
import android.support.v4.app.BundleCompat
import android.support.v4.media.MediaBrowserCompatApi21
import android.support.v4.media.MediaBrowserCompatApi23
import android.support.v4.media.MediaBrowserCompatApi26
import android.support.v4.media.session.IMediaSession
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.os.ResultReceiver
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.util.Log
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.Map

class MediaBrowserCompat {
    public static val CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD"
    public static val CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE"
    public static val EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS"
    public static val EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID"
    public static val EXTRA_PAGE = "android.media.browse.extra.PAGE"
    public static val EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE"
    private final MediaBrowserImpl mImpl
    static val TAG = "MediaBrowserCompat"
    static val DEBUG = Log.isLoggable(TAG, 3)

    class CallbackHandler extends Handler {
        private final WeakReference mCallbackImplRef
        private WeakReference mCallbacksMessengerRef

        CallbackHandler(MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl) {
            this.mCallbackImplRef = WeakReference(mediaBrowserServiceCallbackImpl)
        }

        @Override // android.os.Handler
        fun handleMessage(Message message) {
            if (this.mCallbacksMessengerRef == null || this.mCallbacksMessengerRef.get() == null || this.mCallbackImplRef.get() == null) {
                return
            }
            Bundle data = message.getData()
            data.setClassLoader(MediaSessionCompat.class.getClassLoader())
            MediaBrowserServiceCallbackImpl mediaBrowserServiceCallbackImpl = (MediaBrowserServiceCallbackImpl) this.mCallbackImplRef.get()
            Messenger messenger = (Messenger) this.mCallbacksMessengerRef.get()
            try {
                switch (message.what) {
                    case 1:
                        mediaBrowserServiceCallbackImpl.onServiceConnected(messenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (MediaSessionCompat.Token) data.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN), data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS))
                        break
                    case 2:
                        mediaBrowserServiceCallbackImpl.onConnectionFailed(messenger)
                        break
                    case 3:
                        mediaBrowserServiceCallbackImpl.onLoadChildren(messenger, data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), data.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST), data.getBundle(MediaBrowserProtocol.DATA_OPTIONS))
                        break
                    default:
                        Log.w(MediaBrowserCompat.TAG, "Unhandled message: " + message + "\n  Client version: 1\n  Service version: " + message.arg1)
                        break
                }
            } catch (BadParcelableException e) {
                Log.e(MediaBrowserCompat.TAG, "Could not unparcel the data.")
                if (message.what == 1) {
                    mediaBrowserServiceCallbackImpl.onConnectionFailed(messenger)
                }
            }
        }

        Unit setCallbacksMessenger(Messenger messenger) {
            this.mCallbacksMessengerRef = WeakReference(messenger)
        }
    }

    class ConnectionCallback {
        ConnectionCallbackInternal mConnectionCallbackInternal
        final Object mConnectionCallbackObj

        interface ConnectionCallbackInternal {
            Unit onConnected()

            Unit onConnectionFailed()

            Unit onConnectionSuspended()
        }

        class StubApi21 implements MediaBrowserCompatApi21.ConnectionCallback {
            StubApi21() {
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi21.ConnectionCallback
            fun onConnected() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnected()
                }
                ConnectionCallback.this.onConnected()
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi21.ConnectionCallback
            fun onConnectionFailed() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed()
                }
                ConnectionCallback.this.onConnectionFailed()
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi21.ConnectionCallback
            fun onConnectionSuspended() {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended()
                }
                ConnectionCallback.this.onConnectionSuspended()
            }
        }

        constructor() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(StubApi21())
            } else {
                this.mConnectionCallbackObj = null
            }
        }

        fun onConnected() {
        }

        fun onConnectionFailed() {
        }

        fun onConnectionSuspended() {
        }

        Unit setInternalConnectionCallback(ConnectionCallbackInternal connectionCallbackInternal) {
            this.mConnectionCallbackInternal = connectionCallbackInternal
        }
    }

    abstract class CustomActionCallback {
        fun onError(String str, Bundle bundle, Bundle bundle2) {
        }

        fun onProgressUpdate(String str, Bundle bundle, Bundle bundle2) {
        }

        fun onResult(String str, Bundle bundle, Bundle bundle2) {
        }
    }

    class CustomActionResultReceiver extends ResultReceiver {
        private final String mAction
        private final CustomActionCallback mCallback
        private final Bundle mExtras

        CustomActionResultReceiver(String str, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler)
            this.mAction = str
            this.mExtras = bundle
            this.mCallback = customActionCallback
        }

        @Override // android.support.v4.os.ResultReceiver
        protected fun onReceiveResult(Int i, Bundle bundle) {
            if (this.mCallback == null) {
            }
            switch (i) {
                case -1:
                    this.mCallback.onError(this.mAction, this.mExtras, bundle)
                    break
                case 0:
                    this.mCallback.onResult(this.mAction, this.mExtras, bundle)
                    break
                case 1:
                    this.mCallback.onProgressUpdate(this.mAction, this.mExtras, bundle)
                    break
                default:
                    Log.w(MediaBrowserCompat.TAG, "Unknown result code: " + i + " (extras=" + this.mExtras + ", resultData=" + bundle + ")")
                    break
            }
        }
    }

    abstract class ItemCallback {
        final Object mItemCallbackObj

        class StubApi23 implements MediaBrowserCompatApi23.ItemCallback {
            StubApi23() {
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi23.ItemCallback
            fun onError(@NonNull String str) {
                ItemCallback.this.onError(str)
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi23.ItemCallback
            fun onItemLoaded(Parcel parcel) {
                if (parcel == null) {
                    ItemCallback.this.onItemLoaded(null)
                    return
                }
                parcel.setDataPosition(0)
                MediaItem mediaItem = (MediaItem) MediaItem.CREATOR.createFromParcel(parcel)
                parcel.recycle()
                ItemCallback.this.onItemLoaded(mediaItem)
            }
        }

        constructor() {
            if (Build.VERSION.SDK_INT >= 23) {
                this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(StubApi23())
            } else {
                this.mItemCallbackObj = null
            }
        }

        fun onError(@NonNull String str) {
        }

        fun onItemLoaded(MediaItem mediaItem) {
        }
    }

    class ItemReceiver extends ResultReceiver {
        private final ItemCallback mCallback
        private final String mMediaId

        ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            super(handler)
            this.mMediaId = str
            this.mCallback = itemCallback
        }

        @Override // android.support.v4.os.ResultReceiver
        protected fun onReceiveResult(Int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader())
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                this.mCallback.onError(this.mMediaId)
                return
            }
            Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)
            if (parcelable == null || (parcelable is MediaItem)) {
                this.mCallback.onItemLoaded((MediaItem) parcelable)
            } else {
                this.mCallback.onError(this.mMediaId)
            }
        }
    }

    interface MediaBrowserImpl {
        Unit connect()

        Unit disconnect()

        @Nullable
        Bundle getExtras()

        Unit getItem(@NonNull String str, @NonNull ItemCallback itemCallback)

        @NonNull
        String getRoot()

        ComponentName getServiceComponent()

        @NonNull
        MediaSessionCompat.Token getSessionToken()

        Boolean isConnected()

        Unit search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback)

        Unit sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback)

        Unit subscribe(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback)

        Unit unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback)
    }

    @RequiresApi(21)
    class MediaBrowserImplApi21 implements ConnectionCallback.ConnectionCallbackInternal, MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        protected final Object mBrowserObj
        protected Messenger mCallbacksMessenger
        final Context mContext
        private MediaSessionCompat.Token mMediaSessionToken
        protected final Bundle mRootHints
        protected ServiceBinderWrapper mServiceBinderWrapper
        protected Int mServiceVersion
        protected val mHandler = CallbackHandler(this)
        private val mSubscriptions = ArrayMap()

        MediaBrowserImplApi21(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.mContext = context
            bundle = bundle == null ? Bundle() : bundle
            bundle.putInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 1)
            this.mRootHints = Bundle(bundle)
            connectionCallback.setInternalConnectionCallback(this)
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context, componentName, connectionCallback.mConnectionCallbackObj, this.mRootHints)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun connect() {
            MediaBrowserCompatApi21.connect(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun disconnect() {
            if (this.mServiceBinderWrapper != null && this.mCallbacksMessenger != null) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger)
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error unregistering client messenger.")
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @Nullable
        fun getExtras() {
            return MediaBrowserCompatApi21.getExtras(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun getItem(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw IllegalArgumentException("mediaId is empty")
            }
            if (itemCallback == null) {
                throw IllegalArgumentException("cb is null")
            }
            if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.")
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.1
                    @Override // java.lang.Runnable
                    fun run() {
                        itemCallback.onError(str)
                    }
                })
            } else {
                if (this.mServiceBinderWrapper == null) {
                    this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.2
                        @Override // java.lang.Runnable
                        fun run() {
                            itemCallback.onError(str)
                        }
                    })
                    return
                }
                try {
                    this.mServiceBinderWrapper.getMediaItem(str, ItemReceiver(str, itemCallback, this.mHandler), this.mCallbacksMessenger)
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error getting media item: " + str)
                    this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.3
                        @Override // java.lang.Runnable
                        fun run() {
                            itemCallback.onError(str)
                        }
                    })
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @NonNull
        fun getRoot() {
            return MediaBrowserCompatApi21.getRoot(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun getServiceComponent() {
            return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @NonNull
        public MediaSessionCompat.Token getSessionToken() {
            if (this.mMediaSessionToken == null) {
                this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj))
            }
            return this.mMediaSessionToken
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun isConnected() {
            return MediaBrowserCompatApi21.isConnected(this.mBrowserObj)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.ConnectionCallbackInternal
        fun onConnected() {
            Bundle extras = MediaBrowserCompatApi21.getExtras(this.mBrowserObj)
            if (extras == null) {
                return
            }
            this.mServiceVersion = extras.getInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 0)
            IBinder binder = BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER)
            if (binder != null) {
                this.mServiceBinderWrapper = ServiceBinderWrapper(binder, this.mRootHints)
                this.mCallbacksMessenger = Messenger(this.mHandler)
                this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger)
                try {
                    this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger)
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error registering client messenger.")
                }
            }
            IMediaSession iMediaSessionAsInterface = IMediaSession.Stub.asInterface(BundleCompat.getBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER))
            if (iMediaSessionAsInterface != null) {
                this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), iMediaSessionAsInterface)
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.ConnectionCallbackInternal
        fun onConnectionFailed() {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onConnectionFailed(Messenger messenger) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.ConnectionCallback.ConnectionCallbackInternal
        fun onConnectionSuspended() {
            this.mServiceBinderWrapper = null
            this.mCallbacksMessenger = null
            this.mMediaSessionToken = null
            this.mHandler.setCallbacksMessenger(null)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onLoadChildren(Messenger messenger, String str, List list, Bundle bundle) {
            if (this.mCallbacksMessenger != messenger) {
                return
            }
            Subscription subscription = (Subscription) this.mSubscriptions.get(str)
            if (subscription == null) {
                if (MediaBrowserCompat.DEBUG) {
                    Log.d(MediaBrowserCompat.TAG, "onLoadChildren for id that isn't subscribed id=" + str)
                    return
                }
                return
            }
            SubscriptionCallback callback = subscription.getCallback(this.mContext, bundle)
            if (callback != null) {
                if (bundle == null) {
                    if (list == null) {
                        callback.onError(str)
                        return
                    } else {
                        callback.onChildrenLoaded(str, list)
                        return
                    }
                }
                if (list == null) {
                    callback.onError(str, bundle)
                } else {
                    callback.onChildrenLoaded(str, list, bundle)
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onServiceConnected(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun search(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (!isConnected()) {
                throw IllegalStateException("search() called while not connected")
            }
            if (this.mServiceBinderWrapper == null) {
                Log.i(MediaBrowserCompat.TAG, "The connected service doesn't support search.")
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.4
                    @Override // java.lang.Runnable
                    fun run() {
                        searchCallback.onError(str, bundle)
                    }
                })
                return
            }
            try {
                this.mServiceBinderWrapper.search(str, bundle, SearchResultReceiver(str, bundle, searchCallback, this.mHandler), this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error searching items with query: " + str, e)
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.5
                    @Override // java.lang.Runnable
                    fun run() {
                        searchCallback.onError(str, bundle)
                    }
                })
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun sendCustomAction(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (!isConnected()) {
                throw IllegalStateException("Cannot send a custom action (" + str + ") with extras " + bundle + " because the browser is not connected to the service.")
            }
            if (this.mServiceBinderWrapper == null) {
                Log.i(MediaBrowserCompat.TAG, "The connected service doesn't support sendCustomAction.")
                if (customActionCallback != null) {
                    this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.6
                        @Override // java.lang.Runnable
                        fun run() {
                            customActionCallback.onError(str, bundle, null)
                        }
                    })
                }
            }
            try {
                this.mServiceBinderWrapper.sendCustomAction(str, bundle, CustomActionResultReceiver(str, bundle, customActionCallback, this.mHandler), this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error sending a custom action: action=" + str + ", extras=" + bundle, e)
                if (customActionCallback != null) {
                    this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21.7
                        @Override // java.lang.Runnable
                        fun run() {
                            customActionCallback.onError(str, bundle, null)
                        }
                    })
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Subscription subscription = (Subscription) this.mSubscriptions.get(str)
            if (subscription == null) {
                subscription = Subscription()
                this.mSubscriptions.put(str, subscription)
            }
            subscriptionCallback.setSubscription(subscription)
            Bundle bundle2 = bundle == null ? null : Bundle(bundle)
            subscription.putCallback(this.mContext, bundle2, subscriptionCallback)
            if (this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, str, subscriptionCallback.mSubscriptionCallbackObj)
                return
            }
            try {
                this.mServiceBinderWrapper.addSubscription(str, subscriptionCallback.mToken, bundle2, this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error subscribing media item: " + str)
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Subscription subscription = (Subscription) this.mSubscriptions.get(str)
            if (subscription == null) {
                return
            }
            if (this.mServiceBinderWrapper != null) {
                try {
                    if (subscriptionCallback == null) {
                        this.mServiceBinderWrapper.removeSubscription(str, null, this.mCallbacksMessenger)
                    } else {
                        List callbacks = subscription.getCallbacks()
                        List optionsList = subscription.getOptionsList()
                        for (Int size = callbacks.size() - 1; size >= 0; size--) {
                            if (callbacks.get(size) == subscriptionCallback) {
                                this.mServiceBinderWrapper.removeSubscription(str, subscriptionCallback.mToken, this.mCallbacksMessenger)
                                callbacks.remove(size)
                                optionsList.remove(size)
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + str)
                }
            } else if (subscriptionCallback == null) {
                MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, str)
            } else {
                List callbacks2 = subscription.getCallbacks()
                List optionsList2 = subscription.getOptionsList()
                for (Int size2 = callbacks2.size() - 1; size2 >= 0; size2--) {
                    if (callbacks2.get(size2) == subscriptionCallback) {
                        callbacks2.remove(size2)
                        optionsList2.remove(size2)
                    }
                }
                if (callbacks2.size() == 0) {
                    MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, str)
                }
            }
            if (subscription.isEmpty() || subscriptionCallback == null) {
                this.mSubscriptions.remove(str)
            }
        }
    }

    @RequiresApi(23)
    class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        MediaBrowserImplApi23(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21, android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
            if (this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi23.getItem(this.mBrowserObj, str, itemCallback.mItemCallbackObj)
            } else {
                super.getItem(str, itemCallback)
            }
        }
    }

    @RequiresApi(26)
    class MediaBrowserImplApi26 extends MediaBrowserImplApi23 {
        MediaBrowserImplApi26(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle)
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21, android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun subscribe(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
                super.subscribe(str, bundle, subscriptionCallback)
            } else if (bundle == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, str, subscriptionCallback.mSubscriptionCallbackObj)
            } else {
                MediaBrowserCompatApi26.subscribe(this.mBrowserObj, str, bundle, subscriptionCallback.mSubscriptionCallbackObj)
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImplApi21, android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
                super.unsubscribe(str, subscriptionCallback)
            } else if (subscriptionCallback == null) {
                MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, str)
            } else {
                MediaBrowserCompatApi26.unsubscribe(this.mBrowserObj, str, subscriptionCallback.mSubscriptionCallbackObj)
            }
        }
    }

    class MediaBrowserImplBase implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        static val CONNECT_STATE_CONNECTED = 3
        static val CONNECT_STATE_CONNECTING = 2
        static val CONNECT_STATE_DISCONNECTED = 1
        static val CONNECT_STATE_DISCONNECTING = 0
        static val CONNECT_STATE_SUSPENDED = 4
        final ConnectionCallback mCallback
        Messenger mCallbacksMessenger
        final Context mContext
        private Bundle mExtras
        private MediaSessionCompat.Token mMediaSessionToken
        final Bundle mRootHints
        private String mRootId
        ServiceBinderWrapper mServiceBinderWrapper
        final ComponentName mServiceComponent
        MediaServiceConnection mServiceConnection
        val mHandler = CallbackHandler(this)
        private val mSubscriptions = ArrayMap()
        Int mState = 1

        class MediaServiceConnection implements ServiceConnection {
            MediaServiceConnection() {
            }

            private fun postOrRun(Runnable runnable) {
                if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                    runnable.run()
                } else {
                    MediaBrowserImplBase.this.mHandler.post(runnable)
                }
            }

            Boolean isCurrent(String str) {
                if (MediaBrowserImplBase.this.mServiceConnection == this && MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                    return true
                }
                if (MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                    Log.i(MediaBrowserCompat.TAG, str + " for " + MediaBrowserImplBase.this.mServiceComponent + " with mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection + " this=" + this)
                }
                return false
            }

            @Override // android.content.ServiceConnection
            fun onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
                postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection.1
                    @Override // java.lang.Runnable
                    fun run() {
                        if (MediaBrowserCompat.DEBUG) {
                            Log.d(MediaBrowserCompat.TAG, "MediaServiceConnection.onServiceConnected name=" + componentName + " binder=" + iBinder)
                            MediaBrowserImplBase.this.dump()
                        }
                        if (MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                            MediaBrowserImplBase.this.mServiceBinderWrapper = ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.mRootHints)
                            MediaBrowserImplBase.this.mCallbacksMessenger = Messenger(MediaBrowserImplBase.this.mHandler)
                            MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger)
                            MediaBrowserImplBase.this.mState = 2
                            try {
                                if (MediaBrowserCompat.DEBUG) {
                                    Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...")
                                    MediaBrowserImplBase.this.dump()
                                }
                                MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger)
                            } catch (RemoteException e) {
                                Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + MediaBrowserImplBase.this.mServiceComponent)
                                if (MediaBrowserCompat.DEBUG) {
                                    Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...")
                                    MediaBrowserImplBase.this.dump()
                                }
                            }
                        }
                    }
                })
            }

            @Override // android.content.ServiceConnection
            fun onServiceDisconnected(final ComponentName componentName) {
                postOrRun(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection.2
                    @Override // java.lang.Runnable
                    fun run() {
                        if (MediaBrowserCompat.DEBUG) {
                            Log.d(MediaBrowserCompat.TAG, "MediaServiceConnection.onServiceDisconnected name=" + componentName + " this=" + this + " mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection)
                            MediaBrowserImplBase.this.dump()
                        }
                        if (MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                            MediaBrowserImplBase.this.mServiceBinderWrapper = null
                            MediaBrowserImplBase.this.mCallbacksMessenger = null
                            MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null)
                            MediaBrowserImplBase.this.mState = 4
                            MediaBrowserImplBase.this.mCallback.onConnectionSuspended()
                        }
                    }
                })
            }
        }

        constructor(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            if (context == null) {
                throw IllegalArgumentException("context must not be null")
            }
            if (componentName == null) {
                throw IllegalArgumentException("service component must not be null")
            }
            if (connectionCallback == null) {
                throw IllegalArgumentException("connection callback must not be null")
            }
            this.mContext = context
            this.mServiceComponent = componentName
            this.mCallback = connectionCallback
            this.mRootHints = bundle == null ? null : Bundle(bundle)
        }

        private fun getStateLabel(Int i) {
            switch (i) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTING"
                case 1:
                    return "CONNECT_STATE_DISCONNECTED"
                case 2:
                    return "CONNECT_STATE_CONNECTING"
                case 3:
                    return "CONNECT_STATE_CONNECTED"
                case 4:
                    return "CONNECT_STATE_SUSPENDED"
                default:
                    return "UNKNOWN/" + i
            }
        }

        private fun isCurrent(Messenger messenger, String str) {
            if (this.mCallbacksMessenger == messenger && this.mState != 0 && this.mState != 1) {
                return true
            }
            if (this.mState != 0 && this.mState != 1) {
                Log.i(MediaBrowserCompat.TAG, str + " for " + this.mServiceComponent + " with mCallbacksMessenger=" + this.mCallbacksMessenger + " this=" + this)
            }
            return false
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun connect() {
            if (this.mState != 0 && this.mState != 1) {
                throw IllegalStateException("connect() called while neigther disconnecting nor disconnected (state=" + getStateLabel(this.mState) + ")")
            }
            this.mState = 2
            this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.1
                @Override // java.lang.Runnable
                fun run() {
                    if (MediaBrowserImplBase.this.mState == 0) {
                        return
                    }
                    MediaBrowserImplBase.this.mState = 2
                    if (MediaBrowserCompat.DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                        throw RuntimeException("mServiceConnection should be null. Instead it is " + MediaBrowserImplBase.this.mServiceConnection)
                    }
                    if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                        throw RuntimeException("mServiceBinderWrapper should be null. Instead it is " + MediaBrowserImplBase.this.mServiceBinderWrapper)
                    }
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        throw RuntimeException("mCallbacksMessenger should be null. Instead it is " + MediaBrowserImplBase.this.mCallbacksMessenger)
                    }
                    Intent intent = Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE)
                    intent.setComponent(MediaBrowserImplBase.this.mServiceComponent)
                    MediaBrowserImplBase.this.mServiceConnection = MediaBrowserImplBase.this.MediaServiceConnection()
                    Boolean zBindService = false
                    try {
                        zBindService = MediaBrowserImplBase.this.mContext.bindService(intent, MediaBrowserImplBase.this.mServiceConnection, 1)
                    } catch (Exception e) {
                        Log.e(MediaBrowserCompat.TAG, "Failed binding to service " + MediaBrowserImplBase.this.mServiceComponent)
                    }
                    if (!zBindService) {
                        MediaBrowserImplBase.this.forceCloseConnection()
                        MediaBrowserImplBase.this.mCallback.onConnectionFailed()
                    }
                    if (MediaBrowserCompat.DEBUG) {
                        Log.d(MediaBrowserCompat.TAG, "connect...")
                        MediaBrowserImplBase.this.dump()
                    }
                }
            })
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun disconnect() {
            this.mState = 0
            this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.2
                @Override // java.lang.Runnable
                fun run() {
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        try {
                            MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger)
                        } catch (RemoteException e) {
                            Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + MediaBrowserImplBase.this.mServiceComponent)
                        }
                    }
                    Int i = MediaBrowserImplBase.this.mState
                    MediaBrowserImplBase.this.forceCloseConnection()
                    if (i != 0) {
                        MediaBrowserImplBase.this.mState = i
                    }
                    if (MediaBrowserCompat.DEBUG) {
                        Log.d(MediaBrowserCompat.TAG, "disconnect...")
                        MediaBrowserImplBase.this.dump()
                    }
                }
            })
        }

        Unit dump() {
            Log.d(MediaBrowserCompat.TAG, "MediaBrowserCompat...")
            Log.d(MediaBrowserCompat.TAG, "  mServiceComponent=" + this.mServiceComponent)
            Log.d(MediaBrowserCompat.TAG, "  mCallback=" + this.mCallback)
            Log.d(MediaBrowserCompat.TAG, "  mRootHints=" + this.mRootHints)
            Log.d(MediaBrowserCompat.TAG, "  mState=" + getStateLabel(this.mState))
            Log.d(MediaBrowserCompat.TAG, "  mServiceConnection=" + this.mServiceConnection)
            Log.d(MediaBrowserCompat.TAG, "  mServiceBinderWrapper=" + this.mServiceBinderWrapper)
            Log.d(MediaBrowserCompat.TAG, "  mCallbacksMessenger=" + this.mCallbacksMessenger)
            Log.d(MediaBrowserCompat.TAG, "  mRootId=" + this.mRootId)
            Log.d(MediaBrowserCompat.TAG, "  mMediaSessionToken=" + this.mMediaSessionToken)
        }

        Unit forceCloseConnection() {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService(this.mServiceConnection)
            }
            this.mState = 1
            this.mServiceConnection = null
            this.mServiceBinderWrapper = null
            this.mCallbacksMessenger = null
            this.mHandler.setCallbacksMessenger(null)
            this.mRootId = null
            this.mMediaSessionToken = null
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @Nullable
        fun getExtras() {
            if (isConnected()) {
                return this.mExtras
            }
            throw IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + ")")
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun getItem(@NonNull final String str, @NonNull final ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw IllegalArgumentException("mediaId is empty")
            }
            if (itemCallback == null) {
                throw IllegalArgumentException("cb is null")
            }
            if (!isConnected()) {
                Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.")
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.3
                    @Override // java.lang.Runnable
                    fun run() {
                        itemCallback.onError(str)
                    }
                })
                return
            }
            try {
                this.mServiceBinderWrapper.getMediaItem(str, ItemReceiver(str, itemCallback, this.mHandler), this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error getting media item: " + str)
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.4
                    @Override // java.lang.Runnable
                    fun run() {
                        itemCallback.onError(str)
                    }
                })
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @NonNull
        fun getRoot() {
            if (isConnected()) {
                return this.mRootId
            }
            throw IllegalStateException("getRoot() called while not connected(state=" + getStateLabel(this.mState) + ")")
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @NonNull
        fun getServiceComponent() {
            if (isConnected()) {
                return this.mServiceComponent
            }
            throw IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + ")")
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        @NonNull
        public MediaSessionCompat.Token getSessionToken() {
            if (isConnected()) {
                return this.mMediaSessionToken
            }
            throw IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")")
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun isConnected() {
            return this.mState == 3
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onConnectionFailed(Messenger messenger) {
            Log.e(MediaBrowserCompat.TAG, "onConnectFailed for " + this.mServiceComponent)
            if (isCurrent(messenger, "onConnectFailed")) {
                if (this.mState != 2) {
                    Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring")
                } else {
                    forceCloseConnection()
                    this.mCallback.onConnectionFailed()
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onLoadChildren(Messenger messenger, String str, List list, Bundle bundle) {
            if (isCurrent(messenger, "onLoadChildren")) {
                if (MediaBrowserCompat.DEBUG) {
                    Log.d(MediaBrowserCompat.TAG, "onLoadChildren for " + this.mServiceComponent + " id=" + str)
                }
                Subscription subscription = (Subscription) this.mSubscriptions.get(str)
                if (subscription == null) {
                    if (MediaBrowserCompat.DEBUG) {
                        Log.d(MediaBrowserCompat.TAG, "onLoadChildren for id that isn't subscribed id=" + str)
                        return
                    }
                    return
                }
                SubscriptionCallback callback = subscription.getCallback(this.mContext, bundle)
                if (callback != null) {
                    if (bundle == null) {
                        if (list == null) {
                            callback.onError(str)
                            return
                        } else {
                            callback.onChildrenLoaded(str, list)
                            return
                        }
                    }
                    if (list == null) {
                        callback.onError(str, bundle)
                    } else {
                        callback.onChildrenLoaded(str, list, bundle)
                    }
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserServiceCallbackImpl
        fun onServiceConnected(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (isCurrent(messenger, "onConnect")) {
                if (this.mState != 2) {
                    Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring")
                    return
                }
                this.mRootId = str
                this.mMediaSessionToken = token
                this.mExtras = bundle
                this.mState = 3
                if (MediaBrowserCompat.DEBUG) {
                    Log.d(MediaBrowserCompat.TAG, "ServiceCallbacks.onConnect...")
                    dump()
                }
                this.mCallback.onConnected()
                try {
                    for (Map.Entry entry : this.mSubscriptions.entrySet()) {
                        String str2 = (String) entry.getKey()
                        Subscription subscription = (Subscription) entry.getValue()
                        List callbacks = subscription.getCallbacks()
                        List optionsList = subscription.getOptionsList()
                        Int i = 0
                        while (true) {
                            Int i2 = i
                            if (i2 < callbacks.size()) {
                                this.mServiceBinderWrapper.addSubscription(str2, ((SubscriptionCallback) callbacks.get(i2)).mToken, (Bundle) optionsList.get(i2), this.mCallbacksMessenger)
                                i = i2 + 1
                            }
                        }
                    }
                } catch (RemoteException e) {
                    Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException.")
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun search(@NonNull final String str, final Bundle bundle, @NonNull final SearchCallback searchCallback) {
            if (!isConnected()) {
                throw IllegalStateException("search() called while not connected (state=" + getStateLabel(this.mState) + ")")
            }
            try {
                this.mServiceBinderWrapper.search(str, bundle, SearchResultReceiver(str, bundle, searchCallback, this.mHandler), this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error searching items with query: " + str, e)
                this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.5
                    @Override // java.lang.Runnable
                    fun run() {
                        searchCallback.onError(str, bundle)
                    }
                })
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun sendCustomAction(@NonNull final String str, final Bundle bundle, @Nullable final CustomActionCallback customActionCallback) {
            if (!isConnected()) {
                throw IllegalStateException("Cannot send a custom action (" + str + ") with extras " + bundle + " because the browser is not connected to the service.")
            }
            try {
                this.mServiceBinderWrapper.sendCustomAction(str, bundle, CustomActionResultReceiver(str, bundle, customActionCallback, this.mHandler), this.mCallbacksMessenger)
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error sending a custom action: action=" + str + ", extras=" + bundle, e)
                if (customActionCallback != null) {
                    this.mHandler.post(Runnable() { // from class: android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase.6
                        @Override // java.lang.Runnable
                        fun run() {
                            customActionCallback.onError(str, bundle, null)
                        }
                    })
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Subscription subscription
            Subscription subscription2 = (Subscription) this.mSubscriptions.get(str)
            if (subscription2 == null) {
                Subscription subscription3 = Subscription()
                this.mSubscriptions.put(str, subscription3)
                subscription = subscription3
            } else {
                subscription = subscription2
            }
            Bundle bundle2 = bundle == null ? null : Bundle(bundle)
            subscription.putCallback(this.mContext, bundle2, subscriptionCallback)
            if (isConnected()) {
                try {
                    this.mServiceBinderWrapper.addSubscription(str, subscriptionCallback.mToken, bundle2, this.mCallbacksMessenger)
                } catch (RemoteException e) {
                    Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException parentId=" + str)
                }
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.MediaBrowserImpl
        fun unsubscribe(@NonNull String str, SubscriptionCallback subscriptionCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            Subscription subscription = (Subscription) this.mSubscriptions.get(str)
            if (subscription == null) {
                return
            }
            try {
                if (subscriptionCallback != null) {
                    List callbacks = subscription.getCallbacks()
                    List optionsList = subscription.getOptionsList()
                    for (Int size = callbacks.size() - 1; size >= 0; size--) {
                        if (callbacks.get(size) == subscriptionCallback) {
                            if (isConnected()) {
                                this.mServiceBinderWrapper.removeSubscription(str, subscriptionCallback.mToken, this.mCallbacksMessenger)
                            }
                            callbacks.remove(size)
                            optionsList.remove(size)
                        }
                    }
                } else if (isConnected()) {
                    this.mServiceBinderWrapper.removeSubscription(str, null, this.mCallbacksMessenger)
                }
            } catch (RemoteException e) {
                Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + str)
            }
            if (subscription.isEmpty() || subscriptionCallback == null) {
                this.mSubscriptions.remove(str)
            }
        }
    }

    interface MediaBrowserServiceCallbackImpl {
        Unit onConnectionFailed(Messenger messenger)

        Unit onLoadChildren(Messenger messenger, String str, List list, Bundle bundle)

        Unit onServiceConnected(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle)
    }

    class MediaItem implements Parcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.MediaBrowserCompat.MediaItem.1
            @Override // android.os.Parcelable.Creator
            public final MediaItem createFromParcel(Parcel parcel) {
                return MediaItem(parcel)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<MediaItem> newArray(Int i) {
                return new MediaItem[i]
            }
        }
        public static val FLAG_BROWSABLE = 1
        public static val FLAG_PLAYABLE = 2
        private final MediaDescriptionCompat mDescription
        private final Int mFlags

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface Flags {
        }

        MediaItem(Parcel parcel) {
            this.mFlags = parcel.readInt()
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(parcel)
        }

        constructor(@NonNull MediaDescriptionCompat mediaDescriptionCompat, Int i) {
            if (mediaDescriptionCompat == null) {
                throw IllegalArgumentException("description cannot be null")
            }
            if (TextUtils.isEmpty(mediaDescriptionCompat.getMediaId())) {
                throw IllegalArgumentException("description must have a non-empty media id")
            }
            this.mFlags = i
            this.mDescription = mediaDescriptionCompat
        }

        fun fromMediaItem(Object obj) {
            if (obj == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            return MediaItem(MediaDescriptionCompat.fromMediaDescription(MediaBrowserCompatApi21.MediaItem.getDescription(obj)), MediaBrowserCompatApi21.MediaItem.getFlags(obj))
        }

        fun fromMediaItemList(List list) {
            if (list == null || Build.VERSION.SDK_INT < 21) {
                return null
            }
            ArrayList arrayList = ArrayList(list.size())
            Iterator it = list.iterator()
            while (it.hasNext()) {
                arrayList.add(fromMediaItem(it.next()))
            }
            return arrayList
        }

        @Override // android.os.Parcelable
        fun describeContents() {
            return 0
        }

        @NonNull
        fun getDescription() {
            return this.mDescription
        }

        fun getFlags() {
            return this.mFlags
        }

        @Nullable
        fun getMediaId() {
            return this.mDescription.getMediaId()
        }

        fun isBrowsable() {
            return (this.mFlags & 1) != 0
        }

        fun isPlayable() {
            return (this.mFlags & 2) != 0
        }

        fun toString() {
            StringBuilder sb = StringBuilder("MediaItem{")
            sb.append("mFlags=").append(this.mFlags)
            sb.append(", mDescription=").append(this.mDescription)
            sb.append('}')
            return sb.toString()
        }

        @Override // android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            parcel.writeInt(this.mFlags)
            this.mDescription.writeToParcel(parcel, i)
        }
    }

    abstract class SearchCallback {
        fun onError(@NonNull String str, Bundle bundle) {
        }

        fun onSearchResult(@NonNull String str, Bundle bundle, @NonNull List list) {
        }
    }

    class SearchResultReceiver extends ResultReceiver {
        private final SearchCallback mCallback
        private final Bundle mExtras
        private final String mQuery

        SearchResultReceiver(String str, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler)
            this.mQuery = str
            this.mExtras = bundle
            this.mCallback = searchCallback
        }

        @Override // android.support.v4.os.ResultReceiver
        protected fun onReceiveResult(Int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader())
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                this.mCallback.onError(this.mQuery, this.mExtras)
                return
            }
            Array<Parcelable> parcelableArray = bundle.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)
            ArrayList arrayList = null
            if (parcelableArray != null) {
                ArrayList arrayList2 = ArrayList()
                for (Parcelable parcelable : parcelableArray) {
                    arrayList2.add((MediaItem) parcelable)
                }
                arrayList = arrayList2
            }
            this.mCallback.onSearchResult(this.mQuery, this.mExtras, arrayList)
        }
    }

    class ServiceBinderWrapper {
        private Messenger mMessenger
        private Bundle mRootHints

        constructor(IBinder iBinder, Bundle bundle) {
            this.mMessenger = Messenger(iBinder)
            this.mRootHints = bundle
        }

        private fun sendRequest(Int i, Bundle bundle, Messenger messenger) throws RemoteException {
            Message messageObtain = Message.obtain()
            messageObtain.what = i
            messageObtain.arg1 = 1
            messageObtain.setData(bundle)
            messageObtain.replyTo = messenger
            this.mMessenger.send(messageObtain)
        }

        Unit addSubscription(String str, IBinder iBinder, Bundle bundle, Messenger messenger) throws IllegalAccessException, NoSuchMethodException, SecurityException, RemoteException, IllegalArgumentException, InvocationTargetException {
            Bundle bundle2 = Bundle()
            bundle2.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str)
            BundleCompat.putBinder(bundle2, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, iBinder)
            bundle2.putBundle(MediaBrowserProtocol.DATA_OPTIONS, bundle)
            sendRequest(3, bundle2, messenger)
        }

        Unit connect(Context context, Messenger messenger) throws RemoteException {
            Bundle bundle = Bundle()
            bundle.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, context.getPackageName())
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints)
            sendRequest(1, bundle, messenger)
        }

        Unit disconnect(Messenger messenger) throws RemoteException {
            sendRequest(2, null, messenger)
        }

        Unit getMediaItem(String str, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle = Bundle()
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str)
            bundle.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver)
            sendRequest(5, bundle, messenger)
        }

        Unit registerCallbackMessenger(Messenger messenger) throws RemoteException {
            Bundle bundle = Bundle()
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, this.mRootHints)
            sendRequest(6, bundle, messenger)
        }

        Unit removeSubscription(String str, IBinder iBinder, Messenger messenger) throws IllegalAccessException, NoSuchMethodException, SecurityException, RemoteException, IllegalArgumentException, InvocationTargetException {
            Bundle bundle = Bundle()
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, str)
            BundleCompat.putBinder(bundle, MediaBrowserProtocol.DATA_CALLBACK_TOKEN, iBinder)
            sendRequest(4, bundle, messenger)
        }

        Unit search(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = Bundle()
            bundle2.putString(MediaBrowserProtocol.DATA_SEARCH_QUERY, str)
            bundle2.putBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS, bundle)
            bundle2.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver)
            sendRequest(8, bundle2, messenger)
        }

        Unit sendCustomAction(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = Bundle()
            bundle2.putString(MediaBrowserProtocol.DATA_CUSTOM_ACTION, str)
            bundle2.putBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS, bundle)
            bundle2.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, resultReceiver)
            sendRequest(9, bundle2, messenger)
        }

        Unit unregisterCallbackMessenger(Messenger messenger) throws RemoteException {
            sendRequest(7, null, messenger)
        }
    }

    class Subscription {
        private val mCallbacks = ArrayList()
        private val mOptionsList = ArrayList()

        fun getCallback(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader())
            }
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 >= this.mOptionsList.size()) {
                    return null
                }
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i2), bundle)) {
                    return (SubscriptionCallback) this.mCallbacks.get(i2)
                }
                i = i2 + 1
            }
        }

        fun getCallbacks() {
            return this.mCallbacks
        }

        fun getOptionsList() {
            return this.mOptionsList
        }

        fun isEmpty() {
            return this.mCallbacks.isEmpty()
        }

        fun putCallback(Context context, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader())
            }
            Int i = 0
            while (true) {
                Int i2 = i
                if (i2 >= this.mOptionsList.size()) {
                    this.mCallbacks.add(subscriptionCallback)
                    this.mOptionsList.add(bundle)
                    return
                } else {
                    if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get(i2), bundle)) {
                        this.mCallbacks.set(i2, subscriptionCallback)
                        return
                    }
                    i = i2 + 1
                }
            }
        }
    }

    abstract class SubscriptionCallback {
        private final Object mSubscriptionCallbackObj
        WeakReference mSubscriptionRef
        private val mToken = Binder()

        class StubApi21 implements MediaBrowserCompatApi21.SubscriptionCallback {
            StubApi21() {
            }

            List applyOptions(List list, Bundle bundle) {
                if (list == null) {
                    return null
                }
                Int i = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1)
                Int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1)
                if (i == -1 && i2 == -1) {
                    return list
                }
                Int i3 = i2 * i
                Int size = i3 + i2
                if (i < 0 || i2 <= 0 || i3 >= list.size()) {
                    return Collections.EMPTY_LIST
                }
                if (size > list.size()) {
                    size = list.size()
                }
                return list.subList(i3, size)
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi21.SubscriptionCallback
            fun onChildrenLoaded(@NonNull String str, List list) {
                Subscription subscription = SubscriptionCallback.this.mSubscriptionRef == null ? null : (Subscription) SubscriptionCallback.this.mSubscriptionRef.get()
                if (subscription == null) {
                    SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list))
                    return
                }
                List listFromMediaItemList = MediaItem.fromMediaItemList(list)
                List callbacks = subscription.getCallbacks()
                List optionsList = subscription.getOptionsList()
                Int i = 0
                while (true) {
                    Int i2 = i
                    if (i2 >= callbacks.size()) {
                        return
                    }
                    Bundle bundle = (Bundle) optionsList.get(i2)
                    if (bundle == null) {
                        SubscriptionCallback.this.onChildrenLoaded(str, listFromMediaItemList)
                    } else {
                        SubscriptionCallback.this.onChildrenLoaded(str, applyOptions(listFromMediaItemList, bundle), bundle)
                    }
                    i = i2 + 1
                }
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi21.SubscriptionCallback
            fun onError(@NonNull String str) {
                SubscriptionCallback.this.onError(str)
            }
        }

        class StubApi26 extends StubApi21 implements MediaBrowserCompatApi26.SubscriptionCallback {
            StubApi26() {
                super()
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi26.SubscriptionCallback
            fun onChildrenLoaded(@NonNull String str, List list, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list), bundle)
            }

            @Override // android.support.v4.media.MediaBrowserCompatApi26.SubscriptionCallback
            fun onError(@NonNull String str, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onError(str, bundle)
            }
        }

        constructor() {
            if (Build.VERSION.SDK_INT >= 26) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi26.createSubscriptionCallback(StubApi26())
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(StubApi21())
            } else {
                this.mSubscriptionCallbackObj = null
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun setSubscription(Subscription subscription) {
            this.mSubscriptionRef = WeakReference(subscription)
        }

        fun onChildrenLoaded(@NonNull String str, @NonNull List list) {
        }

        fun onChildrenLoaded(@NonNull String str, @NonNull List list, @NonNull Bundle bundle) {
        }

        fun onError(@NonNull String str) {
        }

        fun onError(@NonNull String str, @NonNull Bundle bundle) {
        }
    }

    constructor(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mImpl = MediaBrowserImplApi26(context, componentName, connectionCallback, bundle)
            return
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = MediaBrowserImplApi23(context, componentName, connectionCallback, bundle)
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = MediaBrowserImplApi21(context, componentName, connectionCallback, bundle)
        } else {
            this.mImpl = MediaBrowserImplBase(context, componentName, connectionCallback, bundle)
        }
    }

    public final Unit connect() {
        this.mImpl.connect()
    }

    public final Unit disconnect() {
        this.mImpl.disconnect()
    }

    @Nullable
    public final Bundle getExtras() {
        return this.mImpl.getExtras()
    }

    public final Unit getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        this.mImpl.getItem(str, itemCallback)
    }

    @NonNull
    public final String getRoot() {
        return this.mImpl.getRoot()
    }

    @NonNull
    public final ComponentName getServiceComponent() {
        return this.mImpl.getServiceComponent()
    }

    @NonNull
    public final MediaSessionCompat.Token getSessionToken() {
        return this.mImpl.getSessionToken()
    }

    public final Boolean isConnected() {
        return this.mImpl.isConnected()
    }

    public final Unit search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("query cannot be empty")
        }
        if (searchCallback == null) {
            throw IllegalArgumentException("callback cannot be null")
        }
        this.mImpl.search(str, bundle, searchCallback)
    }

    public final Unit sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("action cannot be empty")
        }
        this.mImpl.sendCustomAction(str, bundle, customActionCallback)
    }

    public final Unit subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("parentId is empty")
        }
        if (subscriptionCallback == null) {
            throw IllegalArgumentException("callback is null")
        }
        if (bundle == null) {
            throw IllegalArgumentException("options are null")
        }
        this.mImpl.subscribe(str, bundle, subscriptionCallback)
    }

    public final Unit subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("parentId is empty")
        }
        if (subscriptionCallback == null) {
            throw IllegalArgumentException("callback is null")
        }
        this.mImpl.subscribe(str, null, subscriptionCallback)
    }

    public final Unit unsubscribe(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("parentId is empty")
        }
        this.mImpl.unsubscribe(str, null)
    }

    public final Unit unsubscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw IllegalArgumentException("parentId is empty")
        }
        if (subscriptionCallback == null) {
            throw IllegalArgumentException("callback is null")
        }
        this.mImpl.unsubscribe(str, subscriptionCallback)
    }
}
