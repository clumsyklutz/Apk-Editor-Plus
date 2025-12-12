package android.support.v4.app

import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobServiceEngine
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import java.util.ArrayList
import java.util.HashMap

abstract class JobIntentService extends Service {
    static val DEBUG = false
    static val TAG = "JobIntentService"
    final ArrayList mCompatQueue
    WorkEnqueuer mCompatWorkEnqueuer
    CommandProcessor mCurProcessor
    CompatJobEngine mJobImpl
    static val sLock = Object()
    static val sClassWorkEnqueuer = HashMap()
    Boolean mInterruptIfStopped = false
    Boolean mStopped = false
    Boolean mDestroyed = false

    final class CommandProcessor extends AsyncTask {
        CommandProcessor() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public final Void doInBackground(Void... voidArr) {
            while (true) {
                GenericWorkItem genericWorkItemDequeueWork = JobIntentService.this.dequeueWork()
                if (genericWorkItemDequeueWork == null) {
                    return null
                }
                JobIntentService.this.onHandleWork(genericWorkItemDequeueWork.getIntent())
                genericWorkItemDequeueWork.complete()
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public final Unit onCancelled(Void r2) {
            JobIntentService.this.processorFinished()
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public final Unit onPostExecute(Void r2) {
            JobIntentService.this.processorFinished()
        }
    }

    interface CompatJobEngine {
        IBinder compatGetBinder()

        GenericWorkItem dequeueWork()
    }

    final class CompatWorkEnqueuer extends WorkEnqueuer {
        private final Context mContext
        private final PowerManager.WakeLock mLaunchWakeLock
        Boolean mLaunchingService
        private final PowerManager.WakeLock mRunWakeLock
        Boolean mServiceProcessing

        CompatWorkEnqueuer(Context context, ComponentName componentName) {
            super(context, componentName)
            this.mContext = context.getApplicationContext()
            PowerManager powerManager = (PowerManager) context.getSystemService("power")
            this.mLaunchWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":launch")
            this.mLaunchWakeLock.setReferenceCounted(false)
            this.mRunWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":run")
            this.mRunWakeLock.setReferenceCounted(false)
        }

        @Override // android.support.v4.app.JobIntentService.WorkEnqueuer
        final Unit enqueueWork(Intent intent) {
            Intent intent2 = Intent(intent)
            intent2.setComponent(this.mComponentName)
            if (this.mContext.startService(intent2) != null) {
                synchronized (this) {
                    if (!this.mLaunchingService) {
                        this.mLaunchingService = true
                        if (!this.mServiceProcessing) {
                            this.mLaunchWakeLock.acquire(60000L)
                        }
                    }
                }
            }
        }

        @Override // android.support.v4.app.JobIntentService.WorkEnqueuer
        public final Unit serviceProcessingFinished() {
            synchronized (this) {
                if (this.mServiceProcessing) {
                    if (this.mLaunchingService) {
                        this.mLaunchWakeLock.acquire(60000L)
                    }
                    this.mServiceProcessing = false
                    this.mRunWakeLock.release()
                }
            }
        }

        @Override // android.support.v4.app.JobIntentService.WorkEnqueuer
        public final Unit serviceProcessingStarted() {
            synchronized (this) {
                if (!this.mServiceProcessing) {
                    this.mServiceProcessing = true
                    this.mRunWakeLock.acquire(600000L)
                    this.mLaunchWakeLock.release()
                }
            }
        }

        @Override // android.support.v4.app.JobIntentService.WorkEnqueuer
        public final Unit serviceStartReceived() {
            synchronized (this) {
                this.mLaunchingService = false
            }
        }
    }

    final class CompatWorkItem implements GenericWorkItem {
        final Intent mIntent
        final Int mStartId

        CompatWorkItem(Intent intent, Int i) {
            this.mIntent = intent
            this.mStartId = i
        }

        @Override // android.support.v4.app.JobIntentService.GenericWorkItem
        public final Unit complete() {
            JobIntentService.this.stopSelf(this.mStartId)
        }

        @Override // android.support.v4.app.JobIntentService.GenericWorkItem
        public final Intent getIntent() {
            return this.mIntent
        }
    }

    interface GenericWorkItem {
        Unit complete()

        Intent getIntent()
    }

    @RequiresApi(26)
    final class JobServiceEngineImpl extends JobServiceEngine implements CompatJobEngine {
        static val DEBUG = false
        static val TAG = "JobServiceEngineImpl"
        final Object mLock
        JobParameters mParams
        final JobIntentService mService

        final class WrapperWorkItem implements GenericWorkItem {
            final JobWorkItem mJobWork

            WrapperWorkItem(JobWorkItem jobWorkItem) {
                this.mJobWork = jobWorkItem
            }

            @Override // android.support.v4.app.JobIntentService.GenericWorkItem
            public final Unit complete() {
                synchronized (JobServiceEngineImpl.this.mLock) {
                    if (JobServiceEngineImpl.this.mParams != null) {
                        JobServiceEngineImpl.this.mParams.completeWork(this.mJobWork)
                    }
                }
            }

            @Override // android.support.v4.app.JobIntentService.GenericWorkItem
            public final Intent getIntent() {
                return this.mJobWork.getIntent()
            }
        }

        JobServiceEngineImpl(JobIntentService jobIntentService) {
            super(jobIntentService)
            this.mLock = Object()
            this.mService = jobIntentService
        }

        @Override // android.support.v4.app.JobIntentService.CompatJobEngine
        public final IBinder compatGetBinder() {
            return getBinder()
        }

        @Override // android.support.v4.app.JobIntentService.CompatJobEngine
        public final GenericWorkItem dequeueWork() {
            synchronized (this.mLock) {
                if (this.mParams == null) {
                    return null
                }
                JobWorkItem jobWorkItemDequeueWork = this.mParams.dequeueWork()
                if (jobWorkItemDequeueWork == null) {
                    return null
                }
                jobWorkItemDequeueWork.getIntent().setExtrasClassLoader(this.mService.getClassLoader())
                return WrapperWorkItem(jobWorkItemDequeueWork)
            }
        }

        @Override // android.app.job.JobServiceEngine
        public final Boolean onStartJob(JobParameters jobParameters) {
            this.mParams = jobParameters
            this.mService.ensureProcessorRunningLocked(false)
            return true
        }

        @Override // android.app.job.JobServiceEngine
        public final Boolean onStopJob(JobParameters jobParameters) {
            Boolean zDoStopCurrentWork = this.mService.doStopCurrentWork()
            synchronized (this.mLock) {
                this.mParams = null
            }
            return zDoStopCurrentWork
        }
    }

    @RequiresApi(26)
    final class JobWorkEnqueuer extends WorkEnqueuer {
        private final JobInfo mJobInfo
        private final JobScheduler mJobScheduler

        JobWorkEnqueuer(Context context, ComponentName componentName, Int i) {
            super(context, componentName)
            ensureJobId(i)
            this.mJobInfo = new JobInfo.Builder(i, this.mComponentName).setOverrideDeadline(0L).build()
            this.mJobScheduler = (JobScheduler) context.getApplicationContext().getSystemService("jobscheduler")
        }

        @Override // android.support.v4.app.JobIntentService.WorkEnqueuer
        final Unit enqueueWork(Intent intent) {
            this.mJobScheduler.enqueue(this.mJobInfo, JobWorkItem(intent))
        }
    }

    abstract class WorkEnqueuer {
        final ComponentName mComponentName
        Boolean mHasJobId
        Int mJobId

        WorkEnqueuer(Context context, ComponentName componentName) {
            this.mComponentName = componentName
        }

        abstract Unit enqueueWork(Intent intent)

        Unit ensureJobId(Int i) {
            if (this.mHasJobId) {
                if (this.mJobId != i) {
                    throw IllegalArgumentException("Given job ID " + i + " is different than previous " + this.mJobId)
                }
            } else {
                this.mHasJobId = true
                this.mJobId = i
            }
        }

        fun serviceProcessingFinished() {
        }

        fun serviceProcessingStarted() {
        }

        fun serviceStartReceived() {
        }
    }

    constructor() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mCompatQueue = null
        } else {
            this.mCompatQueue = ArrayList()
        }
    }

    fun enqueueWork(@NonNull Context context, @NonNull ComponentName componentName, Int i, @NonNull Intent intent) {
        if (intent == null) {
            throw IllegalArgumentException("work must not be null")
        }
        synchronized (sLock) {
            WorkEnqueuer workEnqueuer = getWorkEnqueuer(context, componentName, true, i)
            workEnqueuer.ensureJobId(i)
            workEnqueuer.enqueueWork(intent)
        }
    }

    fun enqueueWork(@NonNull Context context, @NonNull Class cls, Int i, @NonNull Intent intent) {
        enqueueWork(context, ComponentName(context, (Class<?>) cls), i, intent)
    }

    static WorkEnqueuer getWorkEnqueuer(Context context, ComponentName componentName, Boolean z, Int i) {
        WorkEnqueuer compatWorkEnqueuer = (WorkEnqueuer) sClassWorkEnqueuer.get(componentName)
        if (compatWorkEnqueuer == null) {
            if (Build.VERSION.SDK_INT < 26) {
                compatWorkEnqueuer = CompatWorkEnqueuer(context, componentName)
            } else {
                if (!z) {
                    throw IllegalArgumentException("Can't be here without a job id")
                }
                compatWorkEnqueuer = JobWorkEnqueuer(context, componentName, i)
            }
            sClassWorkEnqueuer.put(componentName, compatWorkEnqueuer)
        }
        return compatWorkEnqueuer
    }

    GenericWorkItem dequeueWork() {
        GenericWorkItem genericWorkItem
        if (this.mJobImpl != null) {
            return this.mJobImpl.dequeueWork()
        }
        synchronized (this.mCompatQueue) {
            genericWorkItem = this.mCompatQueue.size() > 0 ? (GenericWorkItem) this.mCompatQueue.remove(0) : null
        }
        return genericWorkItem
    }

    Boolean doStopCurrentWork() {
        if (this.mCurProcessor != null) {
            this.mCurProcessor.cancel(this.mInterruptIfStopped)
        }
        this.mStopped = true
        return onStopCurrentWork()
    }

    Unit ensureProcessorRunningLocked(Boolean z) {
        if (this.mCurProcessor == null) {
            this.mCurProcessor = CommandProcessor()
            if (this.mCompatWorkEnqueuer != null && z) {
                this.mCompatWorkEnqueuer.serviceProcessingStarted()
            }
            this.mCurProcessor.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0])
        }
    }

    fun isStopped() {
        return this.mStopped
    }

    @Override // android.app.Service
    fun onBind(@NonNull Intent intent) {
        if (this.mJobImpl != null) {
            return this.mJobImpl.compatGetBinder()
        }
        return null
    }

    @Override // android.app.Service
    fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= 26) {
            this.mJobImpl = JobServiceEngineImpl(this)
            this.mCompatWorkEnqueuer = null
        } else {
            this.mJobImpl = null
            this.mCompatWorkEnqueuer = getWorkEnqueuer(this, ComponentName(this, getClass()), false, 0)
        }
    }

    @Override // android.app.Service
    fun onDestroy() {
        super.onDestroy()
        if (this.mCompatQueue != null) {
            synchronized (this.mCompatQueue) {
                this.mDestroyed = true
                this.mCompatWorkEnqueuer.serviceProcessingFinished()
            }
        }
    }

    protected abstract Unit onHandleWork(@NonNull Intent intent)

    @Override // android.app.Service
    fun onStartCommand(@Nullable Intent intent, Int i, Int i2) {
        if (this.mCompatQueue == null) {
            return 2
        }
        this.mCompatWorkEnqueuer.serviceStartReceived()
        synchronized (this.mCompatQueue) {
            ArrayList arrayList = this.mCompatQueue
            if (intent == null) {
                intent = Intent()
            }
            arrayList.add(CompatWorkItem(intent, i2))
            ensureProcessorRunningLocked(true)
        }
        return 3
    }

    fun onStopCurrentWork() {
        return true
    }

    Unit processorFinished() {
        if (this.mCompatQueue != null) {
            synchronized (this.mCompatQueue) {
                this.mCurProcessor = null
                if (this.mCompatQueue != null && this.mCompatQueue.size() > 0) {
                    ensureProcessorRunningLocked(false)
                } else if (!this.mDestroyed) {
                    this.mCompatWorkEnqueuer.serviceProcessingFinished()
                }
            }
        }
    }

    fun setInterruptIfStopped(Boolean z) {
        this.mInterruptIfStopped = z
    }
}
