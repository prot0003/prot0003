package algonquin.cst2335.prot0003;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase{

    public abstract ChatMessageDAO cmDAO();

    }

