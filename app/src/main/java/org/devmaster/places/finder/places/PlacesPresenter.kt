package org.devmaster.places.finder.places

import android.support.annotation.VisibleForTesting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.devmaster.places.finder.GooglePlacesService

/**
 * Created by betorcs on 3/7/18.
 */
class PlacesPresenter(private val service: GooglePlacesService,
                      private val view: PlacesContract.View) : PlacesContract.Presenter {

    private var query: String? = null
    private var pageToken: String? = null

    private var disposable: Disposable? = null

    override fun searchPlaces(query: String) {
        this.query = query
        searchPlaces()
    }

    override fun onStop() {
        disposable?.dispose()
    }

    private fun searchPlaces() {
        query?.let { query ->

            view.setProgressIndicator(true)

            disposable = service.search(query, pageToken)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        pageToken = response.nextPageToken
                        view.showPlaces(response.results)
                    }, { error ->
                        view.showError(error)
                        view.setProgressIndicator(false)
                    }, {
                        view.setProgressIndicator(false)
                    })
        }
    }

}