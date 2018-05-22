package com.example.xyzreader.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ItemsContract;

public class ArticleDetailUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_update);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.detail_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ArticleDetailFragmentUpdate detailFragmentUpdate = new ArticleDetailFragmentUpdate();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            /*detailFragmentUpdate.setArguments(getIntent().getExtras());*/
            long id = ItemsContract.Items.getItemId(getIntent().getData());
            detailFragmentUpdate.setId(id);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_container, detailFragmentUpdate).commit();
        }

    }
}
