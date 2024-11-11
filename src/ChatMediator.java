public interface ChatMediator {
    void sendDirectMessage(Message message, Profile sender, Profile recipient);
}