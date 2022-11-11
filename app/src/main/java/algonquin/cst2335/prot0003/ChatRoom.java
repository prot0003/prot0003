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
    ActivityChatRoomBinding binding;
    RecyclerView.Adapter<MyRowHolder> adapter;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;
    private RecyclerView.Adapter myAdapter;

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
            myAdapter.notifyItemInserted(messages.size() - 1);
            //clear the previous text
            binding.textInput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
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
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                }
                //    return new MyRowHolder(binding.getRoot());
                return null;
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


//         ActivityChatRoomBinding binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
//         return new MyRowHolder(binding.getRoot());
//      }
//         @Override
//        public int getItemViewType(int position) {
//            if (messages.get(position).isSentButton)return 0;
//            return 1;
//        } binding = C.inflate(getLayoutInflater());
//        return new MyRowHolder(binding.getRoot());
//      }

//        @NonNull
//        @Override
//    public <ReceiveMessageBinding> MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    if (viewType == 0) {
//        SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
//        return new MyRowHolder(binding.getRoot());
//   }
//    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
//    return new MyRowHolder(binding.getRoot());
//   }


