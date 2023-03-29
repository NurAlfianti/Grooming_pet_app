package com.androidlaika.groomingpets.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlaika.groomingpets.DBHelper;
import com.androidlaika.groomingpets.DetailActivity;
import com.androidlaika.groomingpets.Models.OrderModel;
import com.androidlaika.groomingpets.R;

import java.util.ArrayList;

// Di sini kita extending viewHolder class yang dibuat di dalam kelas ini
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder>{

    ArrayList<OrderModel> list;
    Context context;

    // Constructor
    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Function ini digunakan untuk meng-inflate contoh layout file menjadi layout file "activity_order.xml".
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grooming_order, parent, false);
        return new viewHolder(view);
    }

    // Function ini digunakan untuk mengatur semua data view berdasarkan posisinya di "activity_order.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final OrderModel model = list.get(position);

        holder.orderImage.setImageResource(model.getOrderImage());
        holder.orderGroomingPacket.setText(model.getOrderGroomingPacket());
        holder.orderNumber.setText(model.getOrderNumber());
        holder.orderPrice.setText(model.getOrderPrice());

        // Berikut adalah kode apa yang terjadi ketika kita mengklik salah satu item Order Activity
        // Di sini ketika kita mengklik salah satu item itu akan pindah ke Detail Activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("orderNumber", model.getOrderNumber());
                moveToDetailActivity.putExtra("type", 2);
                context.startActivity(moveToDetailActivity);

            }
        });

        // Berikut adalah kode apa yang terjadi ketika kita mengklik salah satu item Order Activity
        // Di sini ketika kita lama mengklik salah satu item itu akan menghapus item itu
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Alert Dialog Box
                // Itu akan ditampilkan ketika kita menekan tombol kembali di ponsel kita
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setIcon(R.drawable.warning)
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DBHelper helper = new DBHelper(context);

                                if (helper.deleteOrder(Integer.parseInt(model.getOrderNumber())) > 0) {
                                    Toast.makeText(context, "Order is deleted successfully", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(context, "Order isn't deleted \n Please delete the order again", Toast.LENGTH_SHORT).show();
                                ((Activity) context).recreate();

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                return true;

            }

        });

    }

    // Function ini digunakan untuk mengatur ukuran recycler view di "activity_order.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }


    // Fungsi ini digunakan untuk mengikat semua tampilan yang digunakan dalam layout file dan mendapatkan tampilan tersebut dengan idnya
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView orderImage;
        TextView orderGroomingPacket, orderNumber, orderPrice;

        // Constructor
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            orderImage = itemView.findViewById(R.id.orderImage);
            orderGroomingPacket = itemView.findViewById(R.id.orderGroomingPacket);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderPrice = itemView.findViewById(R.id.orderPrice);

        }

    }


}
