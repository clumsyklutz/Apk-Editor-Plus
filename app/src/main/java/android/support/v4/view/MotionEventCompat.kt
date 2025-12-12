package android.support.v4.view

import android.view.MotionEvent

class MotionEventCompat {

    @Deprecated
    public static val ACTION_HOVER_ENTER = 9

    @Deprecated
    public static val ACTION_HOVER_EXIT = 10

    @Deprecated
    public static val ACTION_HOVER_MOVE = 7

    @Deprecated
    public static val ACTION_MASK = 255

    @Deprecated
    public static val ACTION_POINTER_DOWN = 5

    @Deprecated
    public static val ACTION_POINTER_INDEX_MASK = 65280

    @Deprecated
    public static val ACTION_POINTER_INDEX_SHIFT = 8

    @Deprecated
    public static val ACTION_POINTER_UP = 6

    @Deprecated
    public static val ACTION_SCROLL = 8

    @Deprecated
    public static val AXIS_BRAKE = 23

    @Deprecated
    public static val AXIS_DISTANCE = 24

    @Deprecated
    public static val AXIS_GAS = 22

    @Deprecated
    public static val AXIS_GENERIC_1 = 32

    @Deprecated
    public static val AXIS_GENERIC_10 = 41

    @Deprecated
    public static val AXIS_GENERIC_11 = 42

    @Deprecated
    public static val AXIS_GENERIC_12 = 43

    @Deprecated
    public static val AXIS_GENERIC_13 = 44

    @Deprecated
    public static val AXIS_GENERIC_14 = 45

    @Deprecated
    public static val AXIS_GENERIC_15 = 46

    @Deprecated
    public static val AXIS_GENERIC_16 = 47

    @Deprecated
    public static val AXIS_GENERIC_2 = 33

    @Deprecated
    public static val AXIS_GENERIC_3 = 34

    @Deprecated
    public static val AXIS_GENERIC_4 = 35

    @Deprecated
    public static val AXIS_GENERIC_5 = 36

    @Deprecated
    public static val AXIS_GENERIC_6 = 37

    @Deprecated
    public static val AXIS_GENERIC_7 = 38

    @Deprecated
    public static val AXIS_GENERIC_8 = 39

    @Deprecated
    public static val AXIS_GENERIC_9 = 40

    @Deprecated
    public static val AXIS_HAT_X = 15

    @Deprecated
    public static val AXIS_HAT_Y = 16

    @Deprecated
    public static val AXIS_HSCROLL = 10

    @Deprecated
    public static val AXIS_LTRIGGER = 17

    @Deprecated
    public static val AXIS_ORIENTATION = 8

    @Deprecated
    public static val AXIS_PRESSURE = 2
    public static val AXIS_RELATIVE_X = 27
    public static val AXIS_RELATIVE_Y = 28

    @Deprecated
    public static val AXIS_RTRIGGER = 18

    @Deprecated
    public static val AXIS_RUDDER = 20

    @Deprecated
    public static val AXIS_RX = 12

    @Deprecated
    public static val AXIS_RY = 13

    @Deprecated
    public static val AXIS_RZ = 14
    public static val AXIS_SCROLL = 26

    @Deprecated
    public static val AXIS_SIZE = 3

    @Deprecated
    public static val AXIS_THROTTLE = 19

    @Deprecated
    public static val AXIS_TILT = 25

    @Deprecated
    public static val AXIS_TOOL_MAJOR = 6

    @Deprecated
    public static val AXIS_TOOL_MINOR = 7

    @Deprecated
    public static val AXIS_TOUCH_MAJOR = 4

    @Deprecated
    public static val AXIS_TOUCH_MINOR = 5

    @Deprecated
    public static val AXIS_VSCROLL = 9

    @Deprecated
    public static val AXIS_WHEEL = 21

    @Deprecated
    public static val AXIS_X = 0

    @Deprecated
    public static val AXIS_Y = 1

    @Deprecated
    public static val AXIS_Z = 11

    @Deprecated
    public static val BUTTON_PRIMARY = 1

    private constructor() {
    }

    @Deprecated
    fun findPointerIndex(MotionEvent motionEvent, Int i) {
        return motionEvent.findPointerIndex(i)
    }

    @Deprecated
    fun getActionIndex(MotionEvent motionEvent) {
        return motionEvent.getActionIndex()
    }

    @Deprecated
    fun getActionMasked(MotionEvent motionEvent) {
        return motionEvent.getActionMasked()
    }

    @Deprecated
    fun getAxisValue(MotionEvent motionEvent, Int i) {
        return motionEvent.getAxisValue(i)
    }

    @Deprecated
    fun getAxisValue(MotionEvent motionEvent, Int i, Int i2) {
        return motionEvent.getAxisValue(i, i2)
    }

    @Deprecated
    fun getButtonState(MotionEvent motionEvent) {
        return motionEvent.getButtonState()
    }

    @Deprecated
    fun getPointerCount(MotionEvent motionEvent) {
        return motionEvent.getPointerCount()
    }

    @Deprecated
    fun getPointerId(MotionEvent motionEvent, Int i) {
        return motionEvent.getPointerId(i)
    }

    @Deprecated
    fun getSource(MotionEvent motionEvent) {
        return motionEvent.getSource()
    }

    @Deprecated
    fun getX(MotionEvent motionEvent, Int i) {
        return motionEvent.getX(i)
    }

    @Deprecated
    fun getY(MotionEvent motionEvent, Int i) {
        return motionEvent.getY(i)
    }

    fun isFromSource(MotionEvent motionEvent, Int i) {
        return (motionEvent.getSource() & i) == i
    }
}
