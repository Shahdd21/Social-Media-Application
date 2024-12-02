import java.util.List;
import java.util.Map;

public class ChatManager implements ChatMediator{

    private final ChatRepository chatRepository;

    public ChatManager(Database database) {
        this.chatRepository = database.getChatRepository();
    }

    @Override
    public void sendDirectMessage(Message message, Profile sender, Profile recipient) {

        // Normalize key by ordering the usernames lexicographically
        String normalizedKey;
        if (sender.getUsername().compareTo(recipient.getUsername()) < 0) {
            normalizedKey = sender.getUsername() + "_" + recipient.getUsername();
        } else {
            normalizedKey = recipient.getUsername() + "_" + sender.getUsername();
        }

        chatRepository.addConversation(normalizedKey,message);
    }

    public boolean findConversation(String key){
        return chatRepository.find(key);
    }

    public Map<String, List<Message>> getConversations(){
        return chatRepository.getConversations();
    }

    public List<Message> getConversationsOf(String key){
        return chatRepository.getConversations().get(key);
    }
}
