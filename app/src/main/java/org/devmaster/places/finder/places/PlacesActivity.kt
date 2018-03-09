package org.devmaster.places.finder.places

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_places.*
import org.devmaster.places.finder.GooglePlacesServiceFactory
import org.devmaster.places.finder.R
import org.devmaster.places.finder.databinding.ActivityPlacesBinding
import org.devmaster.places.finder.domain.Place

class PlacesActivity : AppCompatActivity(), PlacesContract.View {

    private lateinit var mBinding: ActivityPlacesBinding

    private val mPresenter: PlacesPresenter by lazy {
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
        searchView.queryHint = getString(R.string.search_hint)
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { text ->

                    // Close keyboard
                    hideSoftInput()

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

    private fun hideSoftInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    /* PlacesContract.View */

    override fun setProgressIndicator(visible: Boolean) {
        mBinding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        if (visible) hideState()
    }

    override fun showErrorPlaceholder() {
        showErrorState()
    }

    override fun showPlacesEmptyPlaceholder() {
        mBinding.emptyPlaceholder.visibility = View.VISIBLE
    }

    override fun showPlaces(places: Iterable<Place>) {
        mAdapter.addAll(places)
        mAdapter.notifyDataSetChanged()
    }

    private fun showErrorState() {
        mBinding.errorPlaceholder.visibility = View.VISIBLE
    }

    private fun hideState() {
        mBinding.emptyPlaceholder.visibility = View.GONE
        mBinding.errorPlaceholder.visibility = View.GONE
    }

}