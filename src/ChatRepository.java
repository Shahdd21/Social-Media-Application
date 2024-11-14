import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRepository {
    private static final Map<String, List<Message>> conversations = new HashMap<>();

    public static Map<String, List<Message>> getConversations(){
        return conversations;
    }

    public static boolean find(String key){
        return conversations.containsKey(key);
    }

    public static void addConversation(String key, Message message){

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
