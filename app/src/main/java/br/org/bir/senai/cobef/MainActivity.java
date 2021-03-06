package br.org.bir.senai.cobef;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ExpandableListView mExpandableListView;
    private List<String> mGroupList;
    private HashMap<String, List<String>> mChildList;
    private HashMap<String, Integer> mItemIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);


        setupDrawerToggle();

        prepareListData();
        setupExpandableListView();


        mExpandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mExpandableListView.setSelectedGroup(0);
        selectItem(0, getString(R.string.start), 0, -1);
    }

    private void setupExpandableListView() {
        final ExplandableListAdapter adapter = new ExplandableListAdapter(this, mGroupList, mChildList);

        mExpandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
        mExpandableListView.setAdapter(adapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (adapter.getChildrenCount(groupPosition) == 0){
                    String item = (String)adapter.getGroup(groupPosition);
                    selectItem(mItemIndex.get(item), item, groupPosition, -1);
                }

                return false;
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    String item = (String)adapter.getChild(groupPosition, childPosition);
                    selectItem(mItemIndex.get(item), item, groupPosition, childPosition);
                    return false;
                }
            }
        );
    }

    private void prepareListData() {
        mGroupList = new ArrayList<String>();
        mChildList = new HashMap<String, List<String>>();
        mItemIndex = new HashMap<String, Integer>();

        String[] titles = getResources().getStringArray(R.array.left_menu_titles);
        String[] aboutChildren = getResources().getStringArray(R.array.about_children);
        String[] programChildren = getResources().getStringArray(R.array.programs_children);

        mGroupList.addAll(Arrays.asList(titles));

        mChildList.put(getString(R.string.about), Arrays.asList(aboutChildren));
        mChildList.put(getString(R.string.programs), Arrays.asList(programChildren));

        int idx = 0;
        for (String item : mGroupList){

            if (mChildList.containsKey(item)){
                List<String> subitems = mChildList.get(item);
                for (String subitem : subitems){
                    mItemIndex.put(subitem, idx++);
                }
            }
            else {
                mItemIndex.put(item, idx++);
            }
        }
    }

    private void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.mipmap.ic_drawer,     /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    private void selectItem(int index, String title, int groupPosition, int childPosition) {

        Fragment fragment = new PageFragment();

        Bundle args = new Bundle();
        args.putInt(PageFragment.ARG_PAGE_INDEX, index);
        fragment.setArguments(args);

        FragmentManager fragmentManger = getFragmentManager();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        setTitle(title);
        mDrawerLayout.closeDrawer(mExpandableListView);
    }
}
