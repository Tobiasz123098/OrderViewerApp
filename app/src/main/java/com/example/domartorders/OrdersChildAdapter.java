package com.example.domartorders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrdersChildAdapter extends RecyclerView.Adapter<OrdersChildAdapter.ViewHolder> {

    private final List<PositionActivityMerge> dataSource;
    private final Context context;

    public OrdersChildAdapter(List<PositionActivityMerge> dataSource, Context context) {
        this.dataSource = dataSource;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orders_child_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersChildAdapter.ViewHolder holder, int position) {
        PositionActivityMerge item = dataSource.get(position);
        if (item != null) {
            holder.nazwa.setText(item.getNazwa());
            holder.ilosc.setText(String.valueOf(item.getIlosc()));
            holder.cena_brutto.setText(String.valueOf(item.getCena_brutto()));
            holder.waluta.setText(item.getWaluta());
            holder.cechy.setText(item.getCechy());

            holder.etap_produkcji.setText(item.getCzynnosc());

            if (holder.etap_produkcji.getText().equals("IN_QUEUE")) {
                holder.etap_produkcji.setText("W kolejce");
            } else if (holder.etap_produkcji.getText().equals("IN_PROGRESS")) {
                holder.etap_produkcji.setText("W trakcie");
            } else if (holder.etap_produkcji.getText().equals("DONE")) {
                holder.etap_produkcji.setText("Gotowe");
            } else if (holder.etap_produkcji.getText().equals("IN_STOCK")) {
                holder.etap_produkcji.setText("W magazynie");
            } else if (holder.etap_produkcji.getText().equals("SENT")) {
                holder.etap_produkcji.setText("Wysłane");
            } else if (holder.etap_produkcji.getText().equals("RETURNED")) {
                holder.etap_produkcji.setText("Wycofane");
            } else if (holder.etap_produkcji.getText().equals("PARTIALLY_WITHHELD")) {
                holder.etap_produkcji.setText("Częściowo wstrzymane");
            } else if (holder.etap_produkcji.getText().equals("WITHHELD")) {
                holder.etap_produkcji.setText("Wstrzymane");
            } else if (holder.etap_produkcji.getText().equals("PARTIALLY_PENDING")) {
                holder.etap_produkcji.setText("Częściowo oczekujące");
            } else if (holder.etap_produkcji.getText().equals("PENDING")) {
                holder.etap_produkcji.setText("Oczekujące");
            } else if (holder.etap_produkcji.getText().equals("CANCELLED")) {
                holder.etap_produkcji.setText("Anulowane");
            } else if (holder.etap_produkcji.getText().equals("STARTED")) {
                holder.etap_produkcji.setText("Rozpoczęte");
            } else if (holder.etap_produkcji.getText().equals("")) {
                holder.etap_produkcji.setText("brak");
            } else {
                holder.etap_produkcji.setText(item.getCzynnosc());
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nazwa, ilosc, cena_brutto, waluta, etap_produkcji, cechy;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nazwa = itemView.findViewById(R.id.nazwa);
            ilosc = itemView.findViewById(R.id.ilosc);
            cena_brutto = itemView.findViewById(R.id.cenaBrutto);
            waluta = itemView.findViewById(R.id.waluta);
            etap_produkcji = itemView.findViewById(R.id.etapProdukcji);
            cechy = itemView.findViewById(R.id.cechy);
        }
    }
}
