package com.dinhnt.fpl_asm_androidcb.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dinhnt.fpl_asm_androidcb.R;
import com.dinhnt.fpl_asm_androidcb.helper.QuanLySinhVienSQLite;
import com.dinhnt.fpl_asm_androidcb.models.Lop;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class ClassesManagerAdapter extends BaseAdapter {

    ArrayList<Lop> alLop;
    Context context;
    QuanLySinhVienSQLite quanLySinhVien;

    public ClassesManagerAdapter(ArrayList<Lop> alLop, Context context, QuanLySinhVienSQLite quanLySinhVien) {
        this.alLop = alLop;
        this.context = context;
        this.quanLySinhVien = quanLySinhVien;
    }

    @Override
    public int getCount() {
        return alLop.size();
    }

    @Override
    public Object getItem(int i) {
        return alLop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewOfItem {
        TextView txtSTT, txtMaLop, txtTenLop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewOfItem viewOfItem;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();


        if (view == null) {
            view = inflater.inflate(R.layout.item_classes, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.txtSTT = view.findViewById(R.id.txtSTT);
            viewOfItem.txtMaLop = view.findViewById(R.id.txtMaLop);
            viewOfItem.txtTenLop = view.findViewById(R.id.txtTenLop);

            view.setTag(viewOfItem);
        } else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.txtSTT.setText("" + (i + 1));
        viewOfItem.txtMaLop.setText(alLop.get(i).getMaLop());
        viewOfItem.txtTenLop.setText(alLop.get(i).getTenLop());

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogDeleteClasses(alLop.get(i).getMaLop(), alLop.get(i).getTenLop());
                return false;
            }
        });
        return view;
    }

    private void dialogDeleteClasses(String maLop, String tenLop) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Khi thực hiện xóa lớp " + tenLop
                +", toàn bộ sinh viên trong lớp cũng bị xóa theo. Bạn có muốn xóa không?");

        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                quanLySinhVien.deleteClasses(maLop);

                alLop.clear();
                alLop = quanLySinhVien.getAllClasses();
                notifyDataSetChanged();

                Toast.makeText(context, "Xóa lớp mới thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
