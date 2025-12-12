package android.support.v7.view.menu

import android.support.v7.view.menu.MenuPresenter

interface MenuHelper {
    Unit dismiss()

    Unit setPresenterCallback(MenuPresenter.Callback callback)
}
