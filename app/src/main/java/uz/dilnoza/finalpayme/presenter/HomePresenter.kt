package uz.dilnoza.finalpayme.presenter

import uz.dilnoza.finalpayme.contracts.HomeContract

class HomePresenter(private val model: HomeContract.Model, private val view: HomeContract.View) :
    HomeContract.Presenter {

    override fun load() {
        view.openLoader()
        model.getAll {
            view.closeLoader()
            it.onData(view::loadCard).onMessageData(view::showMessage)
        }
    }
}