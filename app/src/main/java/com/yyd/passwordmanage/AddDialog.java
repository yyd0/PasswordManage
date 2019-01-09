package com.yyd.passwordmanage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDialog extends DialogFragment {
    private DialogInputListener listener;

    public void setListener(DialogInputListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        EditText etName = view.findViewById(R.id.et_name);
        EditText etPassword = view.findViewById(R.id.et_password);
        builder.setView(view)
                .setCancelable(false)
                .setTitle("新建")
                .setPositiveButton("Sign in",
                        (dialog, id) -> {
                            String name = etName.getText().toString();
                            String password = etPassword.getText().toString();
                            if (TextUtils.isEmpty(name)) {
                                Toast.makeText(getActivity(), R.string.name_empty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(getActivity(), R.string.password_empty, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (listener != null) {
                                listener.onInputComplete(name, password);
                            }
                        }).setNegativeButton("Cancel", null);


        return builder.create();
    }

    interface DialogInputListener {
        void onInputComplete(String name, String password);
    }

}
