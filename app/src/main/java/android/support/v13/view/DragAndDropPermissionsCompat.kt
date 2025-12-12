package android.support.v13.view

import android.app.Activity
import android.os.Build
import android.support.annotation.Nullable
import android.support.annotation.RestrictTo
import android.view.DragAndDropPermissions
import android.view.DragEvent

class DragAndDropPermissionsCompat {
    private Object mDragAndDropPermissions

    private constructor(Object obj) {
        this.mDragAndDropPermissions = obj
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    fun request(Activity activity, DragEvent dragEvent) {
        DragAndDropPermissions dragAndDropPermissionsRequestDragAndDropPermissions
        if (Build.VERSION.SDK_INT < 24 || (dragAndDropPermissionsRequestDragAndDropPermissions = activity.requestDragAndDropPermissions(dragEvent)) == null) {
            return null
        }
        return DragAndDropPermissionsCompat(dragAndDropPermissionsRequestDragAndDropPermissions)
    }

    public final Unit release() {
        if (Build.VERSION.SDK_INT >= 24) {
            ((DragAndDropPermissions) this.mDragAndDropPermissions).release()
        }
    }
}
