package common.types

import android.os.Bundle
import java.io.Serializable
import java.util.HashMap
import java.util.Map

class ActivityState_V1 implements Serializable {
    private Map stringValues = HashMap()
    private Map objValues = HashMap()
    private Map intValues = HashMap()
    private Map boolValues = HashMap()

    fun getString(String str) {
        return (String) this.stringValues.get(str)
    }

    fun putBoolean(String str, Boolean z) {
        this.boolValues.put(str, Boolean.valueOf(z))
    }

    fun putInt(String str, Int i) {
        this.intValues.put(str, Integer.valueOf(i))
    }

    fun putSerializable(String str, Serializable serializable) {
        this.objValues.put(str, serializable)
    }

    fun putString(String str, String str2) {
        this.stringValues.put(str, str2)
    }

    fun toBundle(Bundle bundle) {
        for (Map.Entry entry : this.stringValues.entrySet()) {
            bundle.putString((String) entry.getKey(), (String) entry.getValue())
        }
        for (Map.Entry entry2 : this.intValues.entrySet()) {
            bundle.putInt((String) entry2.getKey(), ((Integer) entry2.getValue()).intValue())
        }
        for (Map.Entry entry3 : this.boolValues.entrySet()) {
            bundle.putBoolean((String) entry3.getKey(), ((Boolean) entry3.getValue()).booleanValue())
        }
        for (Map.Entry entry4 : this.objValues.entrySet()) {
            bundle.putSerializable((String) entry4.getKey(), (Serializable) entry4.getValue())
        }
    }
}
