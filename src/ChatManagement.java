public class ChatManagement implements ChatMediator{
    @Override
    public void sendDirectMessage(Message message, Profile sender, Profile recipient) {
        String key = sender.getMember().getUserName() +"_"+ recipient.getMember().getUserName();
        ChatRepository.addConversation(key,message);
    }
}
