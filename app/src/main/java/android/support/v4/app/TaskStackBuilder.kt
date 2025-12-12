package android.support.v4.app

import android.app.Activity
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import androidx.core.content.ContextCompat
import android.util.Log
import java.util.ArrayList
import java.util.Iterator

class TaskStackBuilder implements Iterable {
    private static val TAG = "TaskStackBuilder"
    private val mIntents = ArrayList()
    private final Context mSourceContext

    public interface SupportParentable {
        @Nullable
        Intent getSupportParentActivityIntent()
    }

    private constructor(Context context) {
        this.mSourceContext = context
    }

    @NonNull
    fun create(@NonNull Context context) {
        return TaskStackBuilder(context)
    }

    @Deprecated
    fun from(Context context) {
        return create(context)
    }

    @NonNull
    public final TaskStackBuilder addNextIntent(@NonNull Intent intent) {
        this.mIntents.add(intent)
        return this
    }

    @NonNull
    public final TaskStackBuilder addNextIntentWithParentStack(@NonNull Intent intent) {
        ComponentName component = intent.getComponent()
        if (component == null) {
            component = intent.resolveActivity(this.mSourceContext.getPackageManager())
        }
        if (component != null) {
            addParentStack(component)
        }
        addNextIntent(intent)
        return this
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public final TaskStackBuilder addParentStack(@NonNull Activity activity) {
        Intent supportParentActivityIntent = activity is SupportParentable ? ((SupportParentable) activity).getSupportParentActivityIntent() : null
        Intent parentActivityIntent = supportParentActivityIntent == null ? NavUtils.getParentActivityIntent(activity) : supportParentActivityIntent
        if (parentActivityIntent != null) {
            ComponentName component = parentActivityIntent.getComponent()
            if (component == null) {
                component = parentActivityIntent.resolveActivity(this.mSourceContext.getPackageManager())
            }
            addParentStack(component)
            addNextIntent(parentActivityIntent)
        }
        return this
    }

    public final TaskStackBuilder addParentStack(ComponentName componentName) {
        Int size = this.mIntents.size()
        try {
            Intent parentActivityIntent = NavUtils.getParentActivityIntent(this.mSourceContext, componentName)
            while (parentActivityIntent != null) {
                this.mIntents.add(size, parentActivityIntent)
                parentActivityIntent = NavUtils.getParentActivityIntent(this.mSourceContext, parentActivityIntent.getComponent())
            }
            return this
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Bad ComponentName while traversing activity parent metadata")
            throw IllegalArgumentException(e)
        }
    }

    @NonNull
    public final TaskStackBuilder addParentStack(@NonNull Class cls) {
        return addParentStack(ComponentName(this.mSourceContext, (Class<?>) cls))
    }

    @Nullable
    public final Intent editIntentAt(Int i) {
        return (Intent) this.mIntents.get(i)
    }

    @Deprecated
    public final Intent getIntent(Int i) {
        return editIntentAt(i)
    }

    public final Int getIntentCount() {
        return this.mIntents.size()
    }

    @NonNull
    public final Array<Intent> getIntents() {
        Array<Intent> intentArr = new Intent[this.mIntents.size()]
        if (intentArr.length == 0) {
            return intentArr
        }
        intentArr[0] = Intent((Intent) this.mIntents.get(0)).addFlags(268484608)
        Int i = 1
        while (true) {
            Int i2 = i
            if (i2 >= intentArr.length) {
                return intentArr
            }
            intentArr[i2] = Intent((Intent) this.mIntents.get(i2))
            i = i2 + 1
        }
    }

    @Nullable
    public final PendingIntent getPendingIntent(Int i, Int i2) {
        return getPendingIntent(i, i2, null)
    }

    @Nullable
    public final PendingIntent getPendingIntent(Int i, Int i2, @Nullable Bundle bundle) {
        if (this.mIntents.isEmpty()) {
            throw IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent")
        }
        Array<Intent> intentArr = (Array<Intent>) this.mIntents.toArray(new Intent[this.mIntents.size()])
        intentArr[0] = Intent(intentArr[0]).addFlags(268484608)
        return Build.VERSION.SDK_INT >= 16 ? PendingIntent.getActivities(this.mSourceContext, i, intentArr, i2, bundle) : PendingIntent.getActivities(this.mSourceContext, i, intentArr, i2)
    }

    @Override // java.lang.Iterable
    @Deprecated
    public final Iterator iterator() {
        return this.mIntents.iterator()
    }

    public final Unit startActivities() {
        startActivities(null)
    }

    public final Unit startActivities(@Nullable Bundle bundle) {
        if (this.mIntents.isEmpty()) {
            throw IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities")
        }
        Array<Intent> intentArr = (Array<Intent>) this.mIntents.toArray(new Intent[this.mIntents.size()])
        intentArr[0] = Intent(intentArr[0]).addFlags(268484608)
        if (ContextCompat.startActivities(this.mSourceContext, intentArr, bundle)) {
            return
        }
        Intent intent = Intent(intentArr[intentArr.length - 1])
        intent.addFlags(268435456)
        this.mSourceContext.startActivity(intent)
    }
}
