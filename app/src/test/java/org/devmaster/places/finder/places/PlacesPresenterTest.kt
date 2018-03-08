package org.devmaster.places.finder.places

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.devmaster.places.finder.GooglePlacesService
import org.devmaster.places.finder.domain.PlaceResult
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class PlacesPresenterTest {

    @Mock
    private lateinit var mView: PlacesContract.View

    @Mock
    private lateinit var mService: GooglePlacesService

    private lateinit var mPresenter: PlacesContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mPresenter = PlacesPresenter(mService, mView)

        RxJavaPlugins.setNewThreadSchedulerHandler({ Schedulers.trampoline() })
        RxAndroidPlugins.setMainThreadSchedulerHandler({ Schedulers.trampoline() })
    }

    @Test
    fun searchPlaces_showPlaces() {
        // Given
        val query = "some string"

        // Prepare
        val result = Mockito.mock(PlaceResult::class.java)
        Mockito.`when`(mService.search(query))
                .thenReturn(Observable.just(result))

        // When
        mPresenter.searchPlaces(query)

        // Then
        val inOrder = Mockito.inOrder(mView)
        inOrder.verify(mView).setProgressIndicator(true)
        inOrder.verify(mView).showPlaces(Mockito.anyCollection())
        inOrder.verify(mView).setProgressIndicator(false)
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun searchPlaces_showError() {
        // Given
        val query = "some string"

        // Prepare
        val error = RuntimeException()
        Mockito.`when`(mService.search(query))
                .thenReturn(Observable.error(error))

        // When
        mPresenter.searchPlaces(query)

        // Then
        val inOrder = Mockito.inOrder(mView)
        inOrder.verify(mView).setProgressIndicator(true)
        inOrder.verify(mView).showError(error)
        inOrder.verify(mView).setProgressIndicator(false)
        inOrder.verifyNoMoreInteractions()
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

}