package com.dinhnt.fpl_asm_androidcb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.dinhnt.fpl_asm_androidcb.adapter.ClassesManagerAdapter;
import com.dinhnt.fpl_asm_androidcb.helper.QuanLySinhVienSQLite;
import com.dinhnt.fpl_asm_androidcb.models.Lop;

import java.util.ArrayList;

public class ClassesManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_manager);

        ListView listClasses = findViewById(R.id.listClasses);

        QuanLySinhVienSQLite quanLySinhVien = new QuanLySinhVienSQLite(this);
        ArrayList<Lop> alLop = quanLySinhVien.getAllClasses();
        ClassesManagerAdapter adapter = new ClassesManagerAdapter(alLop, this, quanLySinhVien);
        listClasses.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}