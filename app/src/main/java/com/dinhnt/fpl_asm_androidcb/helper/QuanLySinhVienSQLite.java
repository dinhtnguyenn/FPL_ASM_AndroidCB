package com.dinhnt.fpl_asm_androidcb.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dinhnt.fpl_asm_androidcb.models.Lop;

import java.util.ArrayList;

public class QuanLySinhVienSQLite extends SQLiteOpenHelper {

    SQLiteDatabase db = this.getReadableDatabase();

    private static final String DATABASE_NAME = "quanlysinhvien";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOP = "lop";
    private static final String KEY_MALOP = "malop";
    private static final String KEY_TENLOP = "tenlop";

    private static final String TABLE_SINHVIEN = "sinhvien";
    private static final String KEY_MASV = "masv";
    private static final String KEY_TENSV = "tensv";
    private static final String KEY_NGAYSINH = "ngaysinh";

    public QuanLySinhVienSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng lớp
        String sqlLop = "create table " + TABLE_LOP +
                "(" +
                KEY_MALOP + " text, " +
                KEY_TENLOP + " text" +
                ")";
        db.execSQL(sqlLop);

        //tạo bảng sinh viên
        String sqlSinhVien = "create table " + TABLE_SINHVIEN +
                "(" +
                KEY_MASV + " integer primary key autoincrement, " +
                KEY_TENSV + " text, " +
                KEY_NGAYSINH + " text," +
                KEY_MALOP + " text" +
                ")";
        db.execSQL(sqlSinhVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_LOP);
        onCreate(db);

        db.execSQL("drop table if exists " + TABLE_SINHVIEN);
        onCreate(db);
    }

    //lấy danh sách lớp
    public ArrayList<Lop> getAllClasses() {
        Cursor cursor = db.rawQuery("select * from " + TABLE_LOP, null);

        ArrayList<Lop> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Lop(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;

    }

    //Tạo lớp mới
    public void createClasses(String maLop, String tenLop) {
        ContentValues values = new ContentValues();
        values.put(KEY_MALOP, maLop);
        values.put(KEY_TENLOP, tenLop);

        db.insert(TABLE_LOP, null, values);
    }

    //Cập nhật thông tin lớp
    public void updateClasses(String maLop, String tenLop) {
        ContentValues values = new ContentValues();
        values.put(KEY_TENLOP, tenLop);

        db.update(TABLE_LOP, values, "maLop=?", new String[]{maLop});
    }

    //Xóa lớp (sẽ xóa luôn sinh viên của lớp đó)
    public void deleteClasses(String maLop) {
        db.delete(TABLE_SINHVIEN, "maLop=?", new String[]{maLop});
        db.delete(TABLE_LOP, "maLop=?", new String[]{maLop});
    }
}