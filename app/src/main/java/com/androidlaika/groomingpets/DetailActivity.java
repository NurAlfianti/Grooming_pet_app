package com.androidlaika.groomingpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidlaika.groomingpets.databinding.ActivityDetailBinding;


public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Binding Digunakan sebagai pengganti findViewById
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Custom Toolbar
        Toolbar customToolbar = binding.detailCustomToolbar.customToolbar;
        //Atau Gunakan
        //Toolbar customToolbar = findViewById(R.id.maincustomToolbar);
        setSupportActionBar(customToolbar);
        //Atau Gunakan
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);


        /* kode untuk " tombol kembali (<-)"  */
        // pengaturan untuk kembali ke aktivitas sebelumnya//
        // tombol disini digunakan untuk kembali ke mainActivity//
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // fungsi ini menunjukan apa yang akan terjadi bila kita meng-klik tombol kembali "back"  //
        // Di sini kita hanya berpindah ke aktivitas sebelumnya yaitu aktivitas utama setiap kali tombol kembali ditekan
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Kode berikut adalah untuk memasukkan data ke dalam database
        // Membuat objek pembantu basis data
        final DBHelper helper = new DBHelper(this);

        // kode untuk proses di halaman order
        if ((getIntent().getIntExtra("type", 0)) == 1) {

            //pengaturan kalimat judul di custom Toolbar
            getSupportActionBar().setTitle("Order Grooming Packet Process");

            // Di sini kita mendapatkan nilai melalui intent yang diteruskan dari main adapter
            final int detailImage = getIntent().getIntExtra("mainImage", 0);
            final String detailGroomingPacket = getIntent().getStringExtra("mainGroomingPacket");
            final int detailPrice = Integer.parseInt(getIntent().getStringExtra("mainPrice"));
            final String detailDescription = getIntent().getStringExtra("mainDescription");

            // Mengatur data di atas pada tampilan DetailActivity
            binding.detailImage.setImageResource(detailImage);
            binding.detailGroomingPacket.setText(detailGroomingPacket);
            binding.detailPrice.setText(String.format("%d", detailPrice));
            binding.detailDescription.setText(detailDescription);

            /* Kode untuk menambah dan mengurangi quantity */
            // Menginisialisasi nilai hitungan
            count = 1;

            // Kode untuk menambah quantity
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText("" + count);

                    // Kode untuk menaikkan harga saat quantity dinaikkan
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText("" + (newPrice + detailPrice));
                }
            });


            //kode untuk mengurangi jumlah pemesanan
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count > 1) {
                        count--;
                        binding.quantity.setText("" + count);

                        //kode untuk mengurangi harga ketika jumlah di kurangkan
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText("" + (newPrice - detailPrice));
                    }
                }
            });



            //kode , ketika klik "order" dan apa yang akan terjadi.
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Nama Pengguna Dan Nomor Hp yang akan tervalidasi
                    if (!validateUsername() | !validateMobileNumber()) {
                        return;
                    }
                    // Setelah validasi di bawah kode untuk membeli item akan berjalan
                    else {

                        // Alert Dialog Box
                        // Ini akan ditampilkan ketika kita menekan tombol "Pesan Sekarang" di perangkat kita
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Buy Item")
                                .setIcon(R.drawable.warning)
                                .setMessage("Are you sure you want to buy this item?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isInserted = helper.insertOrder(
                                                detailImage,
                                                detailGroomingPacket,
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                detailDescription,
                                                binding.animalOwnerName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString()),
                                                detailPrice
                                        );

                                        if (isInserted) {
                                            Toast.makeText(DetailActivity.this, "Order is placed successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DetailActivity.this,OrderActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else
                                            Toast.makeText(DetailActivity.this, "Order isn't placed \n Please place the order again", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();

                                    }
                                }).show();

                    }

                }
            });


        }
        // Kode untuk order grooming updating page
        else {

            // Menyetel judul custom toolbar
            getSupportActionBar().setTitle("Order Updating Page");


            // Di sini kita mendapatkan nilai melalui intent yang diteruskan dari order adapter

            final int id = Integer.parseInt(getIntent().getStringExtra("orderNumber"));


            // Di sini objek kursor digunakan untuk mendapatkan pesanan berdasarkan idnya dan digunakan di bawah untuk mengatur data dari semua tampilan detail activity
            Cursor cursor = helper.getOrderById(id);
            final int image = cursor.getInt(1);



            // Di sini objek kursor di atas digunakan untuk mengatur data pada tampilan Detail Activity
            binding.detailImage.setImageResource(cursor.getInt(1));
            binding.detailGroomingPacket.setText(cursor.getString(2));
            binding.quantity.setText(String.format("%d", cursor.getInt(3)));
            binding.detailDescription.setText(cursor.getString(4));
            binding.animalOwnerName.setText(cursor.getString(5));
            binding.mobileNumber.setText(cursor.getString(6));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(7)));

            // Di sini kita mendapatkan harga asli barang dari database
            // Ini digunakan ketika quantity bertambah atau berkurang pada saat itu harga juga naik atau turun
            int originalPrice = cursor.getInt(8);

            // Kode untuk menambah dan mengurangi kuantitas
            // Menginisialisasi nilai hitungan
            count = Integer.parseInt(binding.quantity.getText().toString());

            // Kode untuk menambah quantity
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText("" + count);

                    // Kode untuk menaikkan harga saat quantity dinaikkan
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText("" + (newPrice + originalPrice));
                }
            });


            // Kode untuk mengurangi quantity
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count > 1) {
                        count--;
                        binding.quantity.setText("" + count);

                        // Kode untuk menurunkan harga saat quantity diturunkan
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText("" + (newPrice - originalPrice));
                    }
                }
            });

            // Mengubah teks tombol "Order Now" button menjadi "Update Order"
            binding.insertBtn.setText("Update Order");

            // Kode berikut menunjukkan apa yang terjadi ketika kita mengklik tombol "Update Order"
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Nama Pengguna Pertama dan nomor ponsel akan divalidasi
                    if (!validateUsername() | !validateMobileNumber()) {
                        return;
                    }

                    // Setelah validasi di bawah kode untuk memperbarui item akan berjalan
                    else {

                        // Alert Dialog Box
                        // Itu akan ditampilkan ketika kita menekan tombol "Update Order" di ponsel kita
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Update Item")
                                .setIcon(R.drawable.warning)
                                .setMessage("Are you sure you want to update this item?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isUpdated = helper.updateOrder(
                                                id,
                                                image,
                                                binding.detailGroomingPacket.getText().toString(),
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                binding.detailDescription.getText().toString(),
                                                binding.animalOwnerName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString())
                                        );

                                        if (isUpdated) {
                                            Toast.makeText(DetailActivity.this, "Order is updated successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                            else
                                            Toast.makeText(DetailActivity.this, "Order isn't updated \n Please update the order again", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).show();

                    }

                }
            });


        }
    }

        // Validation Methods

        // Username Validation
        private boolean validateUsername(){

            String Username = binding.animalOwnerName.getText().toString();
            //        ^           Awal dari string
            //       [A-Za-z]     Karakter pertama bukan spasi tapi bisa berupa huruf kapital atau huruf kecil
            //       [A-Z a-z]    Karakter lain dapat berupa spasi putih atau kapital atau huruf kecil
            //        +           String berisi setidaknya satu karakter abjad
            //        $           Akhir dari string
            String checkUsername = "^[A-Za-z][A-Z a-z]+$";

            if(Username.isEmpty()){
                binding.animalOwnerName.setError("Field cannot be empty");
                return false;
            }
            else if(Username.length()>=25){
                binding.animalOwnerName.setError("Name is too large");
                return false;
            }
            else if(Username.length()<=3){
                binding.animalOwnerName.setError("Name is too short");
                return false;
            }
            else if(!Username.matches(checkUsername)){
                binding.animalOwnerName.setError("Name must not contain numeric or special characters or first whitespace character");
                return false;
            }
            else {
                binding.animalOwnerName.setError(null);
                return true;
            }

        }


        // Mobile Number Validation
        private boolean validateMobileNumber(){

            String mobileNumber = binding.mobileNumber.getText().toString();
            //        [0-9]          sesuai dengan digit pertama dan memeriksa apakah angka terletak antara 0 sampai 9
            //        [0-9]          sesuai dengan digit lain dan memeriksa apakah angka terletak antara 0 hingga 9
            //        {9}            menentukan sisa
            String checkMobileNumber = "[0-9][0-9]{9}";

            if(mobileNumber.isEmpty()){
                binding.mobileNumber.setError("Field cannot be empty");
                return false;
            }
            else if(mobileNumber.length()>12){
                binding.mobileNumber.setError("Mobile number must be of 11 or 12 number");
                return false;
            }
            else if(mobileNumber.length()<12){
                binding.mobileNumber.setError("Mobile number must be of 11 or 12 number");
                return false;
            }
            else {
                binding.mobileNumber.setError(null);
                return true;
            }

        }

}
