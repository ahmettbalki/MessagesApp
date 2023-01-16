package com.example.messagesapp.ui.CreateGroup;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.messagesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateGroupFragment extends Fragment {

    EditText groupName, groupExplanation;
    RecyclerView groupsRecyclerView;
    Button createGroupButton;
    ImageView groupImage;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseStorage mStroge;


    Uri filePath;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_group, container, false);

        groupName = view.findViewById(R.id.groupName);
        groupExplanation = view.findViewById(R.id.groupExplanation);
        createGroupButton = view.findViewById(R.id.btn_creategroup);
        groupImage = view.findViewById(R.id.groupImage);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mStroge = FirebaseStorage.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        filePath = result.getData().getData();
                        groupImage.setImageURI(filePath);
                    }
                });
        groupImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
        createGroupButton.setOnClickListener(v -> {
            String name = groupName.getText().toString();
            String explanation = groupExplanation.getText().toString();
            String uid = mAuth.getCurrentUser().getUid();
            HashMap<String, Object> group = new HashMap<>();
            group.put("Grup Adı", name);
            group.put("Grup Açıklaması", explanation);
            group.put("Grup Resmi", uid);
            group.put("Numaralar", new ArrayList<String>());
            mStore.collection("gruplar").add(group);
        });


        return view;
    }

}