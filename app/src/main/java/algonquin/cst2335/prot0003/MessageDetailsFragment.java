package algonquin.cst2335.prot0003;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.prot0003.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.prot0003.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;
    public MessageDetailsFragment(){

    }

    public MessageDetailsFragment(ChatMessage m) {
        this.selected = m;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @NonNull Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageText.setText(" The message is" + selected.message);
        binding.timeText.setText("The time is " + selected.timeSent);
       binding.sendReceive.setText("Send or Receive " + selected.isSentButton + "\n(SEND is true, Receive is false)");
        binding.databaseText.setText("id = " + selected.id);

        View view = binding.getRoot();
        view.setClickable(true);
        return view;
    }
}
