package com.murraystudio.cmbmeettheteam;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class TeamMembersActivity extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
    protected TeamAdapter teamAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;

    private List<TeamMember> teamMembersList;

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/sans-serif-light");
        //collapsingToolbarLayout.setCollapsedTitleTypeface();
        //collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Visit \"murraystudio.github.io\" for more info", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        teamMembersList = new ArrayList<TeamMember>();


        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        try {
            parseJson();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        teamAdapter = new TeamAdapter(teamMembersList, this);

        mLayoutManager = new LinearLayoutManager(this);
        setRecyclerViewLayout();
    }

    /*
    *
    * A helper method to set the layout for the recycler view.
    * If the layout is already set, then we get the scroll position.
    *
     */
    private void setRecyclerViewLayout() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    private void parseJson() throws JSONException, IOException {
        JSONObject data = new JSONObject(loadJSONFromAsset());
        JSONArray children = data.getJSONArray("children");

        //go through each JSON child and get the post data needed to populate our post cards.
        for(int i=0; i<children.length(); i++){
            JSONObject cur = children.getJSONObject(i);

            TeamMember tm = new TeamMember();
            tm.firstName = cur.optString("firstName");
            tm.lastName = cur.optString("lastName");
            tm.bio = cur.optString("bio");
            tm.title = cur.optString("title");
            tm.avatarURL = cur.optString("avatar");

            teamMembersList.add(tm);
        }

        teamAdapter = new TeamAdapter(teamMembersList, this);
        mRecyclerView.setAdapter(teamAdapter);
    }

    private String loadJSONFromAsset() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.team);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        String jsonString = writer.toString();

        Log.i("JSON", jsonString);

        return jsonString;
    }
}
