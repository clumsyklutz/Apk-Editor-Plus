package android.support.v7.app

import android.R
import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.util.ArrayMap
import androidx.core.view.ViewCompat
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatCheckedTextView
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView
import android.support.v7.widget.AppCompatRadioButton
import android.support.v7.widget.AppCompatRatingBar
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.TintContextWrapper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Map

class AppCompatViewInflater {
    private static val LOG_TAG = "AppCompatViewInflater"
    private final Array<Object> mConstructorArgs = new Object[2]
    private static final Array<Class> sConstructorSignature = {Context.class, AttributeSet.class}
    private static final Array<Int> sOnClickAttrs = {R.attr.onClick}
    private static final Array<String> sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."}
    private static val sConstructorMap = ArrayMap()

    class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView
        private final String mMethodName
        private Context mResolvedContext
        private Method mResolvedMethod

        constructor(@NonNull View view, @NonNull String str) {
            this.mHostView = view
            this.mMethodName = str
        }

        @NonNull
        private fun resolveMethod(@Nullable Context context, @NonNull String str) {
            Method method
            Context baseContext = context
            while (baseContext != null) {
                try {
                    if (!baseContext.isRestricted() && (method = baseContext.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method
                        this.mResolvedContext = baseContext
                        return
                    }
                } catch (NoSuchMethodException e) {
                }
                baseContext = baseContext is ContextWrapper ? ((ContextWrapper) baseContext).getBaseContext() : null
            }
            Int id = this.mHostView.getId()
            throw IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + (id == -1 ? "" : " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'"))
        }

        @Override // android.view.View.OnClickListener
        fun onClick(@NonNull View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName)
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view)
            } catch (IllegalAccessException e) {
                throw IllegalStateException("Could not execute non-public method for android:onClick", e)
            } catch (InvocationTargetException e2) {
                throw IllegalStateException("Could not execute method for android:onClick", e2)
            }
        }
    }

    private fun checkOnClickListener(View view, AttributeSet attributeSet) {
        Context context = view.getContext()
        if (context is ContextWrapper) {
            if (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, sOnClickAttrs)
                String string = typedArrayObtainStyledAttributes.getString(0)
                if (string != null) {
                    view.setOnClickListener(DeclaredOnClickListener(view, string))
                }
                typedArrayObtainStyledAttributes.recycle()
            }
        }
    }

    private fun createViewByPrefix(Context context, String str, String str2) throws NoSuchMethodException, SecurityException {
        Constructor constructor = (Constructor) sConstructorMap.get(str)
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(str2 != null ? str2 + str : str).asSubclass(View.class).getConstructor(sConstructorSignature)
                sConstructorMap.put(str, constructor)
            } catch (Exception e) {
                return null
            }
        }
        constructor.setAccessible(true)
        return (View) constructor.newInstance(this.mConstructorArgs)
    }

    private fun createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class")
        }
        try {
            this.mConstructorArgs[0] = context
            this.mConstructorArgs[1] = attributeSet
            if (-1 != str.indexOf(46)) {
                return createViewByPrefix(context, str, null)
            }
            for (Int i = 0; i < sClassPrefixList.length; i++) {
                View viewCreateViewByPrefix = createViewByPrefix(context, str, sClassPrefixList[i])
                if (viewCreateViewByPrefix != null) {
                    return viewCreateViewByPrefix
                }
            }
            return null
        } catch (Exception e) {
            return null
        } finally {
            this.mConstructorArgs[0] = null
            this.mConstructorArgs[1] = null
        }
    }

    private fun themifyContext(Context context, AttributeSet attributeSet, Boolean z, Boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.appcompat.R.styleable.View, 0, 0)
        Int resourceId = z ? typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.View_android_theme, 0) : 0
        if (z2 && resourceId == 0 && (resourceId = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.")
        }
        Int i = resourceId
        typedArrayObtainStyledAttributes.recycle()
        return i != 0 ? ((context is ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == i) ? context : ContextThemeWrapper(context, i) : context
    }

    private fun verifyNotNull(View view, String str) {
        if (view == null) {
            throw IllegalStateException(getClass().getName() + " asked to inflate view for <" + str + ">, but returned null")
        }
    }

    @NonNull
    protected fun createAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return AppCompatAutoCompleteTextView(context, attributeSet)
    }

    @NonNull
    protected fun createButton(Context context, AttributeSet attributeSet) {
        return AppCompatButton(context, attributeSet)
    }

    @NonNull
    protected fun createCheckBox(Context context, AttributeSet attributeSet) {
        return AppCompatCheckBox(context, attributeSet)
    }

    @NonNull
    protected fun createCheckedTextView(Context context, AttributeSet attributeSet) {
        return AppCompatCheckedTextView(context, attributeSet)
    }

    @NonNull
    protected fun createEditText(Context context, AttributeSet attributeSet) {
        return AppCompatEditText(context, attributeSet)
    }

    @NonNull
    protected fun createImageButton(Context context, AttributeSet attributeSet) {
        return AppCompatImageButton(context, attributeSet)
    }

    @NonNull
    protected fun createImageView(Context context, AttributeSet attributeSet) {
        return AppCompatImageView(context, attributeSet)
    }

    @NonNull
    protected fun createMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return AppCompatMultiAutoCompleteTextView(context, attributeSet)
    }

    @NonNull
    protected fun createRadioButton(Context context, AttributeSet attributeSet) {
        return AppCompatRadioButton(context, attributeSet)
    }

    @NonNull
    protected fun createRatingBar(Context context, AttributeSet attributeSet) {
        return AppCompatRatingBar(context, attributeSet)
    }

    @NonNull
    protected fun createSeekBar(Context context, AttributeSet attributeSet) {
        return AppCompatSeekBar(context, attributeSet)
    }

    @NonNull
    protected fun createSpinner(Context context, AttributeSet attributeSet) {
        return AppCompatSpinner(context, attributeSet)
    }

    @NonNull
    protected fun createTextView(Context context, AttributeSet attributeSet) {
        return AppCompatTextView(context, attributeSet)
    }

    @Nullable
    protected fun createView(Context context, String str, AttributeSet attributeSet) {
        return null
    }

    final View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet, Boolean z, Boolean z2, Boolean z3, Boolean z4) {
        Context context2
        View viewCreateSeekBar
        context2 = (!z || view == null) ? context : view.getContext()
        if (z2 || z3) {
            context2 = themifyContext(context2, attributeSet, z2, z3)
        }
        if (z4) {
            context2 = TintContextWrapper.wrap(context2)
        }
        switch (str) {
            case "TextView":
                viewCreateSeekBar = createTextView(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "ImageView":
                viewCreateSeekBar = createImageView(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "Button":
                viewCreateSeekBar = createButton(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "EditText":
                viewCreateSeekBar = createEditText(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "Spinner":
                viewCreateSeekBar = createSpinner(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "ImageButton":
                viewCreateSeekBar = createImageButton(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "CheckBox":
                viewCreateSeekBar = createCheckBox(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "RadioButton":
                viewCreateSeekBar = createRadioButton(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "CheckedTextView":
                viewCreateSeekBar = createCheckedTextView(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "AutoCompleteTextView":
                viewCreateSeekBar = createAutoCompleteTextView(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "MultiAutoCompleteTextView":
                viewCreateSeekBar = createMultiAutoCompleteTextView(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "RatingBar":
                viewCreateSeekBar = createRatingBar(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            case "SeekBar":
                viewCreateSeekBar = createSeekBar(context2, attributeSet)
                verifyNotNull(viewCreateSeekBar, str)
                break
            default:
                viewCreateSeekBar = createView(context2, str, attributeSet)
                break
        }
        View viewCreateViewFromTag = (viewCreateSeekBar != null || context == context2) ? viewCreateSeekBar : createViewFromTag(context2, str, attributeSet)
        if (viewCreateViewFromTag != null) {
            checkOnClickListener(viewCreateViewFromTag, attributeSet)
        }
        return viewCreateViewFromTag
    }
}
