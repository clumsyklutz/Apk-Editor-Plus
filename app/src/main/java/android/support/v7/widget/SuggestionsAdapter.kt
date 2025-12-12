package android.support.v7.widget

import android.R
import android.app.SearchManager
import android.app.SearchableInfo
import android.content.ComponentName
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.support.v4.widget.ResourceCursorAdapter
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gmail.heagoo.apkeditor.gzd
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.List
import java.util.WeakHashMap

class SuggestionsAdapter extends ResourceCursorAdapter implements View.OnClickListener {
    private static val DBG = false
    static val INVALID_INDEX = -1
    private static val LOG_TAG = "SuggestionsAdapter"
    private static val QUERY_LIMIT = 50
    static val REFINE_ALL = 2
    static val REFINE_BY_ENTRY = 1
    static val REFINE_NONE = 0
    private Boolean mClosed
    private final Int mCommitIconResId
    private Int mFlagsCol
    private Int mIconName1Col
    private Int mIconName2Col
    private final WeakHashMap mOutsideDrawablesCache
    private final Context mProviderContext
    private Int mQueryRefinement
    private final SearchManager mSearchManager
    private final SearchView mSearchView
    private final SearchableInfo mSearchable
    private Int mText1Col
    private Int mText2Col
    private Int mText2UrlCol
    private ColorStateList mUrlColor

    final class ChildViewCache {
        public final ImageView mIcon1
        public final ImageView mIcon2
        public final ImageView mIconRefine
        public final TextView mText1
        public final TextView mText2

        constructor(View view) {
            this.mText1 = (TextView) view.findViewById(R.id.text1)
            this.mText2 = (TextView) view.findViewById(R.id.text2)
            this.mIcon1 = (ImageView) view.findViewById(R.id.icon1)
            this.mIcon2 = (ImageView) view.findViewById(R.id.icon2)
            this.mIconRefine = (ImageView) view.findViewById(android.support.v7.appcompat.R.id.edit_query)
        }
    }

    constructor(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), (Cursor) null, true)
        this.mClosed = false
        this.mQueryRefinement = 1
        this.mText1Col = -1
        this.mText2Col = -1
        this.mText2UrlCol = -1
        this.mIconName1Col = -1
        this.mIconName2Col = -1
        this.mFlagsCol = -1
        this.mSearchManager = (SearchManager) this.mContext.getSystemService("search")
        this.mSearchView = searchView
        this.mSearchable = searchableInfo
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId()
        this.mProviderContext = context
        this.mOutsideDrawablesCache = weakHashMap
    }

    private fun checkIconCache(String str) {
        Drawable.ConstantState constantState = (Drawable.ConstantState) this.mOutsideDrawablesCache.get(str)
        if (constantState == null) {
            return null
        }
        return constantState.newDrawable()
    }

    private fun formatUrl(CharSequence charSequence) {
        if (this.mUrlColor == null) {
            TypedValue typedValue = TypedValue()
            this.mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.textColorSearchUrl, typedValue, true)
            this.mUrlColor = this.mContext.getResources().getColorStateList(typedValue.resourceId)
        }
        SpannableString spannableString = SpannableString(charSequence)
        spannableString.setSpan(TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33)
        return spannableString
    }

    private fun getActivityIcon(ComponentName componentName) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = this.mContext.getPackageManager()
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128)
            Int iconResource = activityInfo.getIconResource()
            if (iconResource == 0) {
                return null
            }
            Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo)
            if (drawable != null) {
                return drawable
            }
            Log.w(LOG_TAG, "Invalid icon resource " + iconResource + " for " + componentName.flattenToShortString())
            return null
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(LOG_TAG, e.toString())
            return null
        }
    }

    private fun getActivityIconWithCache(ComponentName componentName) throws PackageManager.NameNotFoundException {
        String strFlattenToShortString = componentName.flattenToShortString()
        if (!this.mOutsideDrawablesCache.containsKey(strFlattenToShortString)) {
            Drawable activityIcon = getActivityIcon(componentName)
            this.mOutsideDrawablesCache.put(strFlattenToShortString, activityIcon == null ? null : activityIcon.getConstantState())
            return activityIcon
        }
        Drawable.ConstantState constantState = (Drawable.ConstantState) this.mOutsideDrawablesCache.get(strFlattenToShortString)
        if (constantState == null) {
            return null
        }
        return constantState.newDrawable(this.mProviderContext.getResources())
    }

    fun getColumnString(Cursor cursor, String str) {
        return getStringOrNull(cursor, cursor.getColumnIndex(str))
    }

    private fun getDefaultIcon1(Cursor cursor) throws PackageManager.NameNotFoundException {
        Drawable activityIconWithCache = getActivityIconWithCache(this.mSearchable.getSearchActivity())
        return activityIconWithCache != null ? activityIconWithCache : this.mContext.getPackageManager().getDefaultActivityIcon()
    }

    private fun getDrawable(Uri uri) throws IOException {
        try {
            if ("android.resource".equals(uri.getScheme())) {
                try {
                    return getDrawableFromResourceUri(uri)
                } catch (Resources.NotFoundException e) {
                    throw FileNotFoundException("Resource does not exist: " + uri)
                }
            }
            InputStream inputStreamOpenInputStream = this.mProviderContext.getContentResolver().openInputStream(uri)
            if (inputStreamOpenInputStream == null) {
                throw FileNotFoundException("Failed to open " + uri)
            }
            try {
                Drawable drawableCreateFromStream = Drawable.createFromStream(inputStreamOpenInputStream, null)
                try {
                    inputStreamOpenInputStream.close()
                    return drawableCreateFromStream
                } catch (IOException e2) {
                    Log.e(LOG_TAG, "Error closing icon stream for " + uri, e2)
                    return drawableCreateFromStream
                }
            } finally {
            }
        } catch (FileNotFoundException e3) {
            Log.w(LOG_TAG, "Icon not found: " + uri + ", " + e3.getMessage())
            return null
        }
        Log.w(LOG_TAG, "Icon not found: " + uri + ", " + e3.getMessage())
        return null
    }

    private fun getDrawableFromResourceValue(String str) throws NumberFormatException, IOException {
        if (str == null || str.isEmpty() || "0".equals(str)) {
            return null
        }
        try {
            Int i = Integer.parseInt(str)
            String str2 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + i
            Drawable drawableCheckIconCache = checkIconCache(str2)
            if (drawableCheckIconCache != null) {
                return drawableCheckIconCache
            }
            Drawable drawable = ContextCompat.getDrawable(this.mProviderContext, i)
            storeInIconCache(str2, drawable)
            return drawable
        } catch (Resources.NotFoundException e) {
            Log.w(LOG_TAG, "Icon resource not found: " + str)
            return null
        } catch (NumberFormatException e2) {
            Drawable drawableCheckIconCache2 = checkIconCache(str)
            if (drawableCheckIconCache2 != null) {
                return drawableCheckIconCache2
            }
            Drawable drawable2 = getDrawable(Uri.parse(str))
            storeInIconCache(str, drawable2)
            return drawable2
        }
    }

    private fun getIcon1(Cursor cursor) throws NumberFormatException, IOException {
        if (this.mIconName1Col == -1) {
            return null
        }
        Drawable drawableFromResourceValue = getDrawableFromResourceValue(cursor.getString(this.mIconName1Col))
        return drawableFromResourceValue == null ? getDefaultIcon1(cursor) : drawableFromResourceValue
    }

    private fun getIcon2(Cursor cursor) {
        if (this.mIconName2Col == -1) {
            return null
        }
        return getDrawableFromResourceValue(cursor.getString(this.mIconName2Col))
    }

    private fun getStringOrNull(Cursor cursor, Int i) {
        if (i == -1) {
            return null
        }
        try {
            return cursor.getString(i)
        } catch (Exception e) {
            Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", e)
            return null
        }
    }

    private fun setViewDrawable(ImageView imageView, Drawable drawable, Int i) {
        imageView.setImageDrawable(drawable)
        if (drawable == null) {
            imageView.setVisibility(i)
            return
        }
        imageView.setVisibility(0)
        drawable.setVisible(false, false)
        drawable.setVisible(true, false)
    }

    private fun setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence)
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8)
        } else {
            textView.setVisibility(0)
        }
    }

    private fun storeInIconCache(String str, Drawable drawable) {
        if (drawable != null) {
            this.mOutsideDrawablesCache.put(str, drawable.getConstantState())
        }
    }

    private fun updateSpinnerState(Cursor cursor) {
        Bundle extras = cursor != null ? cursor.getExtras() : null
        if (extras == null || extras.getBoolean("in_progress")) {
        }
    }

    @Override // android.support.v4.widget.CursorAdapter
    fun bindView(View view, Context context, Cursor cursor) {
        ChildViewCache childViewCache = (ChildViewCache) view.getTag()
        Int i = this.mFlagsCol != -1 ? cursor.getInt(this.mFlagsCol) : 0
        if (childViewCache.mText1 != null) {
            setViewText(childViewCache.mText1, getStringOrNull(cursor, this.mText1Col))
        }
        if (childViewCache.mText2 != null) {
            String stringOrNull = getStringOrNull(cursor, this.mText2UrlCol)
            CharSequence url = stringOrNull != null ? formatUrl(stringOrNull) : getStringOrNull(cursor, this.mText2Col)
            if (TextUtils.isEmpty(url)) {
                if (childViewCache.mText1 != null) {
                    childViewCache.mText1.setSingleLine(false)
                    childViewCache.mText1.setMaxLines(2)
                }
            } else if (childViewCache.mText1 != null) {
                childViewCache.mText1.setSingleLine(true)
                childViewCache.mText1.setMaxLines(1)
            }
            setViewText(childViewCache.mText2, url)
        }
        if (childViewCache.mIcon1 != null) {
            setViewDrawable(childViewCache.mIcon1, getIcon1(cursor), 4)
        }
        if (childViewCache.mIcon2 != null) {
            setViewDrawable(childViewCache.mIcon2, getIcon2(cursor), 8)
        }
        if (this.mQueryRefinement != 2 && (this.mQueryRefinement != 1 || (i & 1) == 0)) {
            childViewCache.mIconRefine.setVisibility(8)
            return
        }
        childViewCache.mIconRefine.setVisibility(0)
        childViewCache.mIconRefine.setTag(childViewCache.mText1.getText())
        childViewCache.mIconRefine.setOnClickListener(this)
    }

    @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
    fun changeCursor(Cursor cursor) {
        if (this.mClosed) {
            Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.")
            if (cursor != null) {
                cursor.close()
                return
            }
            return
        }
        try {
            super.changeCursor(cursor)
            if (cursor != null) {
                this.mText1Col = cursor.getColumnIndex("suggest_text_1")
                this.mText2Col = cursor.getColumnIndex("suggest_text_2")
                this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url")
                this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1")
                this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2")
                this.mFlagsCol = cursor.getColumnIndex("suggest_flags")
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "error changing cursor and caching columns", e)
        }
    }

    fun close() {
        changeCursor(null)
        this.mClosed = true
    }

    @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
    fun convertToString(Cursor cursor) {
        String columnString
        String columnString2
        if (cursor == null) {
            return null
        }
        String columnString3 = getColumnString(cursor, "suggest_intent_query")
        if (columnString3 != null) {
            return columnString3
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (columnString2 = getColumnString(cursor, "suggest_intent_data")) != null) {
            return columnString2
        }
        if (!this.mSearchable.shouldRewriteQueryFromText() || (columnString = getColumnString(cursor, "suggest_text_1")) == null) {
            return null
        }
        return columnString
    }

    Drawable getDrawableFromResourceUri(Uri uri) throws PackageManager.NameNotFoundException, NumberFormatException, FileNotFoundException {
        Int identifier
        String authority = uri.getAuthority()
        if (TextUtils.isEmpty(authority)) {
            throw FileNotFoundException("No authority: " + uri)
        }
        try {
            Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication(authority)
            List<String> pathSegments = uri.getPathSegments()
            if (pathSegments == null) {
                throw FileNotFoundException("No path: " + uri)
            }
            Int size = pathSegments.size()
            if (size == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0))
                } catch (NumberFormatException e) {
                    throw FileNotFoundException("Single path segment is not a resource ID: " + uri)
                }
            } else {
                if (size != 2) {
                    throw FileNotFoundException("More than two path segments: " + uri)
                }
                identifier = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority)
            }
            if (identifier == 0) {
                throw FileNotFoundException("No resource found for: " + uri)
            }
            return resourcesForApplication.getDrawable(identifier)
        } catch (PackageManager.NameNotFoundException e2) {
            throw FileNotFoundException("No package found for authority: " + uri)
        }
    }

    @Override // android.support.v4.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    fun getDropDownView(Int i, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup)
        } catch (RuntimeException e) {
            Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e)
            View viewNewDropDownView = newDropDownView(this.mContext, this.mCursor, viewGroup)
            if (viewNewDropDownView != null) {
                ((ChildViewCache) viewNewDropDownView.getTag()).mText1.setText(e.toString())
            }
            return viewNewDropDownView
        }
    }

    fun getQueryRefinement() {
        return this.mQueryRefinement
    }

    Cursor getSearchManagerSuggestions(SearchableInfo searchableInfo, String str, Int i) {
        String suggestAuthority
        Array<String> strArr
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null
        }
        Uri.Builder builderFragment = new Uri.Builder().scheme(gzd.COLUMN_CONTENT).authority(suggestAuthority).query("").fragment("")
        String suggestPath = searchableInfo.getSuggestPath()
        if (suggestPath != null) {
            builderFragment.appendEncodedPath(suggestPath)
        }
        builderFragment.appendPath("search_suggest_query")
        String suggestSelection = searchableInfo.getSuggestSelection()
        if (suggestSelection != null) {
            strArr = new Array<String>{str}
        } else {
            builderFragment.appendPath(str)
            strArr = null
        }
        if (i > 0) {
            builderFragment.appendQueryParameter("limit", String.valueOf(i))
        }
        return this.mContext.getContentResolver().query(builderFragment.build(), null, suggestSelection, strArr, null)
    }

    @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
    fun getView(Int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup)
        } catch (RuntimeException e) {
            Log.w(LOG_TAG, "Search suggestions cursor threw exception.", e)
            View viewNewView = newView(this.mContext, this.mCursor, viewGroup)
            if (viewNewView != null) {
                ((ChildViewCache) viewNewView.getTag()).mText1.setText(e.toString())
            }
            return viewNewView
        }
    }

    @Override // android.support.v4.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
    fun hasStableIds() {
        return false
    }

    @Override // android.support.v4.widget.ResourceCursorAdapter, android.support.v4.widget.CursorAdapter
    fun newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View viewNewView = super.newView(context, cursor, viewGroup)
        viewNewView.setTag(ChildViewCache(viewNewView))
        ((ImageView) viewNewView.findViewById(android.support.v7.appcompat.R.id.edit_query)).setImageResource(this.mCommitIconResId)
        return viewNewView
    }

    @Override // android.widget.BaseAdapter
    fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        updateSpinnerState(getCursor())
    }

    @Override // android.widget.BaseAdapter
    fun notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated()
        updateSpinnerState(getCursor())
    }

    @Override // android.view.View.OnClickListener
    fun onClick(View view) {
        Object tag = view.getTag()
        if (tag is CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence) tag)
        }
    }

    @Override // android.support.v4.widget.CursorAdapter, android.support.v4.widget.CursorFilter.CursorFilterClient
    fun runQueryOnBackgroundThread(CharSequence charSequence) {
        String string = charSequence == null ? "" : charSequence.toString()
        if (this.mSearchView.getVisibility() != 0 || this.mSearchView.getWindowVisibility() != 0) {
            return null
        }
        try {
            Cursor searchManagerSuggestions = getSearchManagerSuggestions(this.mSearchable, string, 50)
            if (searchManagerSuggestions != null) {
                searchManagerSuggestions.getCount()
                return searchManagerSuggestions
            }
        } catch (RuntimeException e) {
            Log.w(LOG_TAG, "Search suggestions query threw an exception.", e)
        }
        return null
    }

    fun setQueryRefinement(Int i) {
        this.mQueryRefinement = i
    }
}
