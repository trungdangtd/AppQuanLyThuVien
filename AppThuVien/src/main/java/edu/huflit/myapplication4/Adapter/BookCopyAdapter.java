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

import edu.huflit.myapplication4.Entity.Copy;
import edu.huflit.myapplication4.Fragment.CopyBookAddFragment;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class BookCopyAdapter extends RecyclerView.Adapter<BookCopyAdapter.BookCopyVH> {
    Context context;
    ArrayList<Copy> copies;


    public BookCopyAdapter(Context applicationContext, ArrayList<Copy> copies) {
        this.context=applicationContext;
        this.copies = copies;
    }

    @NonNull
    @Override
    public BookCopyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookCopyVH(LayoutInflater.from(context).inflate(R.layout.copybook_item, parent, false));
    }

    int maxLength = 40;
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookCopyVH holder, @SuppressLint("RecyclerView") int position) {
        holder.bookCopyIdCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.instance.currentFragment = new CopyBookAddFragment(copies.get(position));
                MainActivity.instance.ReplaceFragment(-1);
                return true;
            }
        });
        holder.tvserial.setText(copies.get(position).getId());
        holder.tvidbook.setText(copies.get(position).getBookId());
        holder.tvbookstatus.setText(copies.get(position).getStatus());
        holder.tvnote.setText(copies.get(position).getNotes());
    }


    @Override
    public int getItemCount() {
        return copies.size();
    }

    static class BookCopyVH extends RecyclerView.ViewHolder {
        TextView tvserial, tvidbook, tvbookstatus, tvnote;
        LinearLayout bookCopyIdCard;
        public BookCopyVH(@NonNull View itemView) {
            super(itemView);
            bookCopyIdCard= itemView.findViewById(R.id.copybookId);
            tvserial= itemView.findViewById(R.id.tvserial);
            tvidbook= itemView.findViewById(R.id.tvidbook);
            tvbookstatus = itemView.findViewById(R.id.tvbookstatus);
            tvnote= itemView.findViewById(R.id.tvnote);
        }
    }
}
