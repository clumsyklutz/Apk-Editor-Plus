package android.support.v7.app

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.support.annotation.NonNull
import android.support.annotation.RequiresPermission
import android.support.annotation.VisibleForTesting
import android.support.v4.content.PermissionChecker
import android.util.Log
import java.util.Calendar

class TwilightManager {
    private static val SUNRISE = 6
    private static val SUNSET = 22
    private static val TAG = "TwilightManager"
    private static TwilightManager sInstance
    private final Context mContext
    private final LocationManager mLocationManager
    private val mTwilightState = TwilightState()

    class TwilightState {
        Boolean isNight
        Long nextUpdate
        Long todaySunrise
        Long todaySunset
        Long tomorrowSunrise
        Long yesterdaySunset

        TwilightState() {
        }
    }

    @VisibleForTesting
    TwilightManager(@NonNull Context context, @NonNull LocationManager locationManager) {
        this.mContext = context
        this.mLocationManager = locationManager
    }

    static TwilightManager getInstance(@NonNull Context context) {
        if (sInstance == null) {
            Context applicationContext = context.getApplicationContext()
            sInstance = TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"))
        }
        return sInstance
    }

    @SuppressLint({"MissingPermission"})
    private fun getLastKnownLocation() {
        Location lastKnownLocationForProvider = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? getLastKnownLocationForProvider("network") : null
        Location lastKnownLocationForProvider2 = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 ? getLastKnownLocationForProvider("gps") : null
        return (lastKnownLocationForProvider2 == null || lastKnownLocationForProvider == null) ? lastKnownLocationForProvider2 != null ? lastKnownLocationForProvider2 : lastKnownLocationForProvider : lastKnownLocationForProvider2.getTime() > lastKnownLocationForProvider.getTime() ? lastKnownLocationForProvider2 : lastKnownLocationForProvider
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    private fun getLastKnownLocationForProvider(String str) {
        try {
            if (this.mLocationManager.isProviderEnabled(str)) {
                return this.mLocationManager.getLastKnownLocation(str)
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to get last known location", e)
        }
        return null
    }

    private fun isStateValid() {
        return this.mTwilightState.nextUpdate > System.currentTimeMillis()
    }

    @VisibleForTesting
    static Unit setInstance(TwilightManager twilightManager) {
        sInstance = twilightManager
    }

    private fun updateState(@NonNull Location location) {
        Long j
        TwilightState twilightState = this.mTwilightState
        Long jCurrentTimeMillis = System.currentTimeMillis()
        TwilightCalculator twilightCalculator = TwilightCalculator.getInstance()
        twilightCalculator.calculateTwilight(jCurrentTimeMillis - 86400000, location.getLatitude(), location.getLongitude())
        Long j2 = twilightCalculator.sunset
        twilightCalculator.calculateTwilight(jCurrentTimeMillis, location.getLatitude(), location.getLongitude())
        Boolean z = twilightCalculator.state == 1
        Long j3 = twilightCalculator.sunrise
        Long j4 = twilightCalculator.sunset
        twilightCalculator.calculateTwilight(86400000 + jCurrentTimeMillis, location.getLatitude(), location.getLongitude())
        Long j5 = twilightCalculator.sunrise
        if (j3 == -1 || j4 == -1) {
            j = 43200000 + jCurrentTimeMillis
        } else {
            j = (jCurrentTimeMillis > j4 ? 0 + j5 : jCurrentTimeMillis > j3 ? 0 + j4 : 0 + j3) + 60000
        }
        twilightState.isNight = z
        twilightState.yesterdaySunset = j2
        twilightState.todaySunrise = j3
        twilightState.todaySunset = j4
        twilightState.tomorrowSunrise = j5
        twilightState.nextUpdate = j
    }

    Boolean isNight() {
        TwilightState twilightState = this.mTwilightState
        if (isStateValid()) {
            return twilightState.isNight
        }
        Location lastKnownLocation = getLastKnownLocation()
        if (lastKnownLocation != null) {
            updateState(lastKnownLocation)
            return twilightState.isNight
        }
        Log.i(TAG, "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.")
        Int i = Calendar.getInstance().get(11)
        return i < 6 || i >= 22
    }
}
