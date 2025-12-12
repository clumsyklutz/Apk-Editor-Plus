package com.gmail.heagoo.apkeditor

import android.content.Context
import androidx.core.app.NotificationCompat
import com.gmail.heagoo.apkeditor.pro.R
import java.util.HashMap

final class ds {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap f968a = HashMap()

    static String a(Context context, String str) {
        initMap(context)
        return (String) f968a.get(str)
    }

    static Unit initMap(Context context) {
        f968a.put("manifest", context.getResources().getString(R.string.mf_manifest))
        f968a.put("application", context.getResources().getString(R.string.mf_application))
        f968a.put("activity", context.getResources().getString(R.string.mf_activity))
        f968a.put("intent-filter", context.getResources().getString(R.string.mf_intent_filter))
        f968a.put("action", context.getResources().getString(R.string.mf_action))
        f968a.put("category", context.getResources().getString(R.string.mf_category))
        f968a.put("data", context.getResources().getString(R.string.mf_data))
        f968a.put("meta-data", context.getResources().getString(R.string.mf_meta_data))
        f968a.put("layout", context.getResources().getString(R.string.mf_layout))
        f968a.put("activity-alias", context.getResources().getString(R.string.mf_activity_alias))
        f968a.put(NotificationCompat.CATEGORY_SERVICE, context.getResources().getString(R.string.mf_service))
        f968a.put("receiver", context.getResources().getString(R.string.mf_receiver))
        f968a.put("profileable", context.getResources().getString(R.string.mf_profileable))
        f968a.put("provider", context.getResources().getString(R.string.mf_provider))
        f968a.put("grant-uri-permission", context.getResources().getString(R.string.mf_grant_uri_permission))
        f968a.put("path-permission", context.getResources().getString(R.string.mf_path_permission))
        f968a.put("uses-library", context.getResources().getString(R.string.mf_uses_library))
        f968a.put("uses-native-library", context.getResources().getString(R.string.mf_uses_native_library))
        f968a.put("compatible-screens", context.getResources().getString(R.string.mf_compatible_screens))
        f968a.put("instrumentation", context.getResources().getString(R.string.mf_instrumentation))
        f968a.put("permission", context.getResources().getString(R.string.mf_permission))
        f968a.put("permission-group", context.getResources().getString(R.string.mf_permission_group))
        f968a.put("permission-tree", context.getResources().getString(R.string.mf_permission_tree))
        f968a.put("queries", context.getResources().getString(R.string.mf_queries))
        f968a.put("supports-gl-texture", context.getResources().getString(R.string.mf_supports_gl_texture))
        f968a.put("supports-screens", context.getResources().getString(R.string.mf_supports_screens))
        f968a.put("uses-configuration", context.getResources().getString(R.string.mf_uses_configuration))
        f968a.put("uses-feature", context.getResources().getString(R.string.mf_uses_feature))
        f968a.put("uses-permission", context.getResources().getString(R.string.mf_uses_permission))
        f968a.put("uses-permission-sdk-23", context.getResources().getString(R.string.string_7f0702bc))
        f968a.put("uses-sdk", context.getResources().getString(R.string.mf_uses_sdk))
    }
}
