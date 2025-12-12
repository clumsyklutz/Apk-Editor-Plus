package android.support.v7.app

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.res.TypedArray
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.Nullable
import android.support.v4.provider.FontsContractCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.ViewStub
import android.view.Window
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.CursorAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import java.lang.ref.WeakReference

class AlertController {
    ListAdapter mAdapter
    private Int mAlertDialogLayout
    private final Int mButtonIconDimen
    Button mButtonNegative
    private Drawable mButtonNegativeIcon
    Message mButtonNegativeMessage
    private CharSequence mButtonNegativeText
    Button mButtonNeutral
    private Drawable mButtonNeutralIcon
    Message mButtonNeutralMessage
    private CharSequence mButtonNeutralText
    private Int mButtonPanelSideLayout
    Button mButtonPositive
    private Drawable mButtonPositiveIcon
    Message mButtonPositiveMessage
    private CharSequence mButtonPositiveText
    private final Context mContext
    private View mCustomTitleView
    final AppCompatDialog mDialog
    Handler mHandler
    private Drawable mIcon
    private ImageView mIconView
    Int mListItemLayout
    Int mListLayout
    ListView mListView
    private CharSequence mMessage
    private TextView mMessageView
    Int mMultiChoiceItemLayout
    NestedScrollView mScrollView
    private Boolean mShowTitle
    Int mSingleChoiceItemLayout
    private CharSequence mTitle
    private TextView mTitleView
    private View mView
    private Int mViewLayoutResId
    private Int mViewSpacingBottom
    private Int mViewSpacingLeft
    private Int mViewSpacingRight
    private Int mViewSpacingTop
    private final Window mWindow
    private Boolean mViewSpacingSpecified = false
    private Int mIconId = 0
    Int mCheckedItem = -1
    private Int mButtonPanelLayoutHint = 0
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() { // from class: android.support.v7.app.AlertController.1
        @Override // android.view.View.OnClickListener
        fun onClick(View view) {
            Message messageObtain = (view != AlertController.this.mButtonPositive || AlertController.this.mButtonPositiveMessage == null) ? (view != AlertController.this.mButtonNegative || AlertController.this.mButtonNegativeMessage == null) ? (view != AlertController.this.mButtonNeutral || AlertController.this.mButtonNeutralMessage == null) ? null : Message.obtain(AlertController.this.mButtonNeutralMessage) : Message.obtain(AlertController.this.mButtonNegativeMessage) : Message.obtain(AlertController.this.mButtonPositiveMessage)
            if (messageObtain != null) {
                messageObtain.sendToTarget()
            }
            AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialog).sendToTarget()
        }
    }

    class AlertParams {
        public ListAdapter mAdapter
        public Array<Boolean> mCheckedItems
        public final Context mContext
        public Cursor mCursor
        public View mCustomTitleView
        public Boolean mForceInverseBackground
        public Drawable mIcon
        public final LayoutInflater mInflater
        public String mIsCheckedColumn
        public Boolean mIsMultiChoice
        public Boolean mIsSingleChoice
        public Array<CharSequence> mItems
        public String mLabelColumn
        public CharSequence mMessage
        public Drawable mNegativeButtonIcon
        public DialogInterface.OnClickListener mNegativeButtonListener
        public CharSequence mNegativeButtonText
        public Drawable mNeutralButtonIcon
        public DialogInterface.OnClickListener mNeutralButtonListener
        public CharSequence mNeutralButtonText
        public DialogInterface.OnCancelListener mOnCancelListener
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener
        public DialogInterface.OnClickListener mOnClickListener
        public DialogInterface.OnDismissListener mOnDismissListener
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener
        public DialogInterface.OnKeyListener mOnKeyListener
        public OnPrepareListViewListener mOnPrepareListViewListener
        public Drawable mPositiveButtonIcon
        public DialogInterface.OnClickListener mPositiveButtonListener
        public CharSequence mPositiveButtonText
        public CharSequence mTitle
        public View mView
        public Int mViewLayoutResId
        public Int mViewSpacingBottom
        public Int mViewSpacingLeft
        public Int mViewSpacingRight
        public Int mViewSpacingTop
        public Int mIconId = 0
        public Int mIconAttrId = 0
        public Boolean mViewSpacingSpecified = false
        public Int mCheckedItem = -1
        public Boolean mRecycleOnMeasure = true
        public Boolean mCancelable = true

        public interface OnPrepareListViewListener {
            Unit onPrepareListView(ListView listView)
        }

        constructor(Context context) {
            this.mContext = context
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater")
        }

        private fun createListView(final AlertController alertController) {
            ListAdapter simpleCursorAdapter
            Int i = R.id.text1
            Boolean z = false
            val recycleListView = (RecycleListView) this.mInflater.inflate(alertController.mListLayout, (ViewGroup) null)
            if (this.mIsMultiChoice) {
                simpleCursorAdapter = this.mCursor == null ? ArrayAdapter(this.mContext, alertController.mMultiChoiceItemLayout, i, this.mItems) { // from class: android.support.v7.app.AlertController.AlertParams.1
                    @Override // android.widget.ArrayAdapter, android.widget.Adapter
                    fun getView(Int i2, View view, ViewGroup viewGroup) {
                        View view2 = super.getView(i2, view, viewGroup)
                        if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[i2]) {
                            recycleListView.setItemChecked(i2, true)
                        }
                        return view2
                    }
                } : CursorAdapter(this.mContext, this.mCursor, z) { // from class: android.support.v7.app.AlertController.AlertParams.2
                    private final Int mIsCheckedIndex
                    private final Int mLabelIndex

                    {
                        Cursor cursor = getCursor()
                        this.mLabelIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mLabelColumn)
                        this.mIsCheckedIndex = cursor.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn)
                    }

                    @Override // android.widget.CursorAdapter
                    fun bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.mLabelIndex))
                        recycleListView.setItemChecked(cursor.getPosition(), cursor.getInt(this.mIsCheckedIndex) == 1)
                    }

                    @Override // android.widget.CursorAdapter
                    fun newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false)
                    }
                }
            } else {
                Int i2 = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout
                simpleCursorAdapter = this.mCursor != null ? SimpleCursorAdapter(this.mContext, i2, this.mCursor, new Array<String>{this.mLabelColumn}, new Array<Int>{R.id.text1}) : this.mAdapter != null ? this.mAdapter : CheckedItemAdapter(this.mContext, i2, R.id.text1, this.mItems)
            }
            if (this.mOnPrepareListViewListener != null) {
                this.mOnPrepareListViewListener.onPrepareListView(recycleListView)
            }
            alertController.mAdapter = simpleCursorAdapter
            alertController.mCheckedItem = this.mCheckedItem
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.support.v7.app.AlertController.AlertParams.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    fun onItemClick(AdapterView adapterView, View view, Int i3, Long j) {
                        AlertParams.this.mOnClickListener.onClick(alertController.mDialog, i3)
                        if (AlertParams.this.mIsSingleChoice) {
                            return
                        }
                        alertController.mDialog.dismiss()
                    }
                })
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.support.v7.app.AlertController.AlertParams.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    fun onItemClick(AdapterView adapterView, View view, Int i3, Long j) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[i3] = recycleListView.isItemChecked(i3)
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialog, i3, recycleListView.isItemChecked(i3))
                    }
                })
            }
            if (this.mOnItemSelectedListener != null) {
                recycleListView.setOnItemSelectedListener(this.mOnItemSelectedListener)
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1)
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2)
            }
            alertController.mListView = recycleListView
        }

        fun apply(AlertController alertController) {
            if (this.mCustomTitleView != null) {
                alertController.setCustomTitle(this.mCustomTitleView)
            } else {
                if (this.mTitle != null) {
                    alertController.setTitle(this.mTitle)
                }
                if (this.mIcon != null) {
                    alertController.setIcon(this.mIcon)
                }
                if (this.mIconId != 0) {
                    alertController.setIcon(this.mIconId)
                }
                if (this.mIconAttrId != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(this.mIconAttrId))
                }
            }
            if (this.mMessage != null) {
                alertController.setMessage(this.mMessage)
            }
            if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null, this.mPositiveButtonIcon)
            }
            if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null, this.mNegativeButtonIcon)
            }
            if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null, this.mNeutralButtonIcon)
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                createListView(alertController)
            }
            if (this.mView == null) {
                if (this.mViewLayoutResId != 0) {
                    alertController.setView(this.mViewLayoutResId)
                }
            } else if (this.mViewSpacingSpecified) {
                alertController.setView(this.mView, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom)
            } else {
                alertController.setView(this.mView)
            }
        }
    }

    final class ButtonHandler extends Handler {
        private static val MSG_DISMISS_DIALOG = 1
        private WeakReference mDialog

        constructor(DialogInterface dialogInterface) {
            this.mDialog = WeakReference(dialogInterface)
        }

        @Override // android.os.Handler
        public final Unit handleMessage(Message message) {
            switch (message.what) {
                case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR /* -3 */:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) this.mDialog.get(), message.what)
                    break
                case 1:
                    ((DialogInterface) message.obj).dismiss()
                    break
            }
        }
    }

    class CheckedItemAdapter extends ArrayAdapter {
        constructor(Context context, Int i, Int i2, Array<CharSequence> charSequenceArr) {
            super(context, i, i2, charSequenceArr)
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        fun getItemId(Int i) {
            return i
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        fun hasStableIds() {
            return true
        }
    }

    class RecycleListView extends ListView {
        private final Int mPaddingBottomNoButtons
        private final Int mPaddingTopNoTitle

        constructor(Context context) {
            this(context, null)
        }

        constructor(Context context, AttributeSet attributeSet) {
            super(context, attributeSet)
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.appcompat.R.styleable.RecycleListView)
            this.mPaddingBottomNoButtons = typedArrayObtainStyledAttributes.getDimensionPixelOffset(android.support.v7.appcompat.R.styleable.RecycleListView_paddingBottomNoButtons, -1)
            this.mPaddingTopNoTitle = typedArrayObtainStyledAttributes.getDimensionPixelOffset(android.support.v7.appcompat.R.styleable.RecycleListView_paddingTopNoTitle, -1)
        }

        fun setHasDecor(Boolean z, Boolean z2) {
            if (z2 && z) {
                return
            }
            setPadding(getPaddingLeft(), z ? getPaddingTop() : this.mPaddingTopNoTitle, getPaddingRight(), z2 ? getPaddingBottom() : this.mPaddingBottomNoButtons)
        }
    }

    constructor(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context
        this.mDialog = appCompatDialog
        this.mWindow = window
        this.mHandler = ButtonHandler(appCompatDialog)
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, android.support.v7.appcompat.R.styleable.AlertDialog, android.support.v7.appcompat.R.attr.alertDialogStyle, 0)
        this.mAlertDialogLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_android_layout, 0)
        this.mButtonPanelSideLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_buttonPanelSideLayout, 0)
        this.mListLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_listLayout, 0)
        this.mMultiChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_multiChoiceItemLayout, 0)
        this.mSingleChoiceItemLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_singleChoiceItemLayout, 0)
        this.mListItemLayout = typedArrayObtainStyledAttributes.getResourceId(android.support.v7.appcompat.R.styleable.AlertDialog_listItemLayout, 0)
        this.mShowTitle = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.appcompat.R.styleable.AlertDialog_showTitle, true)
        this.mButtonIconDimen = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.AlertDialog_buttonIconDimen, 0)
        typedArrayObtainStyledAttributes.recycle()
        appCompatDialog.supportRequestWindowFeature(1)
    }

    static Boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true
        }
        if (!(view is ViewGroup)) {
            return false
        }
        ViewGroup viewGroup = (ViewGroup) view
        Int childCount = viewGroup.getChildCount()
        while (childCount > 0) {
            childCount--
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true
            }
        }
        return false
    }

    private fun centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams()
        layoutParams.gravity = 1
        layoutParams.weight = 0.5f
        button.setLayoutParams(layoutParams)
    }

    static Unit manageScrollIndicators(View view, View view2, View view3) {
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4)
        }
        if (view3 != null) {
            view3.setVisibility(view.canScrollVertically(1) ? 0 : 4)
        }
    }

    @Nullable
    private fun resolvePanel(@Nullable View view, @Nullable View view2) {
        if (view == null) {
            return (ViewGroup) (view2 is ViewStub ? ((ViewStub) view2).inflate() : view2)
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent()
            if (parent is ViewGroup) {
                ((ViewGroup) parent).removeView(view2)
            }
        }
        return (ViewGroup) (view is ViewStub ? ((ViewStub) view).inflate() : view)
    }

    private fun selectContentView() {
        if (this.mButtonPanelSideLayout != 0 && this.mButtonPanelLayoutHint == 1) {
            return this.mButtonPanelSideLayout
        }
        return this.mAlertDialogLayout
    }

    private fun setScrollIndicators(ViewGroup viewGroup, View view, Int i, Int i2) {
        val view2 = null
        val viewFindViewById = this.mWindow.findViewById(android.support.v7.appcompat.R.id.scrollIndicatorUp)
        View viewFindViewById2 = this.mWindow.findViewById(android.support.v7.appcompat.R.id.scrollIndicatorDown)
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, i, i2)
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById)
            }
            if (viewFindViewById2 != null) {
                viewGroup.removeView(viewFindViewById2)
                return
            }
            return
        }
        if (viewFindViewById != null && (i & 1) == 0) {
            viewGroup.removeView(viewFindViewById)
            viewFindViewById = null
        }
        if (viewFindViewById2 == null || (i & 2) != 0) {
            view2 = viewFindViewById2
        } else {
            viewGroup.removeView(viewFindViewById2)
        }
        if (viewFindViewById == null && view2 == null) {
            return
        }
        if (this.mMessage != null) {
            this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: android.support.v7.app.AlertController.2
                @Override // android.support.v4.widget.NestedScrollView.OnScrollChangeListener
                fun onScrollChange(NestedScrollView nestedScrollView, Int i3, Int i4, Int i5, Int i6) {
                    AlertController.manageScrollIndicators(nestedScrollView, viewFindViewById, view2)
                }
            })
            this.mScrollView.post(Runnable() { // from class: android.support.v7.app.AlertController.3
                @Override // java.lang.Runnable
                fun run() {
                    AlertController.manageScrollIndicators(AlertController.this.mScrollView, viewFindViewById, view2)
                }
            })
        } else {
            if (this.mListView != null) {
                this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: android.support.v7.app.AlertController.4
                    @Override // android.widget.AbsListView.OnScrollListener
                    fun onScroll(AbsListView absListView, Int i3, Int i4, Int i5) {
                        AlertController.manageScrollIndicators(absListView, viewFindViewById, view2)
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    fun onScrollStateChanged(AbsListView absListView, Int i3) {
                    }
                })
                this.mListView.post(Runnable() { // from class: android.support.v7.app.AlertController.5
                    @Override // java.lang.Runnable
                    fun run() {
                        AlertController.manageScrollIndicators(AlertController.this.mListView, viewFindViewById, view2)
                    }
                })
                return
            }
            if (viewFindViewById != null) {
                viewGroup.removeView(viewFindViewById)
            }
            if (view2 != null) {
                viewGroup.removeView(view2)
            }
        }
    }

    private fun setupButtons(ViewGroup viewGroup) {
        Int i
        this.mButtonPositive = (Button) viewGroup.findViewById(R.id.button1)
        this.mButtonPositive.setOnClickListener(this.mButtonHandler)
        if (TextUtils.isEmpty(this.mButtonPositiveText) && this.mButtonPositiveIcon == null) {
            this.mButtonPositive.setVisibility(8)
            i = 0
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText)
            if (this.mButtonPositiveIcon != null) {
                this.mButtonPositiveIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen)
                this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null)
            }
            this.mButtonPositive.setVisibility(0)
            i = 1
        }
        this.mButtonNegative = (Button) viewGroup.findViewById(R.id.button2)
        this.mButtonNegative.setOnClickListener(this.mButtonHandler)
        if (TextUtils.isEmpty(this.mButtonNegativeText) && this.mButtonNegativeIcon == null) {
            this.mButtonNegative.setVisibility(8)
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText)
            if (this.mButtonNegativeIcon != null) {
                this.mButtonNegativeIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen)
                this.mButtonNegative.setCompoundDrawables(this.mButtonNegativeIcon, null, null, null)
            }
            this.mButtonNegative.setVisibility(0)
            i |= 2
        }
        this.mButtonNeutral = (Button) viewGroup.findViewById(R.id.button3)
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler)
        if (TextUtils.isEmpty(this.mButtonNeutralText) && this.mButtonNeutralIcon == null) {
            this.mButtonNeutral.setVisibility(8)
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText)
            if (this.mButtonPositiveIcon != null) {
                this.mButtonPositiveIcon.setBounds(0, 0, this.mButtonIconDimen, this.mButtonIconDimen)
                this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null)
            }
            this.mButtonNeutral.setVisibility(0)
            i |= 4
        }
        if (shouldCenterSingleButton(this.mContext)) {
            if (i == 1) {
                centerButton(this.mButtonPositive)
            } else if (i == 2) {
                centerButton(this.mButtonNegative)
            } else if (i == 4) {
                centerButton(this.mButtonNeutral)
            }
        }
        if (i != 0) {
            return
        }
        viewGroup.setVisibility(8)
    }

    private fun setupContent(ViewGroup viewGroup) {
        this.mScrollView = (NestedScrollView) this.mWindow.findViewById(android.support.v7.appcompat.R.id.scrollView)
        this.mScrollView.setFocusable(false)
        this.mScrollView.setNestedScrollingEnabled(false)
        this.mMessageView = (TextView) viewGroup.findViewById(R.id.message)
        if (this.mMessageView == null) {
            return
        }
        if (this.mMessage != null) {
            this.mMessageView.setText(this.mMessage)
            return
        }
        this.mMessageView.setVisibility(8)
        this.mScrollView.removeView(this.mMessageView)
        if (this.mListView == null) {
            viewGroup.setVisibility(8)
            return
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent()
        Int iIndexOfChild = viewGroup2.indexOfChild(this.mScrollView)
        viewGroup2.removeViewAt(iIndexOfChild)
        viewGroup2.addView(this.mListView, iIndexOfChild, new ViewGroup.LayoutParams(-1, -1))
    }

    private fun setupCustomContent(ViewGroup viewGroup) {
        View viewInflate = this.mView != null ? this.mView : this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null
        Boolean z = viewInflate != null
        if (!z || !canTextInput(viewInflate)) {
            this.mWindow.setFlags(131072, 131072)
        }
        if (!z) {
            viewGroup.setVisibility(8)
            return
        }
        FrameLayout frameLayout = (FrameLayout) this.mWindow.findViewById(android.support.v7.appcompat.R.id.custom)
        frameLayout.addView(viewInflate, new ViewGroup.LayoutParams(-1, -1))
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom)
        }
        if (this.mListView != null) {
            ((LinearLayoutCompat.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f
        }
    }

    private fun setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            viewGroup.addView(this.mCustomTitleView, 0, new ViewGroup.LayoutParams(-1, -2))
            this.mWindow.findViewById(android.support.v7.appcompat.R.id.title_template).setVisibility(8)
            return
        }
        this.mIconView = (ImageView) this.mWindow.findViewById(R.id.icon)
        if (!(!TextUtils.isEmpty(this.mTitle)) || !this.mShowTitle) {
            this.mWindow.findViewById(android.support.v7.appcompat.R.id.title_template).setVisibility(8)
            this.mIconView.setVisibility(8)
            viewGroup.setVisibility(8)
            return
        }
        this.mTitleView = (TextView) this.mWindow.findViewById(android.support.v7.appcompat.R.id.alertTitle)
        this.mTitleView.setText(this.mTitle)
        if (this.mIconId != 0) {
            this.mIconView.setImageResource(this.mIconId)
        } else if (this.mIcon != null) {
            this.mIconView.setImageDrawable(this.mIcon)
        } else {
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom())
            this.mIconView.setVisibility(8)
        }
    }

    private fun setupView() {
        View viewFindViewById
        View viewFindViewById2
        View viewFindViewById3 = this.mWindow.findViewById(android.support.v7.appcompat.R.id.parentPanel)
        View viewFindViewById4 = viewFindViewById3.findViewById(android.support.v7.appcompat.R.id.topPanel)
        View viewFindViewById5 = viewFindViewById3.findViewById(android.support.v7.appcompat.R.id.contentPanel)
        View viewFindViewById6 = viewFindViewById3.findViewById(android.support.v7.appcompat.R.id.buttonPanel)
        ViewGroup viewGroup = (ViewGroup) viewFindViewById3.findViewById(android.support.v7.appcompat.R.id.customPanel)
        setupCustomContent(viewGroup)
        View viewFindViewById7 = viewGroup.findViewById(android.support.v7.appcompat.R.id.topPanel)
        View viewFindViewById8 = viewGroup.findViewById(android.support.v7.appcompat.R.id.contentPanel)
        View viewFindViewById9 = viewGroup.findViewById(android.support.v7.appcompat.R.id.buttonPanel)
        ViewGroup viewGroupResolvePanel = resolvePanel(viewFindViewById7, viewFindViewById4)
        ViewGroup viewGroupResolvePanel2 = resolvePanel(viewFindViewById8, viewFindViewById5)
        ViewGroup viewGroupResolvePanel3 = resolvePanel(viewFindViewById9, viewFindViewById6)
        setupContent(viewGroupResolvePanel2)
        setupButtons(viewGroupResolvePanel3)
        setupTitle(viewGroupResolvePanel)
        Boolean z = (viewGroup == null || viewGroup.getVisibility() == 8) ? false : true
        Boolean z2 = (viewGroupResolvePanel == null || viewGroupResolvePanel.getVisibility() == 8) ? false : true
        Boolean z3 = (viewGroupResolvePanel3 == null || viewGroupResolvePanel3.getVisibility() == 8) ? false : true
        if (!z3 && viewGroupResolvePanel2 != null && (viewFindViewById2 = viewGroupResolvePanel2.findViewById(android.support.v7.appcompat.R.id.textSpacerNoButtons)) != null) {
            viewFindViewById2.setVisibility(0)
        }
        if (z2) {
            if (this.mScrollView != null) {
                this.mScrollView.setClipToPadding(true)
            }
            View viewFindViewById10 = (this.mMessage == null && this.mListView == null) ? null : viewGroupResolvePanel.findViewById(android.support.v7.appcompat.R.id.titleDividerNoCustom)
            if (viewFindViewById10 != null) {
                viewFindViewById10.setVisibility(0)
            }
        } else if (viewGroupResolvePanel2 != null && (viewFindViewById = viewGroupResolvePanel2.findViewById(android.support.v7.appcompat.R.id.textSpacerNoTitle)) != null) {
            viewFindViewById.setVisibility(0)
        }
        if (this.mListView is RecycleListView) {
            ((RecycleListView) this.mListView).setHasDecor(z2, z3)
        }
        if (!z) {
            View view = this.mListView != null ? this.mListView : this.mScrollView
            if (view != null) {
                setScrollIndicators(viewGroupResolvePanel2, view, (z3 ? 2 : 0) | (z2 ? 1 : 0), 3)
            }
        }
        ListView listView = this.mListView
        if (listView == null || this.mAdapter == null) {
            return
        }
        listView.setAdapter(this.mAdapter)
        Int i = this.mCheckedItem
        if (i >= 0) {
            listView.setItemChecked(i, true)
            listView.setSelection(i)
        }
    }

    private fun shouldCenterSingleButton(Context context) {
        TypedValue typedValue = TypedValue()
        context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.alertDialogCenterButtons, typedValue, true)
        return typedValue.data != 0
    }

    fun getButton(Int i) {
        switch (i) {
            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR /* -3 */:
                return this.mButtonNeutral
            case -2:
                return this.mButtonNegative
            case -1:
                return this.mButtonPositive
            default:
                return null
        }
    }

    fun getIconAttributeResId(Int i) {
        TypedValue typedValue = TypedValue()
        this.mContext.getTheme().resolveAttribute(i, typedValue, true)
        return typedValue.resourceId
    }

    fun getListView() {
        return this.mListView
    }

    fun installContent() {
        this.mDialog.setContentView(selectContentView())
        setupView()
    }

    fun onKeyDown(Int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent)
    }

    fun onKeyUp(Int i, KeyEvent keyEvent) {
        return this.mScrollView != null && this.mScrollView.executeKeyEvent(keyEvent)
    }

    fun setButton(Int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener)
        }
        switch (i) {
            case FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_LOAD_ERROR /* -3 */:
                this.mButtonNeutralText = charSequence
                this.mButtonNeutralMessage = message
                this.mButtonNeutralIcon = drawable
                return
            case -2:
                this.mButtonNegativeText = charSequence
                this.mButtonNegativeMessage = message
                this.mButtonNegativeIcon = drawable
                return
            case -1:
                this.mButtonPositiveText = charSequence
                this.mButtonPositiveMessage = message
                this.mButtonPositiveIcon = drawable
                return
            default:
                throw IllegalArgumentException("Button does not exist")
        }
    }

    fun setButtonPanelLayoutHint(Int i) {
        this.mButtonPanelLayoutHint = i
    }

    fun setCustomTitle(View view) {
        this.mCustomTitleView = view
    }

    fun setIcon(Int i) {
        this.mIcon = null
        this.mIconId = i
        if (this.mIconView != null) {
            if (i == 0) {
                this.mIconView.setVisibility(8)
            } else {
                this.mIconView.setVisibility(0)
                this.mIconView.setImageResource(this.mIconId)
            }
        }
    }

    fun setIcon(Drawable drawable) {
        this.mIcon = drawable
        this.mIconId = 0
        if (this.mIconView != null) {
            if (drawable == null) {
                this.mIconView.setVisibility(8)
            } else {
                this.mIconView.setVisibility(0)
                this.mIconView.setImageDrawable(drawable)
            }
        }
    }

    fun setMessage(CharSequence charSequence) {
        this.mMessage = charSequence
        if (this.mMessageView != null) {
            this.mMessageView.setText(charSequence)
        }
    }

    fun setTitle(CharSequence charSequence) {
        this.mTitle = charSequence
        if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence)
        }
    }

    fun setView(Int i) {
        this.mView = null
        this.mViewLayoutResId = i
        this.mViewSpacingSpecified = false
    }

    fun setView(View view) {
        this.mView = view
        this.mViewLayoutResId = 0
        this.mViewSpacingSpecified = false
    }

    fun setView(View view, Int i, Int i2, Int i3, Int i4) {
        this.mView = view
        this.mViewLayoutResId = 0
        this.mViewSpacingSpecified = true
        this.mViewSpacingLeft = i
        this.mViewSpacingTop = i2
        this.mViewSpacingRight = i3
        this.mViewSpacingBottom = i4
    }
}
