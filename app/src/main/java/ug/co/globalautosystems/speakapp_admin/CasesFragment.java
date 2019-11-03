package ug.co.globalautosystems.speakapp_admin;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CasesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Context context;
    ProgressBar progressBar;
    ArrayList<Cases> casesArrayList;
    RecyclerView.LayoutManager layoutManager;

    public CasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cases, container, false);
        context = getContext();
        FirebaseApp.initializeApp(context);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressBar=root.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

         casesArrayList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("/cases");
        reference.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {

                    Log.d("Numbers", Objects.requireNonNull(dataSnapshot.getKey()));

                    Cases cases = new Cases(dataSnapshot.child("phone").getValue(String.class),
                            dataSnapshot.child("date").getValue(String.class),
                            dataSnapshot.child("casedate").getValue(String.class),
                            dataSnapshot.child("name").getValue(String.class),
                            "gender",
                            dataSnapshot.child("stature").getValue(String.class),
                            dataSnapshot.child("faculty").getValue(String.class),
                            dataSnapshot.child("nature").getValue(String.class),
                            dataSnapshot.child("location").getValue(String.class),
                            dataSnapshot.child("detail").getValue(String.class));

                    casesArrayList.add(cases);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new MyAdapter(context, casesArrayList);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        //recyclerView.addItemDecoration(itemDecorator, DividerItemDecoration.VERTICAL);

        return root;
    }

}
