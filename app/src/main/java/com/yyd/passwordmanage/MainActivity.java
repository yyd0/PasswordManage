package com.yyd.passwordmanage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;
    private static final String TAG = "MainActivity";
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v ->
                toEdit()
        );
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        Box<Info> infoBox = boxStore.boxFor(Info.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean visible = sharedPreferences.getBoolean("visible", false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_view);
        adapter = new ListAdapter(this, infoBox,visible);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        List<Info> infos = infoBox.query().build().find();
        adapter.setDatas(infos);


        //https://stackoverflow.com/questions/2542938/sharedpreferences-onsharedpreferencechangelistener-not-being-called-consistently
        listener = (sharedPreferences1, key) -> {
            boolean b = sharedPreferences1.getBoolean(key, false);
            if ("visible".equals(key)) {
                adapter.setPasswordVisible(b);
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    private void toEdit() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Info info) {
        adapter.addOne(info);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditEvent(InfoEditEvent event) {
        adapter.changeOne(event);
    }
}
