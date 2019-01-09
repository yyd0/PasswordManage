package com.yyd.passwordmanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_add);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        TextInputLayout etName = (TextInputLayout) findViewById(R.id.til_name);
        TextInputLayout etAccount = (TextInputLayout) findViewById(R.id.til_account);
        TextInputLayout etPassword = (TextInputLayout) findViewById(R.id.til_password);
        Info info1 = getIntent().getParcelableExtra("info");
        int position = getIntent().getIntExtra("position", 0);
        final Button btAdd = findViewById(R.id.bt_add);
        if (info1 != null) {
            etName.getEditText().setText(info1.getName());
            etAccount.getEditText().setText(info1.getAccount());
            etPassword.getEditText().setText(info1.getPassword());
            toolbar.setTitle(R.string.action_edit);
            btAdd.setText(R.string.action_edit);
            btAdd.setEnabled(true);
        } else {
            toolbar.setTitle(R.string.action_add);
        }

        etName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1)) {
                    etName.setError("名称不能为空");
                    btAdd.setEnabled(false);

                } else {
                    etName.setErrorEnabled(false);
                    checkButtonEnable(etName, etAccount, etPassword, btAdd);
                }
            }
        });
        etAccount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1)) {
                    etAccount.setError("账号不能为空");
                    btAdd.setEnabled(false);

                } else {
                    etAccount.setErrorEnabled(false);
                    checkButtonEnable(etName, etAccount, etPassword, btAdd);
                }
            }
        });
        etPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1)) {
                    etPassword.setError("密码不能为空");
                    btAdd.setEnabled(false);

                } else {
                    etPassword.setErrorEnabled(false);
                    checkButtonEnable(etName, etAccount, etPassword, btAdd);
                }
            }
        });
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        Box<Info> infoBox = boxStore.boxFor(Info.class);
        btAdd.setOnClickListener(v -> {
            String name = etName.getEditText().getText().toString();
            String account = etAccount.getEditText().getText().toString();
            String password = etPassword.getEditText().getText().toString();
            if (info1 == null) {
                Info info = new Info(name, account, password, null);
                EventBus.getDefault().post(info);
                infoBox.put(info);
                Toast.makeText(this, R.string.add_success, Toast.LENGTH_SHORT).show();
                etName.getEditText().setText("");
                etAccount.getEditText().setText("");
                etPassword.getEditText().setText("");
                etName.setErrorEnabled(false);
                etAccount.setErrorEnabled(false);
                etPassword.setErrorEnabled(false);
            } else {
                if (!name.equals(info1.getName()))
                    info1.setName(name);
                if (!account.equals(info1.getAccount()))
                    info1.setAccount(account);
                if (!password.equals(info1.getPassword()))
                    info1.setPassword(password);
                infoBox.put(info1);
                Toast.makeText(this, R.string.edit_success, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new InfoEditEvent(position, info1));
                finish();
            }

        });
    }

    private void checkButtonEnable(TextInputLayout etName, TextInputLayout etAccount, TextInputLayout etPassword, Button btAdd) {
        String name = etName.getEditText().getText().toString();
        String account = etAccount.getEditText().getText().toString();
        String password = etPassword.getEditText().getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            btAdd.setEnabled(true);
        }
    }

}
