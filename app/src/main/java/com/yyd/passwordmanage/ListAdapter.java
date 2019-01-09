package com.yyd.passwordmanage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Info> datas;
    private Context context;
    private Box<Info> infoBox;
    private boolean passwordVisible;
    private SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyyMMdd HH:mm");

    public ListAdapter(Context context, Box<Info> infoBox,boolean passwordVisible) {
        this.context = context;
        this.infoBox = infoBox;
        this.passwordVisible = passwordVisible;
        this.datas = new ArrayList<>();
    }

    public void setPasswordVisible(boolean passwordVisible) {
        this.passwordVisible = passwordVisible;
        notifyDataSetChanged();
    }

    public void setDatas(List<Info> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Info info = datas.get(i);
        viewHolder.tvPassword.setText(passwordVisible ? info.getPassword() : "******");
        viewHolder.tvName.setText(info.getName());
        viewHolder.tvAccount.setText(info.getAccount() == null ? "" : info.getAccount());
        String dateStr = simpleDateFormat.format(info.getCreateDate());
        viewHolder.tvDate.setText(dateStr);
        viewHolder.itemView.setOnLongClickListener(v -> {
            deleteOne(info, i);
            return false;
        });
        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("info", info);
            intent.putExtra("position", i);
            context.startActivity(intent);
        });
    }

    private void deleteOne(Info info, int i) {
        datas.remove(info);
        infoBox.remove(info);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, getItemCount());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addOne(Info info) {
        datas.add(info);
        notifyItemInserted(datas.size() - 1);
    }

    public void changeOne(InfoEditEvent event) {
        Info info = datas.get(event.getPosition());
        Info info1 = event.getInfo();
        info.setPassword(info1.getPassword());
        info.setName(info1.getName());
        info.setAccount(info1.getAccount());
        notifyItemChanged(event.getPosition());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPassword;
        TextView tvDate;
        TextView tvAccount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPassword = itemView.findViewById(R.id.tv_password);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAccount = itemView.findViewById(R.id.tv_account);
        }
    }
}
