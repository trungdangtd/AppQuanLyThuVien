package edu.huflit.myapplication4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class BookCopy2Adapter extends RecyclerView.Adapter<BookCopy2Adapter.BookCopyVH> {
    Context context;
    ArrayList<Book> books;


    public BookCopy2Adapter(Context applicationContext, ArrayList<Book> books) {
        this.context=applicationContext;
        this.books = books;
    }

    @NonNull
    @Override
    public BookCopyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookCopyVH(LayoutInflater.from(context).inflate(R.layout.copybook2_item, parent, false));
    }

    int maxLength = 40;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookCopyVH holder, @SuppressLint("RecyclerView") int position) {
        ArrayList<Copy> copies = BookstoreProjectDatabase.LoadCopiesWithBookId(books.get(position).getId(),"");
        System.out.println("copies.size: " + copies.size());
        Glide.with(holder.itemView) // In your activity or fragment, initialize Glide with the context
                .load(books.get(position).getUrlImage()) // Use the load() method to specify the image URL
                .into(holder.bookCopyImage); // Use the into() method to specify the ImageView to load the image into
        holder.copybookList.setLayoutManager(new LinearLayoutManager(MainActivity.instance, RecyclerView.VERTICAL,false));
        holder.copybookList.setAdapter(new BookCopyAdapter(holder.itemView.getContext().getApplicationContext(), copies));
        holder.bookCopyTextView.setText(books.get(position).getTitle());
        holder.btnCopyBookAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean exit = true;
                String bookIdFixed = "";
                int i = 1;
                while (true){
                    bookIdFixed = "";
                    if(i >= 1 && i < 10)
                        bookIdFixed += "00" + i;
                    else if(i >= 10 && i < 100)
                        bookIdFixed += "0" + i;
                    else if(i >= 100 && i < 1000)
                        bookIdFixed += i;
                    if(copies.size() > 0 && i - 1 < copies.size())
                    {
                        if (!copies.get(i - 1).getId().equals(bookIdFixed)) {
                            BookstoreProjectDatabase.AddBookCopy(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                            copies.add(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                            notifyDataSetChanged();
                            System.out.println("Thêm thành công: " + bookIdFixed);
                            break;
                        }
                    }
                    else if(copies.size() == 0) {
                        BookstoreProjectDatabase.AddBookCopy(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                        copies.add(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                        notifyDataSetChanged();
                        System.out.println("Thêm thành công: " + bookIdFixed);
                        break;
                    }
                    else if(copies.size() > 0 && i - 1 == copies.size())
                    {
                        BookstoreProjectDatabase.AddBookCopy(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                        copies.add(new Copy(bookIdFixed,books.get(position).getId(),"Còn",""));
                        notifyDataSetChanged();
                        System.out.println("Thêm thành công: " + bookIdFixed);
                        break;
                    }
                    i++;
                }
            }
        });
        holder.btnCopyBookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Copy copy:BookstoreProjectDatabase.copies){
                    if(copy.getStatus().equals("Còn")){
                        BookstoreProjectDatabase.DeleteBookCopy(books.get(position).getId(),copy.getId());
                        copies.remove(copies.get(position));
                        notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookCopyVH extends RecyclerView.ViewHolder {
        ImageView bookCopyImage;
        TextView bookCopyTextView, btnCopyBookAdd, btnCopyBookDelete;
        RecyclerView copybookList;
        LinearLayout BookCopy2;



        public BookCopyVH(@NonNull View itemView) {
            super(itemView);
            bookCopyImage = itemView.findViewById(R.id.BookImage);
            bookCopyTextView = itemView.findViewById(R.id.BookTitle);
            btnCopyBookAdd = itemView.findViewById(R.id.btncopybookadd);
            btnCopyBookDelete = itemView.findViewById(R.id.btncopybookedelete);
            copybookList = itemView.findViewById(R.id.copybooklist);
            BookCopy2 = itemView.findViewById(R.id.bookcopy2);
        }
    }


}
