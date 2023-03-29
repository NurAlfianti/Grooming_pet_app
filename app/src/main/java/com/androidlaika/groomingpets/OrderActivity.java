package com.androidlaika.groomingpets;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidlaika.groomingpets.Adapters.OrderAdapter;
import com.androidlaika.groomingpets.Models.OrderModel;
import com.androidlaika.groomingpets.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        // Binding digunakan sebagai pengganti findVIewById
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Custom Toolbar
        Toolbar customToolbar = binding.orderCustomToolbar.customToolbar;
        // OR
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // OR
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);


        // Setting title of custom toolbar
        getSupportActionBar().setTitle("The Orders");



        /* Kode untuk "Tombol Kembali (<-)" */
        // Tombol pengaturan kembali untuk pindah ke aktivitas sebelumnya
        // Di sini tombol kembali digunakan untuk berpindah ke aktivitas pemesanan
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // Fungsi ini menunjukkan apa yang akan terjadi ketika kita mengklik tombol kembali
        // Di sini kita hanya berpindah ke aktivitas sebelumnya yaitu aktivitas pemesanan setiap kali tombol kembali ditekan
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Kode berikut adalah untuk mendapatkan data dari database
        // Membuat objek pembantu basis data
        DBHelper helper = new DBHelper(this);



        // Kode berikut menunjukkan bagaimana item ditambahkan dalam tampilan pendaur ulang dari aktivitas pesanan
        ArrayList<OrderModel> list = helper.getOrders();


        // Di sini kita menambahkan item dalam daftar array melalui database


        // Menyetel adaptor pada tampilan pendaur ulang
        OrderAdapter adapter = new OrderAdapter(list, this);
        binding.orderRecyclerView.setAdapter(adapter);
        int count = list.size();
        if(count>0){
            ImageView iv = findViewById(R.id.notfound);
            iv.setVisibility(View.GONE);
        }
        // Pengguliran vertikal menggunakan pengelola tata letak linier
        // Menyetel pengelola tata letak linier pada tampilan pendaur ulang
        // Di sini pengelola tata letak linier digunakan untuk menggulir secara vertikal semua item tampilan pendaur ulang
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.orderRecyclerView.setLayoutManager(layoutManager);
    }
}