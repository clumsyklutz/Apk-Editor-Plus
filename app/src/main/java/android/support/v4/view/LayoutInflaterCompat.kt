package android.support.v4.view

import android.content.Context
import android.os.Build
import android.support.annotation.NonNull
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import java.lang.reflect.Field

class LayoutInflaterCompat {
    private static val TAG = "LayoutInflaterCompatHC"
    private static Boolean sCheckedField
    private static Field sLayoutInflaterFactory2Field

    class Factory2Wrapper implements LayoutInflater.Factory2 {
        final LayoutInflaterFactory mDelegateFactory

        Factory2Wrapper(LayoutInflaterFactory layoutInflaterFactory) {
            this.mDelegateFactory = layoutInflaterFactory
        }

        @Override // android.view.LayoutInflater.Factory2
        fun onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            return this.mDelegateFactory.onCreateView(view, str, context, attributeSet)
        }

        @Override // android.view.LayoutInflater.Factory
        fun onCreateView(String str, Context context, AttributeSet attributeSet) {
            return this.mDelegateFactory.onCreateView(null, str, context, attributeSet)
        }

        fun toString() {
            return getClass().getName() + "{" + this.mDelegateFactory + "}"
        }
    }

    private constructor() {
    }

    private fun forceSetFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (!sCheckedField) {
            try {
                Field declaredField = LayoutInflater.class.getDeclaredField("mFactory2")
                sLayoutInflaterFactory2Field = declaredField
                declaredField.setAccessible(true)
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", e)
            }
            sCheckedField = true
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set(layoutInflater, factory2)
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "forceSetFactory2 could not set the Factory2 on LayoutInflater " + layoutInflater + "; inflation may have unexpected results.", e2)
            }
        }
    }

    @Deprecated
    fun getFactory(LayoutInflater layoutInflater) {
        LayoutInflater.Factory factory = layoutInflater.getFactory()
        if (factory is Factory2Wrapper) {
            return ((Factory2Wrapper) factory).mDelegateFactory
        }
        return null
    }

    @Deprecated
    fun setFactory(@NonNull LayoutInflater layoutInflater, @NonNull LayoutInflaterFactory layoutInflaterFactory) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (Build.VERSION.SDK_INT >= 21) {
            layoutInflater.setFactory2(layoutInflaterFactory != null ? Factory2Wrapper(layoutInflaterFactory) : null)
            return
        }
        Factory2Wrapper factory2Wrapper = layoutInflaterFactory != null ? Factory2Wrapper(layoutInflaterFactory) : null
        layoutInflater.setFactory2(factory2Wrapper)
        LayoutInflater.Factory factory = layoutInflater.getFactory()
        if (factory is LayoutInflater.Factory2) {
            forceSetFactory2(layoutInflater, (LayoutInflater.Factory2) factory)
        } else {
            forceSetFactory2(layoutInflater, factory2Wrapper)
        }
    }

    fun setFactory2(@NonNull LayoutInflater layoutInflater, @NonNull LayoutInflater.Factory2 factory2) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        layoutInflater.setFactory2(factory2)
        if (Build.VERSION.SDK_INT < 21) {
            LayoutInflater.Factory factory = layoutInflater.getFactory()
            if (factory is LayoutInflater.Factory2) {
                forceSetFactory2(layoutInflater, (LayoutInflater.Factory2) factory)
            } else {
                forceSetFactory2(layoutInflater, factory2)
            }
        }
    }
}
