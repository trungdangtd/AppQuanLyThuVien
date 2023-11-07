package edu.huflit.myapplication4.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.huflit.myapplication4.Entity.Nofication;
import edu.huflit.myapplication4.R;

public class NoficationAdapter extends RecyclerView.Adapter<NoficationAdapter.NoficationVH> {
    Context context;
    ArrayList<Nofication> nofications;

    public NoficationAdapter(Context context, ArrayList<Nofication> nofications) {
        this.context = context;
        this.nofications = nofications;
    }

    @NonNull
    @Override
    public NoficationAdapter.NoficationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoficationVH(LayoutInflater.from(context).inflate(R.layout.nofication_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoficationAdapter.NoficationVH holder, int position) {
        switch (nofications.get(position).getTitle())
        {
            case "Cấm":
                holder.nofiCart.setBackgroundColor(Color.RED);
                break;
            case "Cảnh cáo":
                holder.nofiCart.setBackgroundColor(Color.YELLOW);
                break;
            case "Bình thường":
                holder.nofiCart.setBackgroundColor(Color.GREEN);
                break;
        }
        holder.title.setText(nofications.get(position).getTitle());
        holder.content.setText(nofications.get(position).getContent());
        holder.time.setText(nofications.get(position).getDateUpdate());
    }

    @Override
    public int getItemCount() {
        return nofications.size();
    }

    public class NoficationVH extends RecyclerView.ViewHolder {
        LinearLayout nofiCart;
        TextView title;
        TextView content ;
        TextView time;
        public NoficationVH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content=itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            nofiCart = itemView.findViewById(R.id.noficart);
        }
    }
}
