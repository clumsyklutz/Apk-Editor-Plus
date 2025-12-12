package android.support.v7.app

import android.support.v7.app.ActionBar
import android.view.View
import android.widget.AdapterView

class NavItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private final ActionBar.OnNavigationListener mListener

    constructor(ActionBar.OnNavigationListener onNavigationListener) {
        this.mListener = onNavigationListener
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onItemSelected(AdapterView adapterView, View view, Int i, Long j) {
        if (this.mListener != null) {
            this.mListener.onNavigationItemSelected(i, j)
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    fun onNothingSelected(AdapterView adapterView) {
    }
}
