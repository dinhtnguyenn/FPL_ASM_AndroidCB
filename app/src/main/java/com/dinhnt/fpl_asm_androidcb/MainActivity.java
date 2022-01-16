package com.dinhnt.fpl_asm_androidcb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dinhnt.fpl_asm_androidcb.helper.QuanLySinhVienSQLite;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private QuanLySinhVienSQLite quanLySinhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddClasses = findViewById(R.id.btnAddClasses);
        Button btnClassesManager = findViewById(R.id.btnClassesManager);
        Button btnStudentManager = findViewById(R.id.btnStudentManager);

        quanLySinhVien = new QuanLySinhVienSQLite(this);

        btnAddClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddClasses();
            }
        });

        btnClassesManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClassesManagerActivity.class));
            }
        });

        btnStudentManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentManagerActivity.class));
            }
        });
    }

    private void dialogAddClasses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_classes, null);

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText edtMaLop = v.findViewById(R.id.edtMaLop);
        EditText edtTenLop = v.findViewById(R.id.edtTenLop);
        Button btnClear = v.findViewById(R.id.btnClear);
        Button btnAdd = v.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLop = edtMaLop.getText().toString();
                String tenLop = edtTenLop.getText().toString();
                quanLySinhVien.createClasses(maLop, tenLop);

                Snackbar.make(findViewById(R.id.root), "Thêm lớp mới thành công", 5000)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();

                alertDialog.dismiss();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMaLop.setText("");
                edtTenLop.setText("");
            }
        });
    }
}