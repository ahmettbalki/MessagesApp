package com.example.messagesapp.CreateMessage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.messagesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateMessageFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    EditText messageName, message;
    Button createMessage;
    RecyclerView messagesRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_message, container, false);

        messageName = view.findViewById(R.id.messageName);
        message = view.findViewById(R.id.message);
        createMessage = view.findViewById(R.id.createMessage);
        messagesRecyclerView = view.findViewById(R.id.recyclerView);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        createMessage.setOnClickListener(view1 -> {
            String messageNameText = messageName.getText().toString();
            String messageText = message.getText().toString();

        if (messageNameText.isEmpty() || messageText.isEmpty()) {
                messageName.setError("Mesaj adı giriniz.");
                message.setError("Mesaj giriniz.");
        }
        createMessage(messageNameText, messageText);
        });
        return view;
    }
    public void createMessage(String messageNameText, String messageText) {
        String userId = mAuth.getCurrentUser().getUid();

        mStore.collection("/users/" + userId + "/messages").add(new HashMap<String, Object>() {{
            put("messageName", messageNameText);
            put("message", messageText);
        }});
    }
        // Required empty public constructor

}