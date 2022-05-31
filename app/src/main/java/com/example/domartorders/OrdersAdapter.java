package com.example.domartorders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> implements Filterable {

    private static final String TAG = OrdersAdapter.class.getSimpleName();

    private List<Order> infoList;
    private List<Order> infoListAll;
    private Context context;

    public OrdersAdapter(List<Order> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
        infoListAll = new ArrayList<>();
        infoListAll.addAll(infoList);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orders_adapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Order order = infoList.get(position);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.child_rv.setLayoutManager(layoutManager);

        loadOrderDetails(order.getId(), holder);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.RotateIn)
                        .duration(200)
                        .repeat(0)
                        .playOn(holder.imageButton);

                order.setExpanded(!order.isExpanded());
                notifyItemChanged(position);
            }
        });

        holder.orderNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setExpanded(!order.isExpanded());
                notifyItemChanged(position);
            }
        });

        //tłumaczenie do statusu
        holder.statusTextView.setText(order.getStatus());

        if (holder.statusTextView.getText().equals("READY") | holder.statusTextView.getText().equals("IN_STOCK")) {
            holder.statusTextView.setText("Gotowe");
        } else if (holder.statusTextView.getText().equals("PARTIALLY_SENT")) {
            holder.statusTextView.setText("Częściowo wyjechało");
        } else if (holder.statusTextView.getText().equals("SENT")) {
            holder.statusTextView.setText("Wyjechało");
        } else if (holder.statusTextView.getText().equals("IN_PROGRESS") | holder.statusTextView.getText().equals("MODIFIED") | holder.statusTextView.getText().equals("DURING_THE_WITHDRAWAL") | holder.statusTextView.getText().equals("PLACED")) {
            holder.statusTextView.setText("W trakcie realizacji");
        } else if (holder.statusTextView.getText().equals("WITHDRAWN")) {
            holder.statusTextView.setText("Zakończone");
        } else {
            holder.statusTextView.setText(order.getStatus());
        }

        //tłumaczenie do radzaju dostawy
        holder.rodzajDostawyTextView.setText(order.getRodzaj_dost());

        if (holder.rodzajDostawyTextView.getText().equals("COURIER")) {
            holder.rodzajDostawyTextView.setText("Wysyłka kurierem");
        } else if (holder.rodzajDostawyTextView.getText().equals("COURIER_DOMARTSTYL")) {
            holder.rodzajDostawyTextView.setText("Transport DomArtStyl");
        } else if (holder.rodzajDostawyTextView.getText().equals("COLLECT_IN_PERSON")) {
            holder.rodzajDostawyTextView.setText("Odbiór osobisty");
        } else if (holder.rodzajDostawyTextView.getText().equals("CUSTOMER_COURIER")) {
            holder.rodzajDostawyTextView.setText("Odbiór kurierem");
        } else if (holder.rodzajDostawyTextView.getText().equals("TO_BE_DETERMINED")) {
            holder.rodzajDostawyTextView.setText("Do ustalenia");
        } else {
            holder.rodzajDostawyTextView.setText(order.getRodzaj_dost());
        }

        //wyświetlanie "brak" przy formie zapłaty
        holder.formaZaplatyTextView.setText(order.getForma_zap());

        if (holder.formaZaplatyTextView.getText().equals("")) {
            holder.formaZaplatyTextView.setText("brak");
        } else {
            holder.formaZaplatyTextView.setText(order.getForma_zap());
        }


//napisać do tego klasę zamiast wypisywać tyle if-ów
        if (holder.titleTextView.getText().equals("")) {
            holder.titleTextView.setText("----");
        } else {
            holder.titleTextView.setText(order.getNr());
        }
        if (holder.adresDostawyTextView.getText().equals("")) {
            holder.adresDostawyTextView.setText("----");
        } else {
            holder.adresDostawyTextView.setText(order.getAdres_dostawy());
        }
        if (holder.cenaBruttoTextView.getText().equals("")) {
            holder.cenaBruttoTextView.setText("----");
        } else {
            holder.cenaBruttoTextView.setText(String.valueOf(order.getCena_brutto()));
        }
        if (holder.dataZmianaTextView.getText().length() == 0) {
            holder.dataZmianaTextView.setText("brak");
        } else {
            holder.dataZmianaTextView.setText(order.getData_zmiana());
        }
        if (holder.odbiorcaTextView.getText().length() == 0) {
            holder.odbiorcaTextView.setText("brak");
        } else {
            holder.odbiorcaTextView.setText(order.getOdbiorca());
        }
//        if (holder.terminZaplatyTextView.getText().equals("")) {
//            holder.terminZaplatyTextView.setText("----");
//        } else {
//            holder.terminZaplatyTextView.setText(String.valueOf(information.getTermin_zap()));
//        }
        if (holder.walutaTextView.getText().equals("")) {
            holder.walutaTextView.setText("----");
        } else {
            holder.walutaTextView.setText(order.getWaluta());
        }

        boolean isExpanded = infoList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + infoList.size());
        return infoList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults filterResults = new FilterResults();
            //charSequence.toString().isEmpty()
            if (charSequence == null || charSequence.length() == 0) {
                filterResults.values = infoListAll;
            } else {
                String search = charSequence.toString().toLowerCase().trim();
                filterResults.values = infoListAll.stream()
                        .filter(item -> (item.getNr() != null && item.getNr().toLowerCase().contains(search))
                                || (item.getStatus() != null && StatusEnum.getRelatedStatuses(search).contains(item.getStatus())))
                        .collect(Collectors.toList());
            }
            return filterResults;
        }

        //run on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            infoList.clear();
            infoList.addAll((Collection<? extends Order>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout expandableLayout;
        ImageButton imageButton;
        TextView titleTextView, adresDostawyTextView, cenaBruttoTextView, dataZmianaTextView, formaZaplatyTextView,
                odbiorcaTextView, rodzajDostawyTextView, statusTextView, walutaTextView, orderNumberTextView;
        RecyclerView child_rv;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: ");
            orderNumberTextView = itemView.findViewById(R.id.orderNumberTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            titleTextView = itemView.findViewById(R.id.textView1);
            adresDostawyTextView = itemView.findViewById(R.id.adresTextView);
            cenaBruttoTextView = itemView.findViewById(R.id.plotTextView);
            dataZmianaTextView = itemView.findViewById(R.id.data_przyjecia_zamowienia);
            formaZaplatyTextView = itemView.findViewById(R.id.formaZaplatyTextView_validate);
            odbiorcaTextView = itemView.findViewById(R.id.zamawiajacy);
            rodzajDostawyTextView = itemView.findViewById(R.id.rodzajDostawyText_validate);
            statusTextView = itemView.findViewById(R.id.textView25);
            walutaTextView = itemView.findViewById(R.id.walutaTextView_validate);
            child_rv = itemView.findViewById(R.id.child_rv);

            imageButton = itemView.findViewById(R.id.imageButton);

        }
    }

    private void loadOrderDetails(long orderId, ViewHolder holder) {
        List<OrderActivity> orderActivityList = new ArrayList<>();
        List<PositionActivityMerge> mergedList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("czynnosci").child(String.valueOf(orderId)).get().addOnCompleteListener(task -> {
            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
                orderActivityList.add(itemSnapshot.getValue(OrderActivity.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("poz_zlec").child(String.valueOf(orderId)).get().addOnCompleteListener(task -> {
            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
                OrderPosition orderPosition = itemSnapshot.getValue(OrderPosition.class);
                mergedList.add(new PositionActivityMerge(itemSnapshot.getValue(OrderPosition.class), findActivity(orderPosition.getZlec_id(), orderActivityList)));
            }
            holder.child_rv.setAdapter(new OrdersChildAdapter(mergedList, holder.child_rv.getContext()));
        });
    }

    private OrderActivity findActivity(long positionId, List<OrderActivity> orderActivities) {
        return orderActivities.stream().filter(orderActivity -> orderActivity.getZlec_id() == positionId).findFirst().orElse(null);
    }
}

