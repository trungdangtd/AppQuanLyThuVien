package edu.huflit.myapplication4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Book;
import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.Fragment.BookDetailFragment;
import edu.huflit.myapplication4.Fragment.UpdateBookFragment;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookVH>{
    Context context;
    ArrayList<Book> book;

    public BookAdapter(Context applicationContext, ArrayList<Book> bookList) {
        this.context=applicationContext;
        this.book =bookList;
    }

    @NonNull
    @Override
    public BookVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookVH(LayoutInflater.from(context).inflate(R.layout.book_card, parent, false));
    }
    ArrayList<Copy> copyCount;
    int maxLength = 40;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookVH holder, @SuppressLint("RecyclerView") int position) {
        copyCount = new ArrayList<>();
        copyCount = BookstoreProjectDatabase.LoadCopiesWithBookId(book.get(position).getId(), "Còn");
        if(MainActivity.instance.isLogin && (BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý") || BookstoreProjectDatabase.accountInfo.getRole().equals("Thủ kho"))) {
            holder.bookCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Create a new PopupWindow instance
                    PopupWindow popup = new PopupWindow(holder.itemView.getContext().getApplicationContext());

                    // Inflate your custom layout
                    LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.updateordeletebox, null);

                    Button updateBtn = layout.findViewById(R.id.updateButton);
                    Button deleteBtn = layout.findViewById(R.id.deleteButton);
                    Button cancelBtn = layout.findViewById(R.id.cancelButton);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.dismiss();
                        }
                    });
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.instance.currentFragment = new UpdateBookFragment(book.get(position));
                            MainActivity.instance.ReplaceFragment(-1);
                            popup.dismiss();
                        }
                    });
                    if(BookstoreProjectDatabase.accountInfo.getRole().equals("Quản lý")) {
                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BookstoreProjectDatabase.DeleteBook(book.get(position).getId());
                                BookstoreProjectDatabase.DeleteBookCopy(book.get(position).getId());
                                BookstoreProjectDatabase.LoadBooks();
                                book.remove(book.get(position));
                                notifyDataSetChanged();
                                popup.dismiss();
                            }
                        });
                    }

                    // Set the custom layout as the content view for the popup window
                    popup.setContentView(layout);

                    // Set the width and height of the popup window
                    popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                    popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

                    // Show the popup window
                    popup.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);
                    return true;
                }
            });
        }
        holder.bookCard.setOnClickListener(v -> {
            MainActivity.instance.currentFragment = new BookDetailFragment(book.get(position));
            MainActivity.instance.ReplaceFragment(-1);
        });
        if(!book.get(position).getUrlImage().equals("Unknown"))
        {
            Glide.with(holder.itemView) // In your activity or fragment, initialize Glide with the context
                    .load(book.get(position).getUrlImage()) // Use the load() method to specify the image URL
                    .into(holder.bookImage); // Use the into() method to specify the ImageView to load the image into
        }
        else
        {
            holder.bookImage.setImageResource(R.drawable.unknownbook);
        }
        String nameBook = book.get(position).getTitle();

        if(book.get(position).getTitle().length() > maxLength)
            nameBook = nameBook.substring(0, maxLength - 1) + "...";

        holder.bookName.setText(nameBook);


        holder.bookStatus.setText("Tình trạng: " + (copyCount.size() > 0 ? "Còn" : "Hết"));
    }


    @Override
    public int getItemCount() {
        return book.size();
    }

    static class BookVH extends RecyclerView.ViewHolder {
        LinearLayout bookCard;
        ImageView bookImage;
        TextView bookName, bookStatus;
        public BookVH(@NonNull View itemView) {
            super(itemView);
            bookCard = itemView.findViewById(R.id.BookCard);
            bookImage = itemView.findViewById(R.id.imageBook);
            bookName = itemView.findViewById(R.id.nameBook);
            bookStatus = itemView.findViewById(R.id.statusBook);
        }
    }
}
