package algonquin.cst2335.prot0003;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.prot0003.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.prot0003.databinding.SentMessageBinding;
import algonquin.cst2335.prot0003.databinding.ReceiveMassageBinding;

public class ChatRoom extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatMessageDAO mDao;
    ChatMessage chatMessage;
    ChatRoomViewModel chatModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        setSupportActionBar(binding.myToolbar);

        //register a listener to the MutableLiveData Object, anytime observe when rotation
        chatModel.selectedMessage.observe(this, newMessageValue -> {
            //load the fragment
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();

            MessageDetailsFragment detailsFragment = new MessageDetailsFragment( newMessageValue);
            tx.replace(R.id.fragmentLocation, detailsFragment);
            tx.addToBackStack("anything you want here"); //means that the back arrow undones the transaction
            tx.commit(); //This line actually loads the fragment into the specified FrameLayout

            /*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, new MessageDetailsFragment(newMessageValue))
                    .commit();
             */
        });
        // load from database:
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "chat-message").build();
        mDao = db.cmDAO();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
            //load everything:
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                //whatever is in here runs on another processor.
                messages.addAll(mDao.getAllMessages());
                //now you can load the RecyclerVIew:
                runOnUiThread(() -> {
                    binding.recycleView.setAdapter(myAdapter);
                });
            });
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sentButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            chatMessage = new ChatMessage(messageText, currentDateAndTime, true);
            messages.add(chatMessage);
            //messages = chatModel.messages.getValue();

            //refresh the list
            myAdapter.notifyItemChanged(messages.size() - 1);

            //clear the previous text:
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {

                //  binding.recycleView.setAdapter( myAdapter );
                long last = mDao.insertMessage(chatMessage);
                chatMessage.id = (int) last;
            });
        });

        binding.receiveButton.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            chatMessage = new ChatMessage(messageText, currentDateAndTime, false);
            messages.add(chatMessage);
            messages = chatModel.messages.getValue();

            //refresh the list
            myAdapter.notifyItemChanged(messages.size() - 1);

            //clear the previous text:
            binding.textInput.setText("");

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                long last = mDao.insertMessage(chatMessage);
                chatMessage.id = (int) last;
            });
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
                return new MyRowHolder(root);
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

            itemView.setOnClickListener(click ->{
                int position = getAbsoluteAdapterPosition();
                ChatMessage thisMessage = messages.get(position);
                chatModel.selectedMessage.postValue(thisMessage);

                /* comment this for now, will use it next week
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, clk) -> {})
                        .setPositiveButton("Yes", (dialog, clk) -> {

                         // Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG);
                            Snackbar.make(messageText, "You deleted message " + messageText.getText(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", clkUndo -> {
                                        Executor thread = Executors.newSingleThreadExecutor();
                                        thread.execute(() -> {
                                            mDao.insertMessage(thisMessage);
                                        });
                                       chatModel.messages.getValue().add(position,thisMessage)
                                                    myAdapter.notifyItemInserted(position);
                                    }).show();

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                mDao.deleteMessage(thisMessage);
                            });
                            myAdapter.notifyItemRemoved(position);
                            chatModel.messages.getValue().remove(position);
                      })
                        .create().show();

                 */
            });

        }
    }
}