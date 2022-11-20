package algonquin.cst2335.prot0003;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;


import java.util.List;
@Dao
public interface ChatMessageDAO {
    @Insert
     public void insertMessage(ChatMessage m);

    @Query ("Select * from ChatMessage")
    List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage m);
}
