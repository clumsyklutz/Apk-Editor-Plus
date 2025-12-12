package android.arch.lifecycle

import android.support.annotation.Nullable

public interface Observer {
    Unit onChanged(@Nullable Object obj)
}
