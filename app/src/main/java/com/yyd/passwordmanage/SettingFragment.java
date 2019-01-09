package com.yyd.passwordmanage;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SettingFragment extends PreferenceFragment {
    private static final String TAG = "SettingFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_frag1);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwitchPreference pwdVisible = (SwitchPreference)findPreference("visible");
        SwitchPreference gestureOn = (SwitchPreference)findPreference("gesture");
//        pwdVisible.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.d(TAG, "onPreferenceChange: ");
//                return true;
//            }
//        });
//        pwdVisible.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                Log.d(TAG, "onPreferenceClick: ");
//                return true;
//            }
//        });
    }
}
