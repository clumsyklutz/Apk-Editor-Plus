package android.support.v7.widget

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.support.v4.view.ActionProvider
import androidx.appcompat.R
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.ActivityChooserModel
import android.util.TypedValue
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import java.lang.reflect.InvocationTargetException

class ShareActionProvider extends ActionProvider {
    private static val DEFAULT_INITIAL_ACTIVITY_COUNT = 4
    public static val DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml"
    final Context mContext
    private Int mMaxShownActivityCount
    private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener
    OnShareTargetSelectedListener mOnShareTargetSelectedListener
    String mShareHistoryFileName

    public interface OnShareTargetSelectedListener {
        Boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent)
    }

    class ShareActivityChooserModelPolicy implements ActivityChooserModel.OnChooseActivityListener {
        ShareActivityChooserModelPolicy() {
        }

        @Override // android.support.v7.widget.ActivityChooserModel.OnChooseActivityListener
        fun onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            if (ShareActionProvider.this.mOnShareTargetSelectedListener == null) {
                return false
            }
            ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, intent)
            return false
        }
    }

    class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        ShareMenuItemOnMenuItemClickListener() {
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        fun onMenuItemClick(MenuItem menuItem) {
            Intent intentChooseActivity = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).chooseActivity(menuItem.getItemId())
            if (intentChooseActivity == null) {
                return true
            }
            String action = intentChooseActivity.getAction()
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                ShareActionProvider.this.updateIntent(intentChooseActivity)
            }
            ShareActionProvider.this.mContext.startActivity(intentChooseActivity)
            return true
        }
    }

    constructor(Context context) {
        super(context)
        this.mMaxShownActivityCount = 4
        this.mOnMenuItemClickListener = ShareMenuItemOnMenuItemClickListener()
        this.mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME
        this.mContext = context
    }

    private fun setActivityChooserPolicyIfNeeded() {
        if (this.mOnShareTargetSelectedListener == null) {
            return
        }
        if (this.mOnChooseActivityListener == null) {
            this.mOnChooseActivityListener = ShareActivityChooserModelPolicy()
        }
        ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setOnChooseActivityListener(this.mOnChooseActivityListener)
    }

    @Override // android.support.v4.view.ActionProvider
    fun hasSubMenu() {
        return true
    }

    @Override // android.support.v4.view.ActionProvider
    fun onCreateActionView() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ActivityChooserView activityChooserView = ActivityChooserView(this.mContext)
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName))
        }
        TypedValue typedValue = TypedValue()
        this.mContext.getTheme().resolveAttribute(R.attr.actionModeShareDrawable, typedValue, true)
        activityChooserView.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.mContext, typedValue.resourceId))
        activityChooserView.setProvider(this)
        activityChooserView.setDefaultActionButtonContentDescription(R.string.abc_shareactionprovider_share_with_application)
        activityChooserView.setExpandActivityOverflowButtonContentDescription(R.string.abc_shareactionprovider_share_with)
        return activityChooserView
    }

    @Override // android.support.v4.view.ActionProvider
    fun onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear()
        ActivityChooserModel activityChooserModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName)
        PackageManager packageManager = this.mContext.getPackageManager()
        Int activityCount = activityChooserModel.getActivityCount()
        Int iMin = Math.min(activityCount, this.mMaxShownActivityCount)
        for (Int i = 0; i < iMin; i++) {
            ResolveInfo activity = activityChooserModel.getActivity(i)
            subMenu.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener)
        }
        if (iMin < activityCount) {
            SubMenu subMenuAddSubMenu = subMenu.addSubMenu(0, iMin, iMin, this.mContext.getString(R.string.abc_activity_chooser_view_see_all))
            for (Int i2 = 0; i2 < activityCount; i2++) {
                ResolveInfo activity2 = activityChooserModel.getActivity(i2)
                subMenuAddSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener)
            }
        }
    }

    fun setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.mOnShareTargetSelectedListener = onShareTargetSelectedListener
        setActivityChooserPolicyIfNeeded()
    }

    fun setShareHistoryFileName(String str) {
        this.mShareHistoryFileName = str
        setActivityChooserPolicyIfNeeded()
    }

    fun setShareIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction()
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                updateIntent(intent)
            }
        }
        ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setIntent(intent)
    }

    Unit updateIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            intent.addFlags(134742016)
        } else {
            intent.addFlags(524288)
        }
    }
}
