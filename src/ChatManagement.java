public class ChatManagement implements ChatMediator{
    @Override
    public void sendDirectMessage(Message message, Profile sender, Profile recipient) {

        Database database = Database.getInstance();
// Normalize key by ordering the usernames lexicographically
        String normalizedKey;
        if (sender.getUsername().compareTo(recipient.getUsername()) < 0) {
            normalizedKey = sender.getUsername() + "_" + recipient.getUsername();
        } else {
            normalizedKey = recipient.getUsername() + "_" + sender.getUsername();
        }

        database.addConversation(normalizedKey,message);
    }
}
