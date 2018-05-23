package com.example.xyzreader.ui;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

public class ArticleDetailUpdateActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, FragmentManager.OnBackStackChangedListener {

    private static final String TAG = ArticleDetailUpdateActivity.class.getSimpleName();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_update);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);

        getSupportLoaderManager().initLoader(0, null, this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursor = data;

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), data);
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem(getIntent().getIntExtra(ArticleListActivity.EXTRA_POSITION, -1));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursor = null;
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        Cursor data;
        ArticleDetailUpdateFragment detailUpdateFragment;

        public ScreenSlidePagerAdapter(FragmentManager fm, Cursor data) {
            super(fm);
            this.data = data;

            fm.addOnBackStackChangedListener(ArticleDetailUpdateActivity.this);

            detailUpdateFragment = new ArticleDetailUpdateFragment();
        }

        @Override
        public Fragment getItem(int position) {
            detailUpdateFragment = new ArticleDetailUpdateFragment();
            data.moveToPosition(position);
            long id = data.getLong(data.getColumnIndex(ItemsContract.Items._ID));
            detailUpdateFragment.setId(id);

            Log.d(TAG, "getItem position: " + position);
            return detailUpdateFragment;
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }
    }

}

