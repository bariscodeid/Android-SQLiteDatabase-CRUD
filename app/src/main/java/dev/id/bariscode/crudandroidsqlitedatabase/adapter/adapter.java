package dev.id.bariscode.crudandroidsqlitedatabase.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dev.id.bariscode.crudandroidsqlitedatabase.R;
import dev.id.bariscode.crudandroidsqlitedatabase.model.modelDataPerson;

public class adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<modelDataPerson> items;

    public adapter(Activity activity, List<modelDataPerson> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) inflater = (LayoutInflater)
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_list, null);


        TextView id = (TextView)convertView.findViewById(R.id.tvId);
        TextView name = (TextView)convertView.findViewById(R.id.tvNamaLengkap);
        TextView address = (TextView)convertView.findViewById(R.id.tvAlamat);

        modelDataPerson item_data = items.get(position);

        id.setText(item_data.getId());
        name.setText(item_data.getName());
        address.setText(item_data.getAddress());

        return convertView;
    }
}
