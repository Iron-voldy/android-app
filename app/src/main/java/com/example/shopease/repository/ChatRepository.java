package com.example.shopease.repository;

import com.example.shopease.models.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import java.util.function.Consumer;
import com.google.firebase.database.GenericTypeIndicator;

public class ChatRepository {
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("chats");

    public void getMessages(Consumer<List<Message>> callback) {
        dbRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                List<Message> messages = task.getResult().getValue(new GenericTypeIndicator<List<Message>>() {});
                callback.accept(messages);
            }
        });
    }

    public void sendMessage(Message message) {
        dbRef.push().setValue(message);
    }
}
