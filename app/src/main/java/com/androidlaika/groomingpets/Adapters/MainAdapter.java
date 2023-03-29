package com.androidlaika.groomingpets.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlaika.groomingpets.DetailActivity;
import com.androidlaika.groomingpets.Models.MainModel;
import com.androidlaika.groomingpets.R;

import java.util.ArrayList;
import java.util.Collection;

// Di sini kita memperluas kelas viewHolder yang dibuat di dalam kelas ini
// Juga menerapkan antarmuka Filterable untuk memfilter data di bilah pencarian dan metodenya dibuat di dalam kelas ini

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> implements Filterable {

    ArrayList<MainModel> list;
    ArrayList<MainModel> listAll;
    Context context;

    // Constructor
    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listAll = new ArrayList<>(list);
    }

    // Function ini digunakan untuk inflate contoh layout file ke dalam layout file "activity_main.xml".
    @NonNull
    @Override
    public  viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grooming_main, parent, false);
        return new viewHolder(view);
    }

    // function ini is used to set data of all views by it's position in "activity_main.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final MainModel model = list.get(position);
        holder.mainImage.setImageResource(model.getMainImage());
        holder.mainGroomingPacket.setText(model.getMainGroomingPacket());
        holder.mainPrice.setText(model.getMainPrice());
        holder.mainDescription.setText(model.getMainDescription());

        // Kode yang terjadi ketika kita mengklik salah satu item dari Main Activity
        // Di sini ketika kita mengklik salah satu item itu akan pindah ke Activity Detail
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("mainImage", model.getMainImage());
                moveToDetailActivity.putExtra("mainGroomingPacket", model.getMainGroomingPacket());
                moveToDetailActivity.putExtra("mainPrice", model.getMainPrice());
                moveToDetailActivity.putExtra("mainDescription", model.getMainDescription());
                moveToDetailActivity.putExtra("type", 1);
                context.startActivity(moveToDetailActivity);

            }
        });

    }

    // function ini digunakan untuk menyetel ukuran tampilan Recycler View di "activity_main.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }



    // Kode ini untuk search bar
    // ini akan memilah atau memfilter apa yang kita ketik di search bar
    @Override
    public Filter getFilter() {
        return filter;
    }

    // Kode ini akan menampilkan hasil filter di search bar
    Filter filter = new Filter() {

        // Method ini akan melakukan pemfilteran di background dan mengembalikan hasilnya dalam publishResults() method
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MainModel> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty())
            {
                filteredList.addAll(listAll);
            }
            else
            {
                for(MainModel mainList : listAll)
                {
                    if(mainList.getMainGroomingPacket().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(mainList);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // Method ini akan menampilkan hasil yang difilter di user interface(UI)
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends MainModel>) filterResults.values);
            notifyDataSetChanged();
        }

    };



    // Function ini digunakan untuk mengikat semua tampilan yang digunakan dalam Layout file dan mendapatkan tampilan tersebut dengan id nya
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView mainImage;
        TextView mainGroomingPacket, mainPrice, mainDescription;

        // Constructor
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            mainImage = itemView.findViewById(R.id.mainImage);
            mainGroomingPacket = itemView.findViewById(R.id.mainGroomingPacket);
            mainPrice = itemView.findViewById(R.id.mainPrice);
            mainDescription = itemView.findViewById(R.id.mainDescription);

        }
    }
}