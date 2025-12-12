package android.support.v7.widget

import android.app.PendingIntent
import android.app.SearchableInfo
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.support.v4.view.AbsSavedState
import androidx.core.view.ViewCompat
import androidx.cursoradapter.widget.CursorAdapter
import androidx.appcompat.R
import android.support.v7.view.CollapsibleActionView
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.WeakHashMap

class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    static val DBG = false
    static val HIDDEN_METHOD_INVOKER = AutoCompleteTextViewReflector()
    private static val IME_OPTION_NO_MICROPHONE = "nm"
    static val LOG_TAG = "SearchView"
    private Bundle mAppSearchData
    private Boolean mClearingFocus
    final ImageView mCloseButton
    private final ImageView mCollapsedIcon
    private Int mCollapsedImeOptions
    private final CharSequence mDefaultQueryHint
    private final View mDropDownAnchor
    private Boolean mExpandedInActionView
    final ImageView mGoButton
    private Boolean mIconified
    private Boolean mIconifiedByDefault
    private Int mMaxWidth
    private CharSequence mOldQueryText
    private final View.OnClickListener mOnClickListener
    private OnCloseListener mOnCloseListener
    private final TextView.OnEditorActionListener mOnEditorActionListener
    private final AdapterView.OnItemClickListener mOnItemClickListener
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener
    private OnQueryTextListener mOnQueryChangeListener
    View.OnFocusChangeListener mOnQueryTextFocusChangeListener
    private View.OnClickListener mOnSearchClickListener
    private OnSuggestionListener mOnSuggestionListener
    private final WeakHashMap mOutsideDrawablesCache
    private CharSequence mQueryHint
    private Boolean mQueryRefinement
    private Runnable mReleaseCursorRunnable
    final ImageView mSearchButton
    private final View mSearchEditFrame
    private final Drawable mSearchHintIcon
    private final View mSearchPlate
    final SearchAutoComplete mSearchSrcTextView
    private Rect mSearchSrcTextViewBounds
    private Rect mSearchSrtTextViewBoundsExpanded
    SearchableInfo mSearchable
    private final View mSubmitArea
    private Boolean mSubmitButtonEnabled
    private final Int mSuggestionCommitIconResId
    private final Int mSuggestionRowLayout
    CursorAdapter mSuggestionsAdapter
    private Array<Int> mTemp
    private Array<Int> mTemp2
    View.OnKeyListener mTextKeyListener
    private TextWatcher mTextWatcher
    private UpdatableTouchDelegate mTouchDelegate
    private final Runnable mUpdateDrawableStateRunnable
    private CharSequence mUserQuery
    private final Intent mVoiceAppSearchIntent
    final ImageView mVoiceButton
    private Boolean mVoiceButtonEnabled
    private final Intent mVoiceWebSearchIntent

    class AutoCompleteTextViewReflector {
        private Method doAfterTextChanged
        private Method doBeforeTextChanged
        private Method ensureImeVisible
        private Method showSoftInputUnchecked

        AutoCompleteTextViewReflector() {
            try {
                this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0])
                this.doBeforeTextChanged.setAccessible(true)
            } catch (NoSuchMethodException e) {
            }
            try {
                this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0])
                this.doAfterTextChanged.setAccessible(true)
            } catch (NoSuchMethodException e2) {
            }
            try {
                this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE)
                this.ensureImeVisible.setAccessible(true)
            } catch (NoSuchMethodException e3) {
            }
        }

        Unit doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (this.doAfterTextChanged != null) {
                try {
                    this.doAfterTextChanged.invoke(autoCompleteTextView, new Object[0])
                } catch (Exception e) {
                }
            }
        }

        Unit doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (this.doBeforeTextChanged != null) {
                try {
                    this.doBeforeTextChanged.invoke(autoCompleteTextView, new Object[0])
                } catch (Exception e) {
                }
            }
        }

        Unit ensureImeVisible(AutoCompleteTextView autoCompleteTextView, Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (this.ensureImeVisible != null) {
                try {
                    this.ensureImeVisible.invoke(autoCompleteTextView, Boolean.valueOf(z))
                } catch (Exception e) {
                }
            }
        }
    }

    public interface OnCloseListener {
        Boolean onClose()
    }

    public interface OnQueryTextListener {
        Boolean onQueryTextChange(String str)

        Boolean onQueryTextSubmit(String str)
    }

    public interface OnSuggestionListener {
        Boolean onSuggestionClick(Int i)

        Boolean onSuggestionSelect(Int i)
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.ClassLoaderCreator() { // from class: android.support.v7.widget.SearchView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final SavedState createFromParcel(Parcel parcel) {
                return SavedState(parcel, null)
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return SavedState(parcel, classLoader)
            }

            @Override // android.os.Parcelable.Creator
            public final Array<SavedState> newArray(Int i) {
                return new SavedState[i]
            }
        }
        Boolean isIconified

        constructor(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader)
            this.isIconified = ((Boolean) parcel.readValue(null)).booleanValue()
        }

        SavedState(Parcelable parcelable) {
            super(parcelable)
        }

        fun toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}"
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        fun writeToParcel(Parcel parcel, Int i) {
            super.writeToParcel(parcel, i)
            parcel.writeValue(Boolean.valueOf(this.isIconified))
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        private Boolean mHasPendingShowSoftInputRequest
        final Runnable mRunShowSoftInputIfNecessary
        private SearchView mSearchView
        private Int mThreshold

        constructor(Context context) {
            this(context, null)
        }

        constructor(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle)
        }

        constructor(Context context, AttributeSet attributeSet, Int i) {
            super(context, attributeSet, i)
            this.mRunShowSoftInputIfNecessary = Runnable() { // from class: android.support.v7.widget.SearchView.SearchAutoComplete.1
                @Override // java.lang.Runnable
                fun run() {
                    SearchAutoComplete.this.showSoftInputIfNecessary()
                }
            }
            this.mThreshold = getThreshold()
        }

        private fun getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration()
            Int i = configuration.screenWidthDp
            Int i2 = configuration.screenHeightDp
            if (i < 960 || i2 < 720 || configuration.orientation != 2) {
                return (i >= 600 || (i >= 640 && i2 >= 480)) ? 192 : 160
            }
            return 256
        }

        @Override // android.widget.AutoCompleteTextView
        fun enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter()
        }

        Boolean isEmpty() {
            return TextUtils.getTrimmedLength(getText()) == 0
        }

        @Override // android.support.v7.widget.AppCompatAutoCompleteTextView, android.widget.TextView, android.view.View
        fun onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo)
            if (this.mHasPendingShowSoftInputRequest) {
                removeCallbacks(this.mRunShowSoftInputIfNecessary)
                post(this.mRunShowSoftInputIfNecessary)
            }
            return inputConnectionOnCreateInputConnection
        }

        @Override // android.view.View
        protected fun onFinishInflate() {
            super.onFinishInflate()
            setMinWidth((Int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()))
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        protected fun onFocusChanged(Boolean z, Int i, Rect rect) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.onFocusChanged(z, i, rect)
            this.mSearchView.onTextFocusChanged()
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        fun onKeyPreIme(Int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState()
                    if (keyDispatcherState == null) {
                        return true
                    }
                    keyDispatcherState.startTracking(keyEvent, this)
                    return true
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState()
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent)
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.mSearchView.clearFocus()
                        setImeVisibility(false)
                        return true
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent)
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        fun onWindowFocusChanged(Boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            super.onWindowFocusChanged(z)
            if (z && this.mSearchView.hasFocus() && getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true
                if (SearchView.isLandscapeMode(getContext())) {
                    SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true)
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView
        fun performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        protected fun replaceText(CharSequence charSequence) {
        }

        Unit setImeVisibility(Boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method")
            if (!z) {
                this.mHasPendingShowSoftInputRequest = false
                removeCallbacks(this.mRunShowSoftInputIfNecessary)
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0)
            } else {
                if (!inputMethodManager.isActive(this)) {
                    this.mHasPendingShowSoftInputRequest = true
                    return
                }
                this.mHasPendingShowSoftInputRequest = false
                removeCallbacks(this.mRunShowSoftInputIfNecessary)
                inputMethodManager.showSoftInput(this, 0)
            }
        }

        Unit setSearchView(SearchView searchView) {
            this.mSearchView = searchView
        }

        @Override // android.widget.AutoCompleteTextView
        fun setThreshold(Int i) {
            super.setThreshold(i)
            this.mThreshold = i
        }

        Unit showSoftInputIfNecessary() {
            if (this.mHasPendingShowSoftInputRequest) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0)
                this.mHasPendingShowSoftInputRequest = false
            }
        }
    }

    class UpdatableTouchDelegate extends TouchDelegate {
        private final Rect mActualBounds
        private Boolean mDelegateTargeted
        private final View mDelegateView
        private final Int mSlop
        private final Rect mSlopBounds
        private final Rect mTargetBounds

        constructor(Rect rect, Rect rect2, View view) {
            super(rect, view)
            this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop()
            this.mTargetBounds = Rect()
            this.mSlopBounds = Rect()
            this.mActualBounds = Rect()
            setBounds(rect, rect2)
            this.mDelegateView = view
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:4:0x0013  */
        @Override // android.view.TouchDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        fun onTouchEvent(android.view.MotionEvent r7) {
            /*
                r6 = this
                r1 = 1
                r0 = 0
                Float r2 = r7.getX()
                Int r3 = (Int) r2
                Float r2 = r7.getY()
                Int r4 = (Int) r2
                Int r2 = r7.getAction()
                switch(r2) {
                    case 0: goto L3c
                    case 1: goto L48
                    case 2: goto L48
                    case 3: goto L56
                    default: goto L13
                }
            L13:
                r2 = r0
            L14:
                if (r2 == 0) goto L3b
                if (r1 == 0) goto L5b
                android.graphics.Rect r0 = r6.mActualBounds
                Boolean r0 = r0.contains(r3, r4)
                if (r0 != 0) goto L5b
                android.view.View r0 = r6.mDelegateView
                Int r0 = r0.getWidth()
                Int r0 = r0 / 2
                Float r0 = (Float) r0
                android.view.View r1 = r6.mDelegateView
                Int r1 = r1.getHeight()
                Int r1 = r1 / 2
                Float r1 = (Float) r1
                r7.setLocation(r0, r1)
            L35:
                android.view.View r0 = r6.mDelegateView
                Boolean r0 = r0.dispatchTouchEvent(r7)
            L3b:
                return r0
            L3c:
                android.graphics.Rect r2 = r6.mTargetBounds
                Boolean r2 = r2.contains(r3, r4)
                if (r2 == 0) goto L13
                r6.mDelegateTargeted = r1
                r2 = r1
                goto L14
            L48:
                Boolean r2 = r6.mDelegateTargeted
                if (r2 == 0) goto L14
                android.graphics.Rect r5 = r6.mSlopBounds
                Boolean r5 = r5.contains(r3, r4)
                if (r5 != 0) goto L14
                r1 = r0
                goto L14
            L56:
                Boolean r2 = r6.mDelegateTargeted
                r6.mDelegateTargeted = r0
                goto L14
            L5b:
                android.graphics.Rect r0 = r6.mActualBounds
                Int r0 = r0.left
                Int r0 = r3 - r0
                Float r0 = (Float) r0
                android.graphics.Rect r1 = r6.mActualBounds
                Int r1 = r1.top
                Int r1 = r4 - r1
                Float r1 = (Float) r1
                r7.setLocation(r0, r1)
                goto L35
            */
            throw UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SearchView.UpdatableTouchDelegate.onTouchEvent(android.view.MotionEvent):Boolean")
        }

        fun setBounds(Rect rect, Rect rect2) {
            this.mTargetBounds.set(rect)
            this.mSlopBounds.set(rect)
            this.mSlopBounds.inset(-this.mSlop, -this.mSlop)
            this.mActualBounds.set(rect2)
        }
    }

    constructor(Context context) {
        this(context, null)
    }

    constructor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle)
    }

    constructor(Context context, AttributeSet attributeSet, Int i) {
        super(context, attributeSet, i)
        this.mSearchSrcTextViewBounds = Rect()
        this.mSearchSrtTextViewBoundsExpanded = Rect()
        this.mTemp = new Int[2]
        this.mTemp2 = new Int[2]
        this.mUpdateDrawableStateRunnable = Runnable() { // from class: android.support.v7.widget.SearchView.1
            @Override // java.lang.Runnable
            fun run() {
                SearchView.this.updateFocusedState()
            }
        }
        this.mReleaseCursorRunnable = Runnable() { // from class: android.support.v7.widget.SearchView.2
            @Override // java.lang.Runnable
            fun run() {
                if (SearchView.this.mSuggestionsAdapter == null || !(SearchView.this.mSuggestionsAdapter is SuggestionsAdapter)) {
                    return
                }
                SearchView.this.mSuggestionsAdapter.changeCursor(null)
            }
        }
        this.mOutsideDrawablesCache = WeakHashMap()
        this.mOnClickListener = new View.OnClickListener() { // from class: android.support.v7.widget.SearchView.5
            @Override // android.view.View.OnClickListener
            fun onClick(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                if (view == SearchView.this.mSearchButton) {
                    SearchView.this.onSearchClicked()
                    return
                }
                if (view == SearchView.this.mCloseButton) {
                    SearchView.this.onCloseClicked()
                    return
                }
                if (view == SearchView.this.mGoButton) {
                    SearchView.this.onSubmitQuery()
                } else if (view == SearchView.this.mVoiceButton) {
                    SearchView.this.onVoiceClicked()
                } else if (view == SearchView.this.mSearchSrcTextView) {
                    SearchView.this.forceSuggestionQuery()
                }
            }
        }
        this.mTextKeyListener = new View.OnKeyListener() { // from class: android.support.v7.widget.SearchView.6
            @Override // android.view.View.OnKeyListener
            fun onKey(View view, Int i2, KeyEvent keyEvent) {
                if (SearchView.this.mSearchable == null) {
                    return false
                }
                if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                    return SearchView.this.onSuggestionsKey(view, i2, keyEvent)
                }
                if (SearchView.this.mSearchSrcTextView.isEmpty() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i2 != 66) {
                    return false
                }
                view.cancelLongPress()
                SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString())
                return true
            }
        }
        this.mOnEditorActionListener = new TextView.OnEditorActionListener() { // from class: android.support.v7.widget.SearchView.7
            @Override // android.widget.TextView.OnEditorActionListener
            fun onEditorAction(TextView textView, Int i2, KeyEvent keyEvent) {
                SearchView.this.onSubmitQuery()
                return true
            }
        }
        this.mOnItemClickListener = new AdapterView.OnItemClickListener() { // from class: android.support.v7.widget.SearchView.8
            @Override // android.widget.AdapterView.OnItemClickListener
            fun onItemClick(AdapterView adapterView, View view, Int i2, Long j) {
                SearchView.this.onItemClicked(i2, 0, null)
            }
        }
        this.mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: android.support.v7.widget.SearchView.9
            @Override // android.widget.AdapterView.OnItemSelectedListener
            fun onItemSelected(AdapterView adapterView, View view, Int i2, Long j) {
                SearchView.this.onItemSelected(i2)
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            fun onNothingSelected(AdapterView adapterView) {
            }
        }
        this.mTextWatcher = TextWatcher() { // from class: android.support.v7.widget.SearchView.10
            @Override // android.text.TextWatcher
            fun afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            fun beforeTextChanged(CharSequence charSequence, Int i2, Int i3, Int i4) {
            }

            @Override // android.text.TextWatcher
            fun onTextChanged(CharSequence charSequence, Int i2, Int i3, Int i4) {
                SearchView.this.onTextChanged(charSequence)
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.SearchView, i, 0)
        LayoutInflater.from(context).inflate(tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), (ViewGroup) this, true)
        this.mSearchSrcTextView = (SearchAutoComplete) findViewById(R.id.search_src_text)
        this.mSearchSrcTextView.setSearchView(this)
        this.mSearchEditFrame = findViewById(R.id.search_edit_frame)
        this.mSearchPlate = findViewById(R.id.search_plate)
        this.mSubmitArea = findViewById(R.id.submit_area)
        this.mSearchButton = (ImageView) findViewById(R.id.search_button)
        this.mGoButton = (ImageView) findViewById(R.id.search_go_btn)
        this.mCloseButton = (ImageView) findViewById(R.id.search_close_btn)
        this.mVoiceButton = (ImageView) findViewById(R.id.search_voice_btn)
        this.mCollapsedIcon = (ImageView) findViewById(R.id.search_mag_icon)
        ViewCompat.setBackground(this.mSearchPlate, tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_queryBackground))
        ViewCompat.setBackground(this.mSubmitArea, tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_submitBackground))
        this.mSearchButton.setImageDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon))
        this.mGoButton.setImageDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_goIcon))
        this.mCloseButton.setImageDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_closeIcon))
        this.mVoiceButton.setImageDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_voiceIcon))
        this.mCollapsedIcon.setImageDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon))
        this.mSearchHintIcon = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.SearchView_searchHintIcon)
        TooltipCompat.setTooltipText(this.mSearchButton, getResources().getString(R.string.abc_searchview_description_search))
        this.mSuggestionRowLayout = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line)
        this.mSuggestionCommitIconResId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.SearchView_commitIcon, 0)
        this.mSearchButton.setOnClickListener(this.mOnClickListener)
        this.mCloseButton.setOnClickListener(this.mOnClickListener)
        this.mGoButton.setOnClickListener(this.mOnClickListener)
        this.mVoiceButton.setOnClickListener(this.mOnClickListener)
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener)
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher)
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener)
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener)
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener)
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener)
        this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: android.support.v7.widget.SearchView.3
            @Override // android.view.View.OnFocusChangeListener
            fun onFocusChange(View view, Boolean z) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, z)
                }
            }
        })
        setIconifiedByDefault(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchView_iconifiedByDefault, true))
        Int dimensionPixelSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1)
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize)
        }
        this.mDefaultQueryHint = tintTypedArrayObtainStyledAttributes.getText(R.styleable.SearchView_defaultQueryHint)
        this.mQueryHint = tintTypedArrayObtainStyledAttributes.getText(R.styleable.SearchView_queryHint)
        Int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.SearchView_android_imeOptions, -1)
        if (i2 != -1) {
            setImeOptions(i2)
        }
        Int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.SearchView_android_inputType, -1)
        if (i3 != -1) {
            setInputType(i3)
        }
        setFocusable(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchView_android_focusable, true))
        tintTypedArrayObtainStyledAttributes.recycle()
        this.mVoiceWebSearchIntent = Intent("android.speech.action.WEB_SEARCH")
        this.mVoiceWebSearchIntent.addFlags(268435456)
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search")
        this.mVoiceAppSearchIntent = Intent("android.speech.action.RECOGNIZE_SPEECH")
        this.mVoiceAppSearchIntent.addFlags(268435456)
        this.mDropDownAnchor = findViewById(this.mSearchSrcTextView.getDropDownAnchor())
        if (this.mDropDownAnchor != null) {
            this.mDropDownAnchor.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: android.support.v7.widget.SearchView.4
                @Override // android.view.View.OnLayoutChangeListener
                fun onLayoutChange(View view, Int i4, Int i5, Int i6, Int i7, Int i8, Int i9, Int i10, Int i11) {
                    SearchView.this.adjustDropDownSizeAndPosition()
                }
            })
        }
        updateViewsVisibility(this.mIconifiedByDefault)
        updateQueryHint()
    }

    private fun createIntent(String str, Uri uri, String str2, String str3, Int i, String str4) {
        Intent intent = Intent(str)
        intent.addFlags(268435456)
        if (uri != null) {
            intent.setData(uri)
        }
        intent.putExtra("user_query", this.mUserQuery)
        if (str3 != null) {
            intent.putExtra("query", str3)
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2)
        }
        if (this.mAppSearchData != null) {
            intent.putExtra("app_data", this.mAppSearchData)
        }
        if (i != 0) {
            intent.putExtra("action_key", i)
            intent.putExtra("action_msg", str4)
        }
        intent.setComponent(this.mSearchable.getSearchActivity())
        return intent
    }

    private fun createIntentFromSuggestion(Cursor cursor, Int i, String str) {
        Int position
        String columnString
        try {
            String columnString2 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_action")
            if (columnString2 == null) {
                columnString2 = this.mSearchable.getSuggestIntentAction()
            }
            if (columnString2 == null) {
                columnString2 = "android.intent.action.SEARCH"
            }
            String columnString3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data")
            if (columnString3 == null) {
                columnString3 = this.mSearchable.getSuggestIntentData()
            }
            if (columnString3 != null && (columnString = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data_id")) != null) {
                columnString3 = columnString3 + "/" + Uri.encode(columnString)
            }
            return createIntent(columnString2, columnString3 == null ? null : Uri.parse(columnString3), SuggestionsAdapter.getColumnString(cursor, "suggest_intent_extra_data"), SuggestionsAdapter.getColumnString(cursor, "suggest_intent_query"), i, str)
        } catch (RuntimeException e) {
            try {
                position = cursor.getPosition()
            } catch (RuntimeException e2) {
                position = -1
            }
            Log.w(LOG_TAG, "Search suggestions cursor at row " + position + " returned exception.", e)
            return null
        }
    }

    private fun createVoiceAppSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity()
        Intent intent2 = Intent("android.intent.action.SEARCH")
        intent2.setComponent(searchActivity)
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824)
        Bundle bundle = Bundle()
        if (this.mAppSearchData != null) {
            bundle.putParcelable("app_data", this.mAppSearchData)
        }
        Intent intent3 = Intent(intent)
        Resources resources = getResources()
        String string = searchableInfo.getVoiceLanguageModeId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form"
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null
        Int voiceMaxResults = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", string)
        intent3.putExtra("android.speech.extra.PROMPT", string2)
        intent3.putExtra("android.speech.extra.LANGUAGE", string3)
        intent3.putExtra("android.speech.extra.MAX_RESULTS", voiceMaxResults)
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null)
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity)
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle)
        return intent3
    }

    private fun createVoiceWebSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        Intent intent2 = Intent(intent)
        ComponentName searchActivity = searchableInfo.getSearchActivity()
        intent2.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString())
        return intent2
    }

    private fun dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown()
    }

    private fun getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp)
        getLocationInWindow(this.mTemp2)
        Int i = this.mTemp[1] - this.mTemp2[1]
        Int i2 = this.mTemp[0] - this.mTemp2[0]
        rect.set(i2, i, view.getWidth() + i2, view.getHeight() + i)
    }

    private fun getDecoratedHint(CharSequence charSequence) {
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return charSequence
        }
        Int textSize = (Int) (this.mSearchSrcTextView.getTextSize() * 1.25d)
        this.mSearchHintIcon.setBounds(0, 0, textSize, textSize)
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder("   ")
        spannableStringBuilder.setSpan(ImageSpan(this.mSearchHintIcon), 1, 2, 33)
        spannableStringBuilder.append(charSequence)
        return spannableStringBuilder
    }

    private fun getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height)
    }

    private fun getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width)
    }

    private fun hasVoiceSearch() {
        if (this.mSearchable == null || !this.mSearchable.getVoiceSearchEnabled()) {
            return false
        }
        Intent intent = null
        if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
            intent = this.mVoiceWebSearchIntent
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            intent = this.mVoiceAppSearchIntent
        }
        return (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true
    }

    static Boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2
    }

    private fun isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified()
    }

    private fun launchIntent(Intent intent) {
        if (intent == null) {
            return
        }
        try {
            getContext().startActivity(intent)
        } catch (RuntimeException e) {
            Log.e(LOG_TAG, "Failed launch activity: " + intent, e)
        }
    }

    private fun launchSuggestion(Int i, Int i2, String str) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor()
        if (cursor == null || !cursor.moveToPosition(i)) {
            return false
        }
        launchIntent(createIntentFromSuggestion(cursor, i2, str))
        return true
    }

    private fun postUpdateFocusedState() {
        post(this.mUpdateDrawableStateRunnable)
    }

    private fun rewriteQueryFromSuggestion(Int i) {
        Editable text = this.mSearchSrcTextView.getText()
        Cursor cursor = this.mSuggestionsAdapter.getCursor()
        if (cursor == null) {
            return
        }
        if (!cursor.moveToPosition(i)) {
            setQuery(text)
            return
        }
        CharSequence charSequenceConvertToString = this.mSuggestionsAdapter.convertToString(cursor)
        if (charSequenceConvertToString != null) {
            setQuery(charSequenceConvertToString)
        } else {
            setQuery(text)
        }
    }

    private fun setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence)
        this.mSearchSrcTextView.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length())
    }

    private fun updateCloseButton() {
        Boolean z = true
        Boolean z2 = !TextUtils.isEmpty(this.mSearchSrcTextView.getText())
        if (!z2 && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
            z = false
        }
        this.mCloseButton.setVisibility(z ? 0 : 8)
        Drawable drawable = this.mCloseButton.getDrawable()
        if (drawable != null) {
            drawable.setState(z2 ? ENABLED_STATE_SET : EMPTY_STATE_SET)
        }
    }

    private fun updateQueryHint() {
        CharSequence queryHint = getQueryHint()
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView
        if (queryHint == null) {
            queryHint = ""
        }
        searchAutoComplete.setHint(getDecoratedHint(queryHint))
    }

    private fun updateSearchAutoComplete() {
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold())
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions())
        Int inputType = this.mSearchable.getInputType()
        if ((inputType & 15) == 1) {
            inputType &= -65537
            if (this.mSearchable.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288
            }
        }
        this.mSearchSrcTextView.setInputType(inputType)
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null)
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache)
            this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter)
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1)
        }
    }

    private fun updateSubmitArea() {
        Int i = 8
        if (isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
            i = 0
        }
        this.mSubmitArea.setVisibility(i)
    }

    private fun updateSubmitButton(Boolean z) {
        Int i = 8
        if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (z || !this.mVoiceButtonEnabled)) {
            i = 0
        }
        this.mGoButton.setVisibility(i)
    }

    private fun updateViewsVisibility(Boolean z) {
        Int i = 8
        this.mIconified = z
        Int i2 = z ? 0 : 8
        Boolean z2 = !TextUtils.isEmpty(this.mSearchSrcTextView.getText())
        this.mSearchButton.setVisibility(i2)
        updateSubmitButton(z2)
        this.mSearchEditFrame.setVisibility(z ? 8 : 0)
        if (this.mCollapsedIcon.getDrawable() != null && !this.mIconifiedByDefault) {
            i = 0
        }
        this.mCollapsedIcon.setVisibility(i)
        updateCloseButton()
        updateVoiceButton(z2 ? false : true)
        updateSubmitArea()
    }

    private fun updateVoiceButton(Boolean z) {
        Int i
        if (this.mVoiceButtonEnabled && !isIconified() && z) {
            i = 0
            this.mGoButton.setVisibility(8)
        } else {
            i = 8
        }
        this.mVoiceButton.setVisibility(i)
    }

    Unit adjustDropDownSizeAndPosition() {
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources resources = getContext().getResources()
            Int paddingLeft = this.mSearchPlate.getPaddingLeft()
            Rect rect = Rect()
            Boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this)
            Int dimensionPixelSize = this.mIconifiedByDefault ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) : 0
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect)
            this.mSearchSrcTextView.setDropDownHorizontalOffset(zIsLayoutRtl ? -rect.left : paddingLeft - (rect.left + dimensionPixelSize))
            this.mSearchSrcTextView.setDropDownWidth((dimensionPixelSize + ((this.mDropDownAnchor.getWidth() + rect.left) + rect.right)) - paddingLeft)
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    fun clearFocus() {
        this.mClearingFocus = true
        super.clearFocus()
        this.mSearchSrcTextView.clearFocus()
        this.mSearchSrcTextView.setImeVisibility(false)
        this.mClearingFocus = false
    }

    Unit forceSuggestionQuery() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView)
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView)
    }

    fun getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions()
    }

    fun getInputType() {
        return this.mSearchSrcTextView.getInputType()
    }

    fun getMaxWidth() {
        return this.mMaxWidth
    }

    fun getQuery() {
        return this.mSearchSrcTextView.getText()
    }

    @Nullable
    fun getQueryHint() {
        return this.mQueryHint != null ? this.mQueryHint : (this.mSearchable == null || this.mSearchable.getHintId() == 0) ? this.mDefaultQueryHint : getContext().getText(this.mSearchable.getHintId())
    }

    Int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId
    }

    Int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout
    }

    fun getSuggestionsAdapter() {
        return this.mSuggestionsAdapter
    }

    fun isIconfiedByDefault() {
        return this.mIconifiedByDefault
    }

    fun isIconified() {
        return this.mIconified
    }

    fun isQueryRefinementEnabled() {
        return this.mQueryRefinement
    }

    fun isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled
    }

    Unit launchQuerySearch(Int i, String str, String str2) {
        getContext().startActivity(createIntent("android.intent.action.SEARCH", null, null, str2, i, str))
    }

    @Override // android.support.v7.view.CollapsibleActionView
    fun onActionViewCollapsed() {
        setQuery("", false)
        clearFocus()
        updateViewsVisibility(true)
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions)
        this.mExpandedInActionView = false
    }

    @Override // android.support.v7.view.CollapsibleActionView
    fun onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return
        }
        this.mExpandedInActionView = true
        this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions()
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 33554432)
        this.mSearchSrcTextView.setText("")
        setIconified(false)
    }

    Unit onCloseClicked() {
        if (!TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            this.mSearchSrcTextView.setText("")
            this.mSearchSrcTextView.requestFocus()
            this.mSearchSrcTextView.setImeVisibility(true)
        } else if (this.mIconifiedByDefault) {
            if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
                clearFocus()
                updateViewsVisibility(true)
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected fun onDetachedFromWindow() {
        removeCallbacks(this.mUpdateDrawableStateRunnable)
        post(this.mReleaseCursorRunnable)
        super.onDetachedFromWindow()
    }

    Boolean onItemClicked(Int i, Int i2, String str) {
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick(i)) {
            return false
        }
        launchSuggestion(i, 0, null)
        this.mSearchSrcTextView.setImeVisibility(false)
        dismissSuggestions()
        return true
    }

    Boolean onItemSelected(Int i) {
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionSelect(i)) {
            return false
        }
        rewriteQueryFromSuggestion(i)
        return true
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    protected fun onLayout(Boolean z, Int i, Int i2, Int i3, Int i4) {
        super.onLayout(z, i, i2, i3, i4)
        if (z) {
            getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds)
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, i4 - i2)
            if (this.mTouchDelegate != null) {
                this.mTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds)
            } else {
                this.mTouchDelegate = UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView)
                setTouchDelegate(this.mTouchDelegate)
            }
        }
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.View
    protected fun onMeasure(Int i, Int i2) {
        if (isIconified()) {
            super.onMeasure(i, i2)
            return
        }
        Int mode = View.MeasureSpec.getMode(i)
        Int size = View.MeasureSpec.getSize(i)
        switch (mode) {
            case Integer.MIN_VALUE:
                if (this.mMaxWidth <= 0) {
                    size = Math.min(getPreferredWidth(), size)
                    break
                } else {
                    size = Math.min(this.mMaxWidth, size)
                    break
                }
            case 0:
                if (this.mMaxWidth <= 0) {
                    size = getPreferredWidth()
                    break
                } else {
                    size = this.mMaxWidth
                    break
                }
            case 1073741824:
                if (this.mMaxWidth > 0) {
                    size = Math.min(this.mMaxWidth, size)
                    break
                }
                break
        }
        Int mode2 = View.MeasureSpec.getMode(i2)
        Int size2 = View.MeasureSpec.getSize(i2)
        switch (mode2) {
            case Integer.MIN_VALUE:
                size2 = Math.min(getPreferredHeight(), size2)
                break
            case 0:
                size2 = getPreferredHeight()
                break
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824))
    }

    Unit onQueryRefine(CharSequence charSequence) {
        setQuery(charSequence)
    }

    @Override // android.view.View
    protected fun onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable is SavedState)) {
            super.onRestoreInstanceState(parcelable)
            return
        }
        SavedState savedState = (SavedState) parcelable
        super.onRestoreInstanceState(savedState.getSuperState())
        updateViewsVisibility(savedState.isIconified)
        requestLayout()
    }

    @Override // android.view.View
    protected fun onSaveInstanceState() {
        SavedState savedState = SavedState(super.onSaveInstanceState())
        savedState.isIconified = isIconified()
        return savedState
    }

    Unit onSearchClicked() {
        updateViewsVisibility(false)
        this.mSearchSrcTextView.requestFocus()
        this.mSearchSrcTextView.setImeVisibility(true)
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick(this)
        }
    }

    Unit onSubmitQuery() {
        Editable text = this.mSearchSrcTextView.getText()
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return
        }
        if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit(text.toString())) {
            if (this.mSearchable != null) {
                launchQuerySearch(0, null, text.toString())
            }
            this.mSearchSrcTextView.setImeVisibility(false)
            dismissSuggestions()
        }
    }

    Boolean onSuggestionsKey(View view, Int i, KeyEvent keyEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mSearchable == null || this.mSuggestionsAdapter == null || keyEvent.getAction() != 0 || !keyEvent.hasNoModifiers()) {
            return false
        }
        if (i == 66 || i == 84 || i == 61) {
            return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null)
        }
        if (i != 21 && i != 22) {
            if (i != 19 || this.mSearchSrcTextView.getListSelection() != 0) {
            }
            return false
        }
        this.mSearchSrcTextView.setSelection(i == 21 ? 0 : this.mSearchSrcTextView.length())
        this.mSearchSrcTextView.setListSelection(0)
        this.mSearchSrcTextView.clearListSelection()
        HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true)
        return true
    }

    Unit onTextChanged(CharSequence charSequence) {
        Editable text = this.mSearchSrcTextView.getText()
        this.mUserQuery = text
        Boolean z = !TextUtils.isEmpty(text)
        updateSubmitButton(z)
        updateVoiceButton(z ? false : true)
        updateCloseButton()
        updateSubmitArea()
        if (this.mOnQueryChangeListener != null && !TextUtils.equals(charSequence, this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString())
        }
        this.mOldQueryText = charSequence.toString()
    }

    Unit onTextFocusChanged() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        updateViewsVisibility(isIconified())
        postUpdateFocusedState()
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery()
        }
    }

    Unit onVoiceClicked() {
        if (this.mSearchable == null) {
            return
        }
        SearchableInfo searchableInfo = this.mSearchable
        try {
            if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                getContext().startActivity(createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo))
            } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                getContext().startActivity(createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo))
            }
        } catch (ActivityNotFoundException e) {
            Log.w(LOG_TAG, "Could not find voice search activity")
        }
    }

    @Override // android.view.View
    fun onWindowFocusChanged(Boolean z) {
        super.onWindowFocusChanged(z)
        postUpdateFocusedState()
    }

    @Override // android.view.ViewGroup, android.view.View
    fun requestFocus(Int i, Rect rect) {
        if (this.mClearingFocus || !isFocusable()) {
            return false
        }
        if (isIconified()) {
            return super.requestFocus(i, rect)
        }
        Boolean zRequestFocus = this.mSearchSrcTextView.requestFocus(i, rect)
        if (zRequestFocus) {
            updateViewsVisibility(false)
        }
        return zRequestFocus
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle
    }

    fun setIconified(Boolean z) {
        if (z) {
            onCloseClicked()
        } else {
            onSearchClicked()
        }
    }

    fun setIconifiedByDefault(Boolean z) {
        if (this.mIconifiedByDefault == z) {
            return
        }
        this.mIconifiedByDefault = z
        updateViewsVisibility(z)
        updateQueryHint()
    }

    fun setImeOptions(Int i) {
        this.mSearchSrcTextView.setImeOptions(i)
    }

    fun setInputType(Int i) {
        this.mSearchSrcTextView.setInputType(i)
    }

    fun setMaxWidth(Int i) {
        this.mMaxWidth = i
        requestLayout()
    }

    fun setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener
    }

    fun setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener
    }

    fun setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener
    }

    fun setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener
    }

    fun setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener
    }

    fun setQuery(CharSequence charSequence, Boolean z) {
        this.mSearchSrcTextView.setText(charSequence)
        if (charSequence != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length())
            this.mUserQuery = charSequence
        }
        if (!z || TextUtils.isEmpty(charSequence)) {
            return
        }
        onSubmitQuery()
    }

    fun setQueryHint(@Nullable CharSequence charSequence) {
        this.mQueryHint = charSequence
        updateQueryHint()
    }

    fun setQueryRefinementEnabled(Boolean z) {
        this.mQueryRefinement = z
        if (this.mSuggestionsAdapter is SuggestionsAdapter) {
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(z ? 2 : 1)
        }
    }

    fun setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo
        if (this.mSearchable != null) {
            updateSearchAutoComplete()
            updateQueryHint()
        }
        this.mVoiceButtonEnabled = hasVoiceSearch()
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE)
        }
        updateViewsVisibility(isIconified())
    }

    fun setSubmitButtonEnabled(Boolean z) {
        this.mSubmitButtonEnabled = z
        updateViewsVisibility(isIconified())
    }

    fun setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter
        this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter)
    }

    Unit updateFocusedState() {
        Array<Int> iArr = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET
        Drawable background = this.mSearchPlate.getBackground()
        if (background != null) {
            background.setState(iArr)
        }
        Drawable background2 = this.mSubmitArea.getBackground()
        if (background2 != null) {
            background2.setState(iArr)
        }
        invalidate()
    }
}
