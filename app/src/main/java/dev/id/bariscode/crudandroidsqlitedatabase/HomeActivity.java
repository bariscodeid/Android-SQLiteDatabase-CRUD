package dev.id.bariscode.crudandroidsqlitedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.id.bariscode.crudandroidsqlitedatabase.adapter.adapter;
import dev.id.bariscode.crudandroidsqlitedatabase.helper.databaseConfig;
import dev.id.bariscode.crudandroidsqlitedatabase.model.modelDataPerson;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.lstData)
    ListView lstData;
    @BindView(R.id.fabAddData)
    FloatingActionButton fabAddData;

    AlertDialog.Builder dialog;
    List<modelDataPerson> itemList = new ArrayList<modelDataPerson>();
    adapter clsAdpter;
    databaseConfig SQLiteProduction = new databaseConfig(this);

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        SQLiteProduction = new databaseConfig(getApplicationContext());

        clsAdpter = new adapter(HomeActivity.this, itemList);
        lstData.setAdapter(clsAdpter);

        lstData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();

                final CharSequence[] dialogitem = {
                        "Edit Data", "Delete Data"
                };

                dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setTitle("Pilih Tindakan:");
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whice) {
                        switch (whice) {
                            case 0:
                                Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_NAME, name);
                                intent.putExtra(TAG_ADDRESS, address);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLiteProduction.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
            }
        });
        getAllData();
    }

    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLiteProduction.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String poster = row.get(i).get(TAG_NAME);
            String addresses = row.get(i).get(TAG_ADDRESS);

            modelDataPerson datas = new modelDataPerson();

            datas.setId(id);
            datas.setName(poster);
            datas.setAddress(addresses);

            itemList.add(datas);
        }
        clsAdpter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }

    @OnClick(R.id.fabAddData)
    public void onViewClicked() {
        Intent intent = new Intent(HomeActivity.this,
                AddEditActivity.class);
        startActivity(intent);
    }
}
