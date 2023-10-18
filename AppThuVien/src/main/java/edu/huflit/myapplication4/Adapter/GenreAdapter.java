package edu.huflit.myapplication4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Entity.Genre;
import edu.huflit.myapplication4.Fragment.BookListFragment;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreVH> {
    Context context;
    ArrayList<Genre> genres;

    public GenreAdapter(Context applicationContext, ArrayList<Genre> genres) {
        this.context=applicationContext;
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenreVH(LayoutInflater.from(context).inflate(R.layout.genrecard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreVH holder, @SuppressLint("RecyclerView") int position) {
        holder.genreName.setText(genres.get(position).getName());
        holder.genreCard.setOnClickListener(v -> {
            MainActivity.instance.currentFragment = new BookListFragment("", genres.get(position).getName());
            MainActivity.instance.ReplaceFragment(-1);
        });
    }


    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class GenreVH extends RecyclerView.ViewHolder {
        LinearLayout genreCard;
        TextView genreName;
        public GenreVH(@NonNull View itemView) {
            super(itemView);
            genreCard = itemView.findViewById(R.id.GenreCard);
            genreName = itemView.findViewById(R.id.GenreText);
        }
    }
}
