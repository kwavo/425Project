package com.cnit355.myles.a425lab9_m2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Myles on 4/6/2018.
 */

public class MemberAdapter extends BaseAdapter {

    Context mContext = null;
    int layout = 0;
    ArrayList<MainActivity.Member> data = null;
    LayoutInflater inflater = null;

    public MemberAdapter(Context c, int l, ArrayList<MainActivity.Member> d) {
        this.mContext = c;
        this.layout = l;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getId();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(this.layout, viewGroup, false);
        }

        TextView tvName =


    }
}
