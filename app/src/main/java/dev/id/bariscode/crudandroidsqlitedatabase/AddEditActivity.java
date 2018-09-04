package dev.id.bariscode.crudandroidsqlitedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.id.bariscode.crudandroidsqlitedatabase.helper.databaseConfig;

public class AddEditActivity extends AppCompatActivity {

    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    databaseConfig SQLiteProduction = new databaseConfig(this);
    String id, name, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        ButterKnife.bind(this);

        id = getIntent().getStringExtra(HomeActivity.TAG_ID);
        name = getIntent().getStringExtra(HomeActivity.TAG_NAME);
        alamat = getIntent().getStringExtra(HomeActivity.TAG_ADDRESS);

        if (id == null || id == "") {
            setTitle("Tambah Data");
        } else {
            setTitle("Hapus Data");
            etId.setText(id);
            etNama.setText(name);
            etAlamat.setText(alamat);
        }

        if (etNama.getText().toString().equals("") && etAlamat.getText().toString().equals("")) {
            btnSubmit.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        } else {
            btnUpdate.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.btn_update, R.id.btn_submit, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                updateDataSQLite();
                break;
            case R.id.btn_submit:
                simpanDataSQLite();
                break;
            case R.id.btn_cancel:
                cancelAction();
                break;
        }
    }

    private void updateDataSQLite() {
        if (String.valueOf(etNama.getText()).equals(null) || String.valueOf(etNama.getText()).equals("") ||
                String.valueOf(etAlamat.getText()).equals(null) || String.valueOf(etAlamat.getText()).equals("")) {
            Toast.makeText(this, "Inputan masih kosong, isi dulu!", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteProduction.update(Integer.parseInt(etId.getText().toString().trim()),
                                    etNama.getText().toString().trim(),
                                    etAlamat.getText().toString().trim());
            blankEditTex();
            finish();
        }
    }

    private void simpanDataSQLite() {
        if (String.valueOf(etNama.getText()).equals(null) || String.valueOf(etNama.getText()).equals("") ||
            String.valueOf(etAlamat.getText()).equals(null) || String.valueOf(etAlamat.getText()).equals("")) {
            Toast.makeText(this, "Inputan masih kosong, isi dulu!", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteProduction.insert(etNama.getText().toString().trim(),
                                    etAlamat.getText().toString().trim());
            blankEditTex();
            finish();
        }
    }

    private void cancelAction() {
        blankEditTex();
        finish();
    }

    public void blankEditTex() {
        etId.setText(null);
        etNama.setText(null);
        etAlamat.setText(null);
        etNama.requestFocus();
    }
}
