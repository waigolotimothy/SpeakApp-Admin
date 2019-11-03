package ug.co.globalautosystems.speakapp_admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ug.co.globalautosystems.speakapp_admin.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String countryList[];
    int flag[];
    LayoutInflater inflater;


    public CustomAdapter(Context applicationContext,String[] countryList, int[] flag) {
        this.context = context;
        this.countryList=countryList;
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return countryList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.activity__list_view,null);
        TextView country = view.findViewById(R.id.second_List);
        ImageView icon = view.findViewById(R.id.iconn);
        country.setText(countryList[position]);
        icon.setImageResource(flag[position]);
        return view;

    }
}


