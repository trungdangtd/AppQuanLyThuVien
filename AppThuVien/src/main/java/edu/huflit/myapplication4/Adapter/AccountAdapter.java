package edu.huflit.myapplication4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.huflit.myapplication4.BookstoreProjectDatabase;
import edu.huflit.myapplication4.Entity.Account;
import edu.huflit.myapplication4.Fragment.UpdateAccountFragment;
import edu.huflit.myapplication4.MainActivity;
import edu.huflit.myapplication4.R;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountVH>{

    Context context;
    ArrayList<Account> accounts;

    public AccountAdapter(Context applicationContext, ArrayList<Account> accounts) {
        this.context=applicationContext;
        this.accounts=accounts;
    }

    @NonNull
    @Override
    public AccountAdapter.AccountVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountVH(LayoutInflater.from(context).inflate(R.layout.account_manage_item, parent, false));
    }
    int maxLength = 40;
    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountVH holder, @SuppressLint("RecyclerView") int position) {
        holder.accCard.setOnLongClickListener(new View.OnLongClickListener() {
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
                        MainActivity.instance.currentFragment = new UpdateAccountFragment(accounts.get(position));
                        MainActivity.instance.ReplaceFragment(-1);
                        popup.dismiss();
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookstoreProjectDatabase.DeleteAccount(accounts.get(position).getAccount());
                        BookstoreProjectDatabase.DeleteLibraryCard(accounts.get(position).getAccount());
                        BookstoreProjectDatabase.LoadAccounts("Sinh viên");
                        accounts.remove(accounts.get(position));
                        notifyDataSetChanged();
                        popup.dismiss();
                    }
                });

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

        holder.nameAc.setText(accounts.get(position).getAccount());
        holder.passWordac.setText(accounts.get(position).getPassword());
        holder.roleAc.setText(accounts.get(position).getRole());
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class AccountVH extends RecyclerView.ViewHolder {
        LinearLayout accCard;
        TextView roleAc;
        TextView passWordac ;
        TextView nameAc;
        public AccountVH(@NonNull View itemView) {
            super(itemView);
            accCard = itemView.findViewById(R.id.accountcard);
            nameAc=itemView.findViewById(R.id.Nameacmanage);
            roleAc = itemView.findViewById(R.id.roleac);
            passWordac=itemView.findViewById(R.id.passwordAc);
        }
    }
}
