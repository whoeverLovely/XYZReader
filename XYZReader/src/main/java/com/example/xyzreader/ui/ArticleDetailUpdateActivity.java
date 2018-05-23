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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

public class ArticleDetailUpdateActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ArticleDetailUpdateActivity.class.getSimpleName();

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int pageCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_update);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);

        // Create a new Fragment to be placed in the activity layout
        ArticleDetailUpdateFragment detailFragmentUpdate = new ArticleDetailUpdateFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        detailFragmentUpdate.setArguments(getIntent().getExtras());
        long id = ItemsContract.Items.getItemId(getIntent().getData());

        Log.d(TAG, "the itemId received in detail activity: " + String.valueOf(id));
        detailFragmentUpdate.setId(id);

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.pager, detailFragmentUpdate).commit();

        getSupportLoaderManager().initLoader(0, null, this);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        pageCount = data.getCount();

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), data);
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mPager.setAdapter(mPagerAdapter);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        Cursor data;
        public ScreenSlidePagerAdapter(FragmentManager fm, Cursor data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            ArticleDetailUpdateFragment detailFragmentUpdate = new ArticleDetailUpdateFragment();
            data.moveToPosition(position);
            long id = data.getLong(data.getColumnIndex(ItemsContract.Items._ID));
            detailFragmentUpdate.setId(id);

            return detailFragmentUpdate;
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    }

}

