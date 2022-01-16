package com.dinhnt.fpl_asm_androidcb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dinhnt.fpl_asm_androidcb.helper.QuanLySinhVienSQLite;
import com.dinhnt.fpl_asm_androidcb.models.Lop;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentManagerActivity extends AppCompatActivity {

    private QuanLySinhVienSQLite quanLySinhVienSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);

        Spinner spnClasses = findViewById(R.id.spnClasses);
        Button btnAddStudent = findViewById(R.id.btnAddStudent);

        quanLySinhVienSQLite = new QuanLySinhVienSQLite(this);

        getDataSpinner(spnClasses);

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //...code

                HashMap<String, String> hashMap = (HashMap<String, String>) spnClasses.getSelectedItem();
                Toast.makeText(StudentManagerActivity.this, "Mã lớp: " + hashMap.get("maLop"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataSpinner(Spinner spnClasses) {
        ArrayList<Lop> alLop = quanLySinhVienSQLite.getAllClasses();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (Lop lop : alLop) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("maLop", lop.getMaLop());
            hashMap.put("tenLop", lop.getTenLop());
            list.add(hashMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list, R.layout.item_spinner_classes,
                new String[]{"tenLop"}, new int[]{R.id.txtTenLop});
        spnClasses.setAdapter(adapter);
    }
}