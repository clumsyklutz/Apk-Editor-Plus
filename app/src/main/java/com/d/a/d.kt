package com.d.a

import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Map
import java.util.TimeZone

class d implements k {
    @Override // com.d.a.k
    public final Object a(Object obj, j jVar, Map map) {
        GregorianCalendar gregorianCalendar = GregorianCalendar()
        Calendar calendar = (Calendar) obj
        gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis())
        gregorianCalendar.setTimeZone((TimeZone) calendar.getTimeZone().clone())
        return gregorianCalendar
    }
}
