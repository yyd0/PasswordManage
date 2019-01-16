package com.yyd.passwordmanage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class LockActivity extends AppCompatActivity {
    private static final String TAG = "LockActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean gesture = sharedPreferences.getBoolean("gesture", false);
        if (gesture) {
            setContentView(R.layout.activity_lock);
            TextView textView = (TextView) findViewById(R.id.tv_tip);
            PatternLockView  patternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                boolean isFirst = true;//是否第一次绘制
                String firstPassword = null;//第一次绘制的密码
                @Override
                public void onStarted() {
                    if (isFirst) {
                        textView.setText(R.string.draw_password);
                    }
                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    if (pattern.size() < 3) {
                        textView.setText(R.string.password_short);
                    } else {
                        String s = PatternLockUtils.patternToString(patternLockView, pattern);
                        boolean isPasswordSet = sharedPreferences.getBoolean("isPasswordSet", false);
                        if (isPasswordSet) {//设置过
                            String passwordString = sharedPreferences.getString("passwordString", null);
                            if (s.equals(passwordString)) {//绘制正确
                                toMain();
                            } else {//绘制错误
                                textView.setText(R.string.password_wrong);
                            }
                        } else {//未设置过
                            if (isFirst) {
                                textView.setText(R.string.draw_again);
                                firstPassword = s;
                                isFirst = false;
                            } else {
                                if (s.equals(firstPassword)) {
                                    sharedPreferences.edit().putString("passwordString", s).apply();
                                    sharedPreferences.edit().putBoolean("isPasswordSet", true).apply();
                                    toMain();
                                } else {//两次绘制不一致
                                    firstPassword = null;
                                    isFirst = true;
                                    textView.setText(R.string.password_not_same);
                                }
                            }
                        }
                    }
                    patternLockView.clearPattern();

                }

                @Override
                public void onCleared() {

                }
            });
        } else {
            toMain();
        }

    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
