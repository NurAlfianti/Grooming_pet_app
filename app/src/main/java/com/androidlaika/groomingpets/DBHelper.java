package com.androidlaika.groomingpets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.androidlaika.groomingpets.Models.OrderModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBName = "groomingOrdering.db";
    final static int DBVersion = 2;

    // Konstruktor
    // Dalam konstruktor ini ada empat argumen yang diteruskan dalam fungsi super:
    // 1. konteks   Itu menunjukkan dari mana database dipanggil
    // 2. DBName    Di sini kita memberikan nama database
    // 3. pabrik    Di sini kita menyampaikan nilai pabrik
    // 4. DBVersion Di sini kita memberikan versi basis data nilai
    //              Nilai DBVersion akan diubah setiap kali kita mengubah apa pun database dan di bawah methods onUpgrade juga akan dipanggil
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    // Membuat tabel di database "groomingOrdering.db" di atas
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table orders" +
                        "(id integer primary key autoincrement, " +
                        "image int," +
                        "groomingpacket text," +
                        "quantity int," +
                        "description text," +
                        "animalowner text," +
                        "phone text," +
                        "price int," +
                        "originalrprice int)"
        );

    }

    // Methods ini dipanggil saat kita mengubah database apapun
    // Jika kita mengubah sesuatu di database maka pertama-tama kita harus mengubah DBVersion di atas dan kemudian methods ini akan dipanggil
    // Dalam methods ini akan menghapus tabel di atas dan tabel baru yang diperbarui akan dibuat di tempat itu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists orders");
        onCreate(db);

    }

    // CRUD OPERATION


    // CREATE OPERATION
    // Methods ini digunakan untuk memasukkan pesanan ke dalam database
    // Methods ini dipanggil ketika kita mengklik tombol "Pesan Sekarang" dan pesanan itu akan dimasukkan ke dalam database
    public boolean insertOrder(int image, String groomingPacket, int quantity, String description, String animalOwner, String phone, int price, int originalPrice) {


        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("groomingpacket", groomingPacket);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("animalowner", animalOwner);
        values.put("phone", phone);
        values.put("price", price);
        values.put("originalrprice", originalPrice);

        // Di sini ketika methods insert dipanggil, ia akan mengembalikan id dari baris yang disisipkan
        long value = db.insert("orders", null, values);


        // Jika nilai 0 atau <0 dikembalikan, baris tidak dimasukkan
        if (value <= 0)
            return false;
            // Jika >0 nilai dikembalikan baris dimasukkan
        else
            return true;

    }

    // READ OPERATION
    // Methods ini digunakan untuk membaca perintah dari database
    // Methods ini dipanggil dari aktivitas pesanan di mana kami mendapatkan semua pesanan
    // dari database dan menunjukkan pesanan itu di halaman aktivitas pesanan
    public ArrayList<OrderModel> getOrders() {

        ArrayList<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id, image, groomingPacket, price from orders", null);


        if (cursor.moveToFirst()) {

            OrderModel orderModel = new OrderModel();
            orderModel.setOrderNumber(cursor.getInt(0) + "");
            orderModel.setOrderImage(cursor.getInt(1));
            orderModel.setOrderGroomingPacket(cursor.getString(2));
            orderModel.setOrderPrice(cursor.getInt(3) + "");
            orders.add(orderModel);
            while (cursor.moveToNext()) {

                OrderModel model = new OrderModel();
                model.setOrderNumber(cursor.getInt(0) + "");
                model.setOrderImage(cursor.getInt(1));
                model.setOrderGroomingPacket(cursor.getString(2));
                model.setOrderPrice(cursor.getInt(3) + "");
                orders.add(model);

            }

        }

        cursor.close();
        db.close();
        return orders;

    }

    // Methods ini digunakan untuk membaca order by id dari database
    // Methods ini dipanggil dari aktivitas detail di mana kita mendapatkan satu pesanan dengan idnya dari database dan menunjukkan urutan itu di halaman aktivitas detail
    public Cursor getOrderById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from orders where id=" + id, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;

    }


    // UPDATE OPERATION
    // Methods ini digunakan untuk memperbarui urutan dalam database
    // Methods ini dipanggil saat kita mengklik tombol "Perbarui Pesanan" dan pesanan tersebut akan diperbarui di database
    public boolean updateOrder(int id, int image, String groomingPacket, int quantity, String description, String animalOwner, String phone, int price) {

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("groomingpacket", groomingPacket);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("animalowner", animalOwner);
        values.put("phone", phone);
        values.put("price", price);

        // Di sini ketika methods pembaruan dipanggil, ia akan mengembalikan id dari baris yang diperbarui
        long value = db.update("orders", values, "id=" + id, null);
        db.close();

        // Jika 0 atau <0 nilai dikembalikan, baris tidak diperbarui
        if (value <= 0)
            return false;
            // Jika >0 nilai dikembalikan baris diperbarui
        else
            return true;
    }


    // DELETE OPERATION
    // Methods ini digunakan untuk menghapus pesanan dari database
    // Methods ini dipanggil saat kita menekan lama item apa pun dari aktivitas pemesanan dan ini akan menghapus pesanan tersebut dari database
    public int deleteOrder(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        int value = db.delete("orders", "id="+id, null);
        return value;

    }

}
