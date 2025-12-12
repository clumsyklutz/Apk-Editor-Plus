package android.support.v4.media

import android.support.annotation.RequiresApi
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.List

@RequiresApi(21)
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor

    static {
        try {
            sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class)
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace()
        }
    }

    ParceledListSliceAdapterApi21() {
    }

    static Object newInstance(List list) {
        try {
            return sConstructor.newInstance(list)
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace()
            return null
        }
    }
}
