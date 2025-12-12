package android.support.v7.app

import android.R
import android.app.Activity
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ActionBarDrawerToggleHoneycomb {
    private static val TAG = "ActionBarDrawerToggleHC"
    private static final Array<Int> THEME_ATTRS = {R.attr.homeAsUpIndicator}

    class SetIndicatorInfo {
        public Method setHomeActionContentDescription
        public Method setHomeAsUpIndicator
        public ImageView upIndicatorView

        SetIndicatorInfo(Activity activity) {
            try {
                this.setHomeAsUpIndicator = android.app.ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class)
                this.setHomeActionContentDescription = android.app.ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE)
            } catch (NoSuchMethodException e) {
                View viewFindViewById = activity.findViewById(R.id.home)
                if (viewFindViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent()
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0)
                        View childAt2 = childAt.getId() != 16908332 ? childAt : viewGroup.getChildAt(1)
                        if (childAt2 is ImageView) {
                            this.upIndicatorView = (ImageView) childAt2
                        }
                    }
                }
            }
        }
    }

    private constructor() {
    }

    fun getThemeUpIndicator(Activity activity) {
        TypedArray typedArrayObtainStyledAttributes = activity.obtainStyledAttributes(THEME_ATTRS)
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0)
        typedArrayObtainStyledAttributes.recycle()
        return drawable
    }

    fun setActionBarDescription(SetIndicatorInfo setIndicatorInfo, Activity activity, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (setIndicatorInfo == null) {
            setIndicatorInfo = SetIndicatorInfo(activity)
        }
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                android.app.ActionBar actionBar = activity.getActionBar()
                setIndicatorInfo.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(i))
                if (Build.VERSION.SDK_INT <= 19) {
                    actionBar.setSubtitle(actionBar.getSubtitle())
                }
            } catch (Exception e) {
                Log.w(TAG, "Couldn't set content description via JB-MR2 API", e)
            }
        }
        return setIndicatorInfo
    }

    fun setActionBarUpIndicator(SetIndicatorInfo setIndicatorInfo, Activity activity, Drawable drawable, Int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SetIndicatorInfo setIndicatorInfo2 = SetIndicatorInfo(activity)
        if (setIndicatorInfo2.setHomeAsUpIndicator != null) {
            try {
                android.app.ActionBar actionBar = activity.getActionBar()
                setIndicatorInfo2.setHomeAsUpIndicator.invoke(actionBar, drawable)
                setIndicatorInfo2.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(i))
            } catch (Exception e) {
                Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e)
            }
        } else if (setIndicatorInfo2.upIndicatorView != null) {
            setIndicatorInfo2.upIndicatorView.setImageDrawable(drawable)
        } else {
            Log.w(TAG, "Couldn't set home-as-up indicator")
        }
        return setIndicatorInfo2
    }
}
