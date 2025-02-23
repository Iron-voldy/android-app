package com.example.shopease;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopease.adapters.ChatAdapter;
import com.example.shopease.models.Message;
import com.example.shopease.repository.ChatRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ChatAdapter chatAdapter;
    private final ChatRepository chatRepository = new ChatRepository();
    private final List<Message> messages = new ArrayList<>();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, messages);
        chatRecyclerView.setAdapter(chatAdapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // ✅ Load messages from Firebase
        loadMessages();

        // ✅ Send message on button click
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void loadMessages() {
        chatRepository.getMessages(newMessages -> {
            messages.clear();
            messages.addAll(newMessages);
            runOnUiThread(() -> {
                chatAdapter.notifyDataSetChanged();
                chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
            });
        });
    }

    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        if (!text.isEmpty() && currentUser != null) {
            Message message = new Message(currentUser.getUid(), text);
            chatRepository.sendMessage(message);
            messageInput.setText("");
        }
    }
}
