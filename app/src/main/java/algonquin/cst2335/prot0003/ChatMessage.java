package algonquin.cst2335.prot0003;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="TimeSent")
    protected String timeSent;
    @ColumnInfo(name="SendOrReceive")
    protected boolean isSentButton;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public ChatMessage(){ }

        ChatMessage(String m, String t, boolean sent){
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
    }
        public String getMessage(){
        return message;
    }
        public String getTimeSent(){
        return timeSent;
    }
    public boolean getIsSentButton(){
        return isSentButton;
    }

}
