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

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sentButton.setOnClickListener( click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(messageText, currentDateAndTime, true);
            messages.add(chatMessage);
            messages = chatModel.messages.getValue();

            //refresh the list
            myAdapter.notifyItemChanged(messages.size()-1);

            //clear the previous text:
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener( click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(messageText, currentDateAndTime, false);
            messages.add(chatMessage);
            messages = chatModel.messages.getValue();

            //refresh the list
            myAdapter.notifyItemChanged(messages.size()-1);

            //clear the previous text:
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                View root;
                if (viewType == 0) {
                    root = getLayoutInflater().inflate(R.layout.sent_message, parent, false);
                } else {
                    root = getLayoutInflater().inflate(R.layout.receive_massage, parent, false);
                }
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
                //holder.messageText.setText("");
                //holder.timeText.setText("");

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