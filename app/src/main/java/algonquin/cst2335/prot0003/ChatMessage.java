package algonquin.cst2335.prot0003;
public class ChatMessage {
    private String message;
    private String timeSent ;
    boolean isSentButton ;
    ChatMessage(String m, String t, boolean sent){
        message = m;
        timeSent = t;
        isSentButton = sent;
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
