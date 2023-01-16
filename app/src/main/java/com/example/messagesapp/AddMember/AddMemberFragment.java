package com.example.messagesapp.AddMember;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messagesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddMemberFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    RecyclerView recyclerView_addMember_groups, recyclerView_addMember_contacts;
    TextView addMember_groupName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_member, container, false);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        recyclerView_addMember_groups = view.findViewById(R.id.recyclerView_addMember_groups);
        recyclerView_addMember_contacts = view.findViewById(R.id.recyclerView_addMember_contacts);
        addMember_groupName = view.findViewById(R.id.addMember_groupName);

        return view;
    }
    private void fetchGroups() {
        ArrayList<String> groupNames = new ArrayList<>();
        ArrayList<String> groupIds = new ArrayList<>();

        mStore.collection("Groups").whereArrayContains("members", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    groupNames.add(documentSnapshot.getString("groupName"));
                    groupIds.add(documentSnapshot.getId());
                }
                AddMemberGroupAdapter addMemberGroupAdapter = new AddMemberGroupAdapter(groupNames, groupIds, getContext());
                recyclerView_addMember_groups.setAdapter(addMemberGroupAdapter);
                recyclerView_addMember_groups.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}