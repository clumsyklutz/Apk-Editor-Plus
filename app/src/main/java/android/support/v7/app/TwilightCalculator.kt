package android.support.v7.app

class TwilightCalculator {
    private static val ALTIDUTE_CORRECTION_CIVIL_TWILIGHT = -0.10471976f
    private static val C1 = 0.0334196f
    private static val C2 = 3.49066E-4f
    private static val C3 = 5.236E-6f
    public static val DAY = 0
    private static val DEGREES_TO_RADIANS = 0.017453292f
    private static val J0 = 9.0E-4f
    public static val NIGHT = 1
    private static val OBLIQUITY = 0.4092797f
    private static val UTC_2000 = 946728000000L
    private static TwilightCalculator sInstance
    public Int state
    public Long sunrise
    public Long sunset

    TwilightCalculator() {
    }

    static TwilightCalculator getInstance() {
        if (sInstance == null) {
            sInstance = TwilightCalculator()
        }
        return sInstance
    }

    fun calculateTwilight(Long j, Double d, Double d2) {
        Float f = 6.24006f + (0.01720197f * ((j - UTC_2000) / 8.64E7f))
        Double dSin = f + (0.03341960161924362d * Math.sin(f)) + (3.4906598739326E-4d * Math.sin(2.0f * f)) + (5.236000106378924E-6d * Math.sin(3.0f * f)) + 1.796593063d + 3.141592653589793d
        Double dSin2 = (Math.sin(f) * 0.0053d) + ((-d2) / 360.0d) + Math.round((r2 - J0) - r6) + J0 + ((-0.0069d) * Math.sin(2.0d * dSin))
        Double dAsin = Math.asin(Math.sin(dSin) * Math.sin(0.4092797040939331d))
        Double d3 = 0.01745329238474369d * d
        Double dSin3 = (Math.sin(-0.10471975803375244d) - (Math.sin(d3) * Math.sin(dAsin))) / (Math.cos(dAsin) * Math.cos(d3))
        if (dSin3 >= 1.0d) {
            this.state = 1
            this.sunset = -1L
            this.sunrise = -1L
        } else {
            if (dSin3 <= -1.0d) {
                this.state = 0
                this.sunset = -1L
                this.sunrise = -1L
                return
            }
            Float fAcos = (Float) (Math.acos(dSin3) / 6.283185307179586d)
            this.sunset = Math.round((fAcos + dSin2) * 8.64E7d) + UTC_2000
            this.sunrise = Math.round((dSin2 - fAcos) * 8.64E7d) + UTC_2000
            if (this.sunrise >= j || this.sunset <= j) {
                this.state = 1
            } else {
                this.state = 0
            }
        }
    }
}
