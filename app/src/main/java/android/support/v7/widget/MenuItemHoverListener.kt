package android.support.v7.widget

import android.support.annotation.NonNull
import android.support.annotation.RestrictTo
import android.support.v7.view.menu.MenuBuilder
import android.view.MenuItem

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface MenuItemHoverListener {
    Unit onItemHoverEnter(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem)

    Unit onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem)
}
