package edu.huflit.myapplication4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Fragment.BookDetailFragment;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class CartApdater extends RecyclerView.Adapter<CartApdater.CartVH> {
    Context context;
    ArrayList<Book> bookCarts;

    public CartApdater(Context applicationContext, ArrayList<Book> bookCarts) {
        this.context= applicationContext;
        this.bookCarts = bookCarts;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartVH(LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false));
    }

    int maxLength = 40;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartVH holder, @SuppressLint("RecyclerView") int position) {
        if(!bookCarts.get(position).getUrlImage().equals("Unknown"))
        {
            Glide.with(holder.itemView) // In your activity or fragment, initialize Glide with the context
                    .load(bookCarts.get(position).getUrlImage()) // Use the load() method to specify the image URL
                    .into(holder.bookImg); // Use the into() method to specify the ImageView to load the image into
        }
        else
        {
            holder.bookImg.setImageResource(R.drawable.unknownbook);
        }

        holder.bookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.currentFragment = new BookDetailFragment(bookCarts.get(position));
                MainActivity.instance.ReplaceFragment(-1);
            }
        });

        holder.bookName.setText(bookCarts.get(position).getTitle());
        holder.bookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.currentFragment = new BookDetailFragment(bookCarts.get(position));
                MainActivity.instance.ReplaceFragment(-1);
            }
        });


        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookCarts.remove(bookCarts.get(position));
                Toast.makeText(context, "Xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookCarts.size();
    }

    static class CartVH extends RecyclerView.ViewHolder {
        ImageView bookImg;
        TextView bookName;
        ImageView delBtn;
        public CartVH(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.cartimg);
            bookName = itemView.findViewById(R.id.cartbooktitle);
            delBtn = itemView.findViewById(R.id.cart_delete);
        }

    }
}
