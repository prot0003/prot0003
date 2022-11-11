package algonquin.cst2335.prot0003;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.prot0003.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.prot0003.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    //  binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<myRowHolder>(){

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();
//          String input = binding.textInput.getText().toString();
//          messages.add(binding.textInput.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(messageText, currentDateandTime, true);
            messages.add(chatMessage);
            messages = chatModel.messages.getValue();
            myAdapter.notifyItemChanged(messages.size() - 1);
            //clear the previous text
            binding.textInput.setText("");
        });

        binding.button3.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(messageText, currentDateandTime, false);
            messages.add(chatMessage);
            messages = chatModel.messages.getValue();
            myAdapter.notifyItemChanged(messages.size() - 1);
            //clear the previous text
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View root;
                if (viewType == 0) {
                    root = getLayoutInflater().inflate(R.layout.sent_message, parent, false);
                }else {
                    root = getLayoutInflater().inflate(R.layout.receive_massage, parent, false);
                    //SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                }
                //    return new MyRowHolder(binding.getRoot());
                return new MyRowHolder( root );
            }
            @Override
            public int getItemViewType(int position) {
                ChatMessage thisMessage = messages.get(position);
                if (thisMessage.isSentButton == true) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//              String obj = messages.get(position);
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}