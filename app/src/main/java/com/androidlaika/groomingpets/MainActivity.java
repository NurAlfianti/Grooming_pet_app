package com.androidlaika.groomingpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidlaika.groomingpets.Adapters.MainAdapter;
import com.androidlaika.groomingpets.Models.MainModel;
import com.androidlaika.groomingpets.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainAdapter adapter;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        // Binding digunakan sebagai pengganti findVIewById
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Custom Toolbar
        Toolbar customToolbar = binding.mainCustomToolbar.customToolbar;
        // OR
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // OR
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);



        // Mengatur judul Custom Toolbar
        getSupportActionBar().setTitle("Grooming Pets App");

        /*     Navigation Drawer Menu     */



        // Menambahkan tombol sakelar ke sisi kiri bilah alat yang akan membuka dan menutup laci navigasi khusus
        toggle = new ActionBarDrawerToggle(MainActivity.this, binding.drawerLayout, customToolbar, R.string.open_nav_drawer, R.string.open_nav_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();



        // Kode berikut menunjukkan bagaimana item ditambahkan dalam tampilan RecyleView aktivitas utama
        // Di sini item ditambahkan dari ArrayList seperti yang ditunjukkan di bawah ini
        ArrayList<MainModel> list = new ArrayList<>();


        // Di sini kita menambahkan item dalam daftar array
        // Di sini setiap kali item ditambahkan dalam daftar, konstruktor MainModel dipanggil di mana item diinisialisasi
        list.add(new MainModel(R.drawable.cat_1, "Cat Basic Grooming \n small(< 5,2 kg)", "50000", "shampoo bathing, drying, combing, cleaning \n the dirt that still sticks \n to some parts of the cat's body"));
        list.add(new MainModel(R.drawable.cat_2, "Cat Basic Grooming \n big (> 5,2 kg)", "75000", "shampoo bathing, drying, combing, cleaning \n the dirt that still sticks \n to some parts of the cat's body"));
        list.add(new MainModel(R.drawable.cat_3, "Cat Grooming Complete \n small(<5,2 kg)", "80000", "include Basic Grooming, flea repellent, \n and Dry Grooming"));
        list.add(new MainModel(R.drawable.cat_5, "Cat Grooming Complete \n big (>5,2 kg)", "120000", "include Basic Grooming, flea repellent, \n and Dry Grooming"));
        list.add(new MainModel(R.drawable.dog_1, "Dog Basic Grooming \n small(< 5,2 kg)", "80000", "shampoo bathing, drying, combing, cleaning \n the dirt that still sticks \n to some parts of the cat's body"));
        list.add(new MainModel(R.drawable.dog_2, "Dog Basic Grooming \n big(> 5,2 kg)", "100000", "shampoo bathing, drying, combing, cleaning \n the dirt that still sticks \n to some parts of the cat's body"));
        list.add(new MainModel(R.drawable.dog_3, "Dog Grooming Complete \n small(<5,2 kg)", "120000", "include Basic Grooming, flea repellent, \n and Dry Grooming"));
        list.add(new MainModel(R.drawable.dog_4, "Dog Grooming Complete \n big(>5,2 kg)", "140000", "include Basic Grooming, flea repellent, \n and Dry Grooming"));
        list.add(new MainModel(R.drawable.cat_4, "Cat lion shave ", "130000", "the perfect shave for your cat in a lion style."));



        // Menyetel adaptor pada tampilan RecylerView
        adapter = new MainAdapter(list,this);
        binding.mainRecyclerView.setAdapter(adapter);


        // Pengguliran vertikal menggunakan pengelola tata letak linier
        // Menyetel pengelola tata letak linier pada tampilan pendaur ulang
        // Di sini pengelola tata letak linier digunakan untuk menggulir secara vertikal semua item tampilan pendaur ulang
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainRecyclerView.setLayoutManager(layoutManager);

    }

    // Menu ditampilkan di sisi kanan bilah alat
    // Fungsi ini digunakan untuk menampilkan atau memekarkan menu di aktivitas utama
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_orders_btn, menu);
        return true;
    }


    // Fungsi ini digunakan untuk menunjukkan apa yang akan terjadi setelah memilih salah satu item dari menu yang ada di sisi kanan toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.myOrders:
                // perpindahan ke halaman order activity ke main activity
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                break;

            case R.id.searchBar:
                // Search Bar kode
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Type here to Search");
                searchView.setIconifiedByDefault(false);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return true;
                    }
                });
                break;

            default:

        }
        return true;

    }


    // Metode ini dipanggil saat kita menekan tombol kembali di ponsel kita dari halaman aktivitas utama
    @Override
    public void onBackPressed() {

        // Di sini kita menutup laci jika laci terbuka saat kita menekan tombol kembali di ponsel kita
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers();
        }

        // Kotak Dialog Peringatan
        // Itu akan ditampilkan ketika kita menekan tombol kembali di ponsel kita
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setIcon(R.drawable.warning)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }

    }
}