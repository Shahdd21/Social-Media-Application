import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRepository {
    private final Map<String, List<Message>> conversations = new HashMap<>();

    public Map<String, List<Message>> getConversations(){
        return conversations;
    }

    public boolean find(String key){
        return conversations.containsKey(key);
    }

    public void addConversation(String key, Message message){

        if(find(key)){
            conversations.get(key).add(message);
        }
        else{
            List<Message> list = new ArrayList<>();
            list.add(message);
            conversations.put(key,list);
        }
    }
}
