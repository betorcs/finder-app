package org.devmaster.places.finder.places

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_places.*
import org.devmaster.places.finder.GooglePlacesServiceFactory
import org.devmaster.places.finder.R
import org.devmaster.places.finder.databinding.ActivityPlacesBinding
import org.devmaster.places.finder.domain.Place

class PlacesActivity : AppCompatActivity(), PlacesContract.View {

    private lateinit var mBinding: ActivityPlacesBinding

    private val mPresenter: PlacesContract.Presenter by lazy {
        PlacesPresenter(GooglePlacesServiceFactory.getGooglePlacesService(), this)
    }

    private val mAdapter: PlacesAdapter by lazy {
        PlacesAdapter()
    }

    private lateinit var mEmptyDrawable: Drawable
    private lateinit var mErrorDrawable: Drawable

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {

        // Setup SearchView
        val item = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.queryHint = "Pizza in Bauru"
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { text ->

                    // Clear results
                    mAdapter.clear()
                    mAdapter.notifyDataSetChanged()

                    // New search
                    mPresenter.searchPlaces(text)

                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load drawables
        ContextCompat.getDrawable(this, R.drawable.nosearch)?.let { mEmptyDrawable = it }
        ContextCompat.getDrawable(this, R.drawable.owl)?.let { mErrorDrawable = it }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_places)
        mBinding.recyclerView.adapter = mAdapter

        // Setup Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onStop() {
        mPresenter.onStop()
        super.onStop()
    }

    /* PlacesContract.View */

    override fun setProgressIndicator(visible: Boolean) {
        mBinding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        if (visible) hideState()
    }

    override fun showError(error: Throwable) {
        showErrorState()
    }

    override fun showPlaces(places: Iterable<Place>) {
        mAdapter.addAll(places)
        mAdapter.notifyDataSetChanged()

        if (mAdapter.itemCount == 0) {
            showEmptyState()
        } else {
            hideState()
        }
    }

    private fun showEmptyState() {
        mBinding.imageView.setImageDrawable(mEmptyDrawable)
        mBinding.imageView.visibility = View.VISIBLE
    }

    private fun showErrorState() {
        mBinding.imageView.setImageDrawable(mErrorDrawable)
        mBinding.imageView.visibility = View.VISIBLE
    }

    private fun hideState() {
        mBinding.imageView.visibility = View.GONE
    }

}