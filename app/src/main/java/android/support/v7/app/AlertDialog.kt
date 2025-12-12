package android.support.v7.app

import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Message
import android.support.annotation.ArrayRes
import android.support.annotation.AttrRes
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v7.app.AlertController
import androidx.appcompat.R
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView

class AlertDialog extends AppCompatDialog implements DialogInterface {
    static val LAYOUT_HINT_NONE = 0
    static val LAYOUT_HINT_SIDE = 1
    final AlertController mAlert

    class Builder {
        private final AlertController.AlertParams P
        private final Int mTheme

        constructor(@NonNull Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0))
        }

        constructor(@NonNull Context context, @StyleRes Int i) {
            this.P = new AlertController.AlertParams(ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, i)))
            this.mTheme = i
        }

        fun create() {
            AlertDialog alertDialog = AlertDialog(this.P.mContext, this.mTheme)
            this.P.apply(alertDialog.mAlert)
            alertDialog.setCancelable(this.P.mCancelable)
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true)
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener)
            alertDialog.setOnDismissListener(this.P.mOnDismissListener)
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener)
            }
            return alertDialog
        }

        @NonNull
        fun getContext() {
            return this.P.mContext
        }

        fun setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter
            this.P.mOnClickListener = onClickListener
            return this
        }

        fun setCancelable(Boolean z) {
            this.P.mCancelable = z
            return this
        }

        fun setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String str) {
            this.P.mCursor = cursor
            this.P.mLabelColumn = str
            this.P.mOnClickListener = onClickListener
            return this
        }

        fun setCustomTitle(@Nullable View view) {
            this.P.mCustomTitleView = view
            return this
        }

        fun setIcon(@DrawableRes Int i) {
            this.P.mIconId = i
            return this
        }

        fun setIcon(@Nullable Drawable drawable) {
            this.P.mIcon = drawable
            return this
        }

        fun setIconAttribute(@AttrRes Int i) {
            TypedValue typedValue = TypedValue()
            this.P.mContext.getTheme().resolveAttribute(i, typedValue, true)
            this.P.mIconId = typedValue.resourceId
            return this
        }

        @Deprecated
        fun setInverseBackgroundForced(Boolean z) {
            this.P.mForceInverseBackground = z
            return this
        }

        fun setItems(@ArrayRes Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i)
            this.P.mOnClickListener = onClickListener
            return this
        }

        fun setItems(Array<CharSequence> charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArr
            this.P.mOnClickListener = onClickListener
            return this
        }

        fun setMessage(@StringRes Int i) {
            this.P.mMessage = this.P.mContext.getText(i)
            return this
        }

        fun setMessage(@Nullable CharSequence charSequence) {
            this.P.mMessage = charSequence
            return this
        }

        fun setMultiChoiceItems(@ArrayRes Int i, Array<Boolean> zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i)
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener
            this.P.mCheckedItems = zArr
            this.P.mIsMultiChoice = true
            return this
        }

        fun setMultiChoiceItems(Cursor cursor, String str, String str2, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mCursor = cursor
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener
            this.P.mIsCheckedColumn = str
            this.P.mLabelColumn = str2
            this.P.mIsMultiChoice = true
            return this
        }

        fun setMultiChoiceItems(Array<CharSequence> charSequenceArr, Array<Boolean> zArr, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            this.P.mItems = charSequenceArr
            this.P.mOnCheckboxClickListener = onMultiChoiceClickListener
            this.P.mCheckedItems = zArr
            this.P.mIsMultiChoice = true
            return this
        }

        fun setNegativeButton(@StringRes Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = this.P.mContext.getText(i)
            this.P.mNegativeButtonListener = onClickListener
            return this
        }

        fun setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNegativeButtonText = charSequence
            this.P.mNegativeButtonListener = onClickListener
            return this
        }

        fun setNegativeButtonIcon(Drawable drawable) {
            this.P.mNegativeButtonIcon = drawable
            return this
        }

        fun setNeutralButton(@StringRes Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = this.P.mContext.getText(i)
            this.P.mNeutralButtonListener = onClickListener
            return this
        }

        fun setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mNeutralButtonText = charSequence
            this.P.mNeutralButtonListener = onClickListener
            return this
        }

        fun setNeutralButtonIcon(Drawable drawable) {
            this.P.mNeutralButtonIcon = drawable
            return this
        }

        fun setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener
            return this
        }

        fun setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener
            return this
        }

        fun setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener
            return this
        }

        fun setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener
            return this
        }

        fun setPositiveButton(@StringRes Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = this.P.mContext.getText(i)
            this.P.mPositiveButtonListener = onClickListener
            return this
        }

        fun setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            this.P.mPositiveButtonText = charSequence
            this.P.mPositiveButtonListener = onClickListener
            return this
        }

        fun setPositiveButtonIcon(Drawable drawable) {
            this.P.mPositiveButtonIcon = drawable
            return this
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        fun setRecycleOnMeasureEnabled(Boolean z) {
            this.P.mRecycleOnMeasure = z
            return this
        }

        fun setSingleChoiceItems(@ArrayRes Int i, Int i2, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = this.P.mContext.getResources().getTextArray(i)
            this.P.mOnClickListener = onClickListener
            this.P.mCheckedItem = i2
            this.P.mIsSingleChoice = true
            return this
        }

        fun setSingleChoiceItems(Cursor cursor, Int i, String str, DialogInterface.OnClickListener onClickListener) {
            this.P.mCursor = cursor
            this.P.mOnClickListener = onClickListener
            this.P.mCheckedItem = i
            this.P.mLabelColumn = str
            this.P.mIsSingleChoice = true
            return this
        }

        fun setSingleChoiceItems(ListAdapter listAdapter, Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mAdapter = listAdapter
            this.P.mOnClickListener = onClickListener
            this.P.mCheckedItem = i
            this.P.mIsSingleChoice = true
            return this
        }

        fun setSingleChoiceItems(Array<CharSequence> charSequenceArr, Int i, DialogInterface.OnClickListener onClickListener) {
            this.P.mItems = charSequenceArr
            this.P.mOnClickListener = onClickListener
            this.P.mCheckedItem = i
            this.P.mIsSingleChoice = true
            return this
        }

        fun setTitle(@StringRes Int i) {
            this.P.mTitle = this.P.mContext.getText(i)
            return this
        }

        fun setTitle(@Nullable CharSequence charSequence) {
            this.P.mTitle = charSequence
            return this
        }

        fun setView(Int i) {
            this.P.mView = null
            this.P.mViewLayoutResId = i
            this.P.mViewSpacingSpecified = false
            return this
        }

        fun setView(View view) {
            this.P.mView = view
            this.P.mViewLayoutResId = 0
            this.P.mViewSpacingSpecified = false
            return this
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @Deprecated
        fun setView(View view, Int i, Int i2, Int i3, Int i4) {
            this.P.mView = view
            this.P.mViewLayoutResId = 0
            this.P.mViewSpacingSpecified = true
            this.P.mViewSpacingLeft = i
            this.P.mViewSpacingTop = i2
            this.P.mViewSpacingRight = i3
            this.P.mViewSpacingBottom = i4
            return this
        }

        fun show() {
            AlertDialog alertDialogCreate = create()
            alertDialogCreate.show()
            return alertDialogCreate
        }
    }

    protected constructor(@NonNull Context context) {
        this(context, 0)
    }

    protected constructor(@NonNull Context context, @StyleRes Int i) {
        super(context, resolveDialogTheme(context, i))
        this.mAlert = AlertController(getContext(), this, getWindow())
    }

    protected constructor(@NonNull Context context, Boolean z, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0)
        setCancelable(z)
        setOnCancelListener(onCancelListener)
    }

    static Int resolveDialogTheme(@NonNull Context context, @StyleRes Int i) {
        if ((i >>> 24) > 0) {
            return i
        }
        TypedValue typedValue = TypedValue()
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, typedValue, true)
        return typedValue.resourceId
    }

    fun getButton(Int i) {
        return this.mAlert.getButton(i)
    }

    fun getListView() {
        return this.mAlert.getListView()
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    protected fun onCreate(Bundle bundle) {
        super.onCreate(bundle)
        this.mAlert.installContent()
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    fun onKeyDown(Int i, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(i, keyEvent)) {
            return true
        }
        return super.onKeyDown(i, keyEvent)
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    fun onKeyUp(Int i, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(i, keyEvent)) {
            return true
        }
        return super.onKeyUp(i, keyEvent)
    }

    fun setButton(Int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null, null)
    }

    fun setButton(Int i, CharSequence charSequence, Drawable drawable, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(i, charSequence, onClickListener, null, drawable)
    }

    fun setButton(Int i, CharSequence charSequence, Message message) {
        this.mAlert.setButton(i, charSequence, null, message, null)
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Unit setButtonPanelLayoutHint(Int i) {
        this.mAlert.setButtonPanelLayoutHint(i)
    }

    fun setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view)
    }

    fun setIcon(Int i) {
        this.mAlert.setIcon(i)
    }

    fun setIcon(Drawable drawable) {
        this.mAlert.setIcon(drawable)
    }

    fun setIconAttribute(Int i) {
        TypedValue typedValue = TypedValue()
        getContext().getTheme().resolveAttribute(i, typedValue, true)
        this.mAlert.setIcon(typedValue.resourceId)
    }

    fun setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence)
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    fun setTitle(CharSequence charSequence) {
        super.setTitle(charSequence)
        this.mAlert.setTitle(charSequence)
    }

    fun setView(View view) {
        this.mAlert.setView(view)
    }

    fun setView(View view, Int i, Int i2, Int i3, Int i4) {
        this.mAlert.setView(view, i, i2, i3, i4)
    }
}
