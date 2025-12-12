package com.b.a

import android.widget.SeekBar

final class d implements SeekBar.OnSeekBarChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f687a

    d(a aVar) {
        this.f687a = aVar
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final Unit onProgressChanged(SeekBar seekBar, Int i, Boolean z) {
        this.f687a.f684a.removeMessages(0)
        this.f687a.f684a.sendEmptyMessageDelayed(0, 100L)
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final Unit onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final Unit onStopTrackingTouch(SeekBar seekBar) {
    }
}
