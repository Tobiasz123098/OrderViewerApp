package com.example.domartorders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.parent_rv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, R.layout.spinner_item);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.dropdownitem);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        spinner.setBackgroundResource(R.drawable.blue_outline);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((OrdersAdapter) recyclerView.getAdapter()).getFilter().filter(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.action_bar)));
        getSupportActionBar().setLogo(R.drawable.logo);

        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        List<Order> infoList = new ArrayList<>();

        recyclerView.setAdapter(new OrdersAdapter(infoList, MainActivity.this));
        if (currentUser == null) {
            Toast.makeText(this, "Brak zamówień dla użytkownika - użytkownik nie istnieje ", Toast.LENGTH_LONG).show();
        } else {
            DatabaseReference orders = FirebaseDatabase.getInstance().getReference().child("orders").child(currentUser.getUid());
            orders.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    Order order = snapshot.getValue(Order.class);
                    infoList.add(order);
                    recyclerView.setAdapter(new OrdersAdapter(infoList, MainActivity.this));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    refreshView(currentUser.getUid());
                }

                @Override
                public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                    refreshView(currentUser.getUid());
                }

                @Override
                public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    refreshView(currentUser.getUid());
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
//            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    private void refreshView(String uid) {

        List<Order> infoList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("orders").child(uid).get().addOnCompleteListener(task -> {
            if (task.getResult() == null || !task.getResult().exists()) {
                Toast.makeText(this, "Brak zamówień dla użytkownika: " + uid, Toast.LENGTH_LONG).show();
                //infoList.add(new InformationForOrders("Brak zamówień dla użytkownika  : " + uid));
            } else {
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    Order value = dataSnapshot.getValue(Order.class);
                    infoList.add(value);
                }
            }
            recyclerView.setAdapter(new OrdersAdapter(infoList, MainActivity.this));
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Wyszukaj numer zamówienia");
        searchView.setInputType(InputType.TYPE_CLASS_PHONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((OrdersAdapter) recyclerView.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutItem) {
            Log.d(TAG, "Test");
            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(MainActivity.this, StartLoginActivity.class);
            startActivity(logoutIntent);
            MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void orderMethod(CzynnosciInformation order) {
//        FirebaseDatabase.getInstance().getReference().child("poz_zlec").child(String.valueOf(order.getPoz_id())).get().addOnCompleteListener(zlecTask -> {
//            for (DataSnapshot orderItemSnapshot : zlecTask.getResult().getChildren()) {
//                order.addPozZlecItems(orderItemSnapshot.getValue(PozZlecInformation.class));
//            }
//        });
//        FirebaseDatabase.getInstance().getReference().child("czynnosci").child(String.valueOf(order.getPoz_id())).get().addOnCompleteListener(task -> {
//            for (DataSnapshot itemSnapshot : task.getResult().getChildren()) {
//                order.addCzynnItems(itemSnapshot.getValue(CzynnosciInformation.class));
//            }
//        });
//    }
}




























/*
    orderInfo.getAdres_dostawy() + "\n" + orderInfo.getCena_brutto() + "\n" + orderInfo.getData_dost() + "\n" +
    orderInfo.getData_send() + "\n" + orderInfo.getData_transp() + "\n" + orderInfo.getData_zmiana() + "\n" +
    orderInfo.getForma_zap() + "\n" + orderInfo.getId() + "\n" + orderInfo.getNr() + "\n" +
    orderInfo.getOdbiorca() + "\n" + orderInfo.getPracownik() + "\n" + orderInfo.getRodzaj_dost() + "\n" +
    orderInfo.getStatus() + "\n" + orderInfo.getTermin_zap() + "\n" + orderInfo.getUwagi_dost() + "\n" +
    orderInfo.getWaluta() + "\n" + orderInfo.getWartosc_netto() + "\n" + orderInfo.isZaplacono()
 */

   /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InformationForOrders itemObject = (InformationForOrders) parent.getAdapter().getItem(position);
                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                listView.setAdapter(listViewAdapter);

                FirebaseDatabase.getInstance().getReference().child("orders").child(currentUser.getUid()).get().addOnCompleteListener(task -> {
                    listViewAdapter.add(itemObject.getAdres_dostawy() + "\n" + itemObject.getCena_brutto() + "\n" + itemObject.getData_dost() + "\n" +
                            itemObject.getData_send() + "\n" + itemObject.getData_transp() + "\n" + itemObject.getData_zmiana() + "\n" +
                            itemObject.getForma_zap() + "\n" + itemObject.getId() + "\n" + itemObject.getNr() + "\n" +
                            itemObject.getOdbiorca() + "\n" + itemObject.getPracownik() + "\n" + itemObject.getRodzaj_dost() + "\n" +
                            itemObject.getStatus() + "\n" + itemObject.getTermin_zap() + "\n" + itemObject.getUwagi_dost() + "\n" +
                            itemObject.getWaluta() + "\n" + itemObject.getWartosc_netto() + "\n" + itemObject.isZaplacono());

                    listViewAdapter.notifyDataSetChanged();
                });
            }
        });

         */

//gdy pozycja z ListView zostanie naciśnięta
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");
                Object item = parent.getAdapter().getItem(position);

                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
                listView.setAdapter(listViewAdapter);

                FirebaseDatabase.getInstance().getReference().child("orders").child("9Tjj7bvsvWYKcMJ73Jfrbtjk4ir1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        InformationForOrders informationForOrders = snapshot.getValue(InformationForOrders.class);
                        if (informationForOrders != null) {
                            listViewAdapter.add(item.toString() + "\n");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }
        });
        */