package com.kinglong.realmdemo;

import com.kinglong.realmdemo.util.RealmHelper;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lanjl on 2017/1/24.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {



    private List<Person> mPersonList;

    private View.OnClickListener mOnItemClickListener;

    private FragmentActivity mActivity;

    public SimpleAdapter(FragmentActivity activity, View.OnClickListener onClickListener) {
        mActivity = activity;

        mOnItemClickListener = onClickListener;
    }

    public void setData(List<Person> datas) {
        mPersonList = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder == null || mPersonList == null || mPersonList.size() == 0) {
            return;
        }
        Person person = mPersonList.get(position);
        if (person == null) {
            return;
        }
        holder.mTvName.setText(person.getName());
        holder.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmHelper.getRealmHelper().deletePersion(holder.mTvName.getText().toString());


            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersonList == null ? 0 : mPersonList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_del)
        Button mTvDel;

        @BindView(R.id.ll_person)
        LinearLayout mLlPerson;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }
}
