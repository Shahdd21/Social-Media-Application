import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static Database database = Database.getInstance();
    public static ChatManager chatManager = new ChatManager(database);
    public static UserManager userManager = new UserManager(database);
    public static PostManager postManager = new PostManager(database);
    public static NotificationManager notificationManager = new NotificationManager(database);
    public static PageManager pageManager = new PageManager(database);

    public static void main(String[] args) {

        Member member = new Member("shawerma","Shahd", "Mahmoud",
                "shahd@gmail.com", "0123456789","123", false);
        ProfileManager.createProfile(member);
        userManager.addMember("shawerma",member);

        while(true) {
            startingMenu();
        }
    }

    public static void startingMenu(){
        System.out.println("\t 1:Register");
        System.out.println("\t 2:Login");

        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) register();
        else if(choice == 2) login();
        else System.out.println("Incorrect Input.");
    }

    public static void register(){
        Member member = new Member();

        System.out.println("First Name: ");
        member.setFirstName(input.nextLine());

        System.out.println("Last Name: ");
        member.setLastName(input.nextLine());

        System.out.println("Username: ");
        member.setUserName(input.nextLine());

        System.out.println("E-mail: ");
        member.setEmail(input.nextLine());

        System.out.println("Phone number: ");
        member.setPhoneNumber(input.nextLine());

        String password, confirmedPassword;

        System.out.println("Password: ");
        password = input.nextLine();

        System.out.println("Confirm Password: ");
        confirmedPassword = input.nextLine();

        while(!password.equals(confirmedPassword)) {
            System.out.println("Passwords do not match");
            System.out.println("Confirm Password: ");
            confirmedPassword = input.nextLine();
        }

        member.setPassword(password);

        ProfileManager.createProfile(member);
        userManager.addMember(member.getUserName(), member);
        System.out.println("You joined the Family !");

        mainMenu(member);
    }

    public static void login(){

        System.out.println("Username: ");
        String userName = input.nextLine();

        System.out.println("Password: ");
        String password = input.nextLine();

        if (userManager.isFoundMember(userName)){
            Member member = userManager.getMember(userName);

            if(member.getPassword().equals(password)) {
                //if(member.isAdmin()) adminMenu(member);

                 mainMenu(member);
            }

            else {

                System.out.println("Wrong username or password");

                System.out.println("Forgot your password ? Y/N");
                String c = input.nextLine();

                if (c.equals("Y")) recoverPassword(member);
                else return;
            }
        }
        else{
            System.out.println("User not found");
        }
    }

    public static void recoverPassword(Member member){
        System.out.println("Enter your new password: ");
        String password = input.nextLine();

        System.out.println("Confirm your password: ");
        String confirmedPassword = input.nextLine();

        while(!password.equals(confirmedPassword)) {
            System.out.println("Passwords do not match");
            System.out.println("Confirm Password: ");
            confirmedPassword = input.nextLine();
        }

        member.setPassword(confirmedPassword);

    }

    public static void mainMenu(Member member){

        while(true) {
            System.out.printf("Hello, %s !\n", member.getFirstName());

            System.out.print("\t1) Profile");
            System.out.print("\t\t\t\t2) Feed");
            System.out.println("\t\t\t\t3) Inbox");
            System.out.print("\t4) Create post");
            System.out.print("\t\t\t5) Explore");
            System.out.println("\t\t\t6) View Friends");
            System.out.print("\t7) View Friend Requests");
            System.out.print("\t8) Manage Pages");
            System.out.println("\t\t9) Notification");
            System.out.print("\t10) Search");
            System.out.println("\t\t\t\t11) Log out");

            System.out.println("Choose between 1-11: ");
            int ans = input.nextInt();
            input.nextLine();

            switch (ans) {
                case 1:
                    openProfile(member);
                    break;

                case 2:
                    openFeed(member);
                    break;

                case 3:
                    openInbox(member);
                    break;

                case 4:
                    createPost(member.getProfile());
                    break;

                case 5:
                    explore(member);
                    break;

                case 6:
                    viewFriends(member);
                    break;

                case 7:
                    viewFriendRequests(member);
                    break;

                case 8:
                    managePages(member);
                    break;

                case 9:
                    openNotification(member);
                    break;

                case 10:
                    search(member);
                    break;

                case 11:  return;
            }
        }
    }

    public static void openInbox(Member member){

        NotificationManager notificationManager = new NotificationManager(database);

        System.out.println("Chats: ");
        String memberUsername = member.getUserName();

        Map<String, List<Message>> conversations = chatManager.getConversations();

        conversations.forEach((k,v) -> {
            if(k.contains(memberUsername)){
                String[] usernames = k.split("_");
                String friendUsername = (usernames[0].equals(memberUsername) ? usernames[1] : usernames[0] );
                System.out.println("\u2022"+friendUsername);
                System.out.println(v.get(v.size()-1).getContent());
                System.out.println("******************************************");
            }
        });

        System.out.println("1: Open Chat 2: Add new Chat");
        int ans = input.nextInt();
        input.nextLine();

        if(ans == 1){
            System.out.println("Enter the username of the chat you want to open: ");
            String friendUsername = input.nextLine();
            String combination = (friendUsername.compareTo(memberUsername) < 0 ? friendUsername+"_"+memberUsername :
                    memberUsername+"_"+friendUsername);

            conversations.forEach((k,v) -> {
                if(k.equals(combination)){
                    for (Message message : v) {
                        if(!message.getSender().getUsername().equals(memberUsername)){
                        System.out.println(message.getSender().getUsername());
                        System.out.println(message.getContent());
                        }
                        else System.out.println("\t\t\t"+message.getContent());

                        System.out.println(message.getTimestamp());
                    }
                }
            });

            while(true) {
                System.out.println("1: Send a message 2: Return");
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    System.out.println("Write your message: ");
                    String content = input.nextLine();
                    Message message = new Message(content, ProfileManager.getProfileByUsername(friendUsername),
                            ProfileManager.getProfileByUsername(memberUsername));

                    chatManager.sendDirectMessage(message, ProfileManager.getProfileByUsername(memberUsername),
                            ProfileManager.getProfileByUsername(friendUsername));

                    notificationManager.sendNotification(member.getProfile().getProfileId(),
                            ProfileManager.getProfileByUsername(friendUsername).getProfileId(),
                            EventType.NEW_MESSAGE, member.getFirstName()+" "+
                                    member.getLastName()+" sent you a message");
                } else {
                    break;
                }
            }
        }
        else if (ans == 2) {
            for(Profile friend : userManager.getFriendsList(member.getProfile())){
                System.out.printf("%s %s (%s)\n", friend.getFirstName(), friend.getLastName(),
                        friend.getUsername());
            }

            System.out.println("Write the username you want to send a message to: ");
            String friendUsername = input.nextLine();

            if(userManager.isFoundMember(friendUsername)){
                if(userManager.getFriendsList(member.getProfile())
                        .contains(ProfileManager.getProfileByUsername(friendUsername))){
                    System.out.println("Write your first message: ");
                    String content = input.nextLine();

                    Message message = new Message(content, ProfileManager.getProfileByUsername(friendUsername),
                            member.getProfile());

                    chatManager.sendDirectMessage(message, ProfileManager.getProfileByUsername(memberUsername),
                            ProfileManager.getProfileByUsername(friendUsername));

                    notificationManager.sendNotification(member.getProfile().getProfileId(),
                            ProfileManager.getProfileByUsername(friendUsername).getProfileId(),
                            EventType.NEW_MESSAGE, member.getFirstName()+" "+
                                    member.getLastName()+" sent you a message");
                }

                else System.out.println("The user is not in your friends list");
            }
            else System.out.println("The user does not exist");
        }
    }

    public static void viewFriendRequests(Member member){

        Profile memberProfile = member.getProfile();

        if(!userManager.getPendingFriendsMap().containsKey(memberProfile) || userManager.getPendingFriendsList(memberProfile).isEmpty())
            System.out.println("No friend requests to show.");

        else{
            List<Profile> pendingList = userManager.getPendingFriendsList(memberProfile);

        for (int i = 0; i < pendingList.size(); ++i) {
                Profile friendProfile = pendingList.get(i);

                System.out.printf("%s %s (%s)\n", friendProfile.getFirstName(), friendProfile.getLastName(),
                        friendProfile.getUsername());

                System.out.println("1: Accept 2:Delete 3:Nothing");
                int ans = input.nextInt();
                input.nextLine();

                if (ans == 1) {
                    List<Profile> memberList = userManager.getFriendsMap().getOrDefault(memberProfile, new ArrayList<>());
                    memberList.add(friendProfile);
                    userManager.getFriendsMap().put(memberProfile, memberList);

                    userManager.getPendingFriendsList(memberProfile).remove(friendProfile);

                    List<Profile> friendList = userManager.getFriendsMap().getOrDefault(friendProfile, new ArrayList<>());
                    friendList.add(memberProfile);
                    userManager.getFriendsMap().put(friendProfile, friendList);

                    NotificationManager notificationManager = new NotificationManager(database);

                    notificationManager.sendNotification(memberProfile.getID(), friendProfile.getProfileId(),
                            EventType.FRIEND_REQUEST, member.getFirstName() + " " + member.getLastName()
                                    + " accepted your friend request. You're friends now !");

                } else if (ans == 2) {
                    userManager.getPendingFriendsList(memberProfile).remove(friendProfile);
                } else if (ans == 3) continue;
                else System.out.println("Incorrect input.");
            }
        }
    }

//    public static void adminMenu(Member member){
//        while(true) {
//            System.out.printf("Hello, %s !\n", member.getFirstName());
//
//            System.out.println("\t\u2022 Profile");
//            System.out.println("\t\u2022 Feed");
//            System.out.println("\t\u2022 Create post");
//            System.out.println("\t\u2022 Add Friends");
//            System.out.println("\t\u2022 View Friends");
//            System.out.println("\t\u2022 View Friend Requests");
//            System.out.println("\t\u2022 Manage Reports");
//            System.out.println("\t\u2022 Log out");
//
//            System.out.println("Choose between 1-8: ");
//            int ans = input.nextInt();
//            input.nextLine();
//
//            switch (ans) {
//                case 1:
//                    openProfile(member);
//                    break;
//
//                case 2:
//                    openFeed(member);
//                    break;
//
//                case 3:
//                    createPost(member);
//                    break;
//
//                case 4:
//                    explore(member);
//                    break;
//
//                case 5:
//                    viewFriends(member);
//                    break;
//
//                case 6:
//                    manageReports(member);
//
//                case 7:
//                    viewFriendRequests(member);
//
//                case 8:  return;
//            }
//        }
//    }

//    public static void manageReports(Member admin){
//
//        while(true) {
//            System.out.println("Reports: ");
//            ReportRepository.displayReports(admin);
//
//            System.out.println("Enter the report ID you want to process: ");
//            String reportId = input.nextLine();
//
//        }
//    }

    public static void openFeed(Member member) {

        Profile memberProfile = member.getProfile();
        while (true) {
            System.out.println("Here is Feed !");
            System.out.println();

            if (userManager.getFriendsMap().containsKey(member.getProfile()) && !userManager.getFriendsList(member.getProfile()).isEmpty()) {
                List<Profile> friendProfiles = userManager.getFriendsList(member.getProfile());

                for(Profile friendProfile : friendProfiles){
                    postManager.displayPosts(friendProfile);
                }

                System.out.println("Interact with the post:\n1: Like 2: Comment 3:Share 4:Report 5:Nothing");
                int ans = input.nextInt();
                input.nextLine();

                if(ans != 5){
                    String notificationMessage = null;

                    System.out.println("Enter the Post ID: ");
                    String postId = input.nextLine();

                    Post post = postManager.getPostById(postId);

                    if(ans == 1) {
                        postManager.addLike(post, memberProfile);
                        notificationMessage = memberProfile.getFullName()+" liked your post with ID: "+postId;
                    }

                    if(ans == 2) {
                        Comment comment = new Comment();

                        System.out.println("Enter your comment: ");
                        String commentContent = input.nextLine();

                        comment.setContent(commentContent);
                        comment.setPost(post);
                        comment.setAuthorProfile(member.getProfile());

                        postManager.addComment(post,comment);
                        notificationMessage = memberProfile.getFullName()+" commented on your post with ID: "+postId;
                    }

                    if(ans == 3){
                        postManager.addPost(member.getProfile(),post);
                        notificationMessage = memberProfile.getFullName()+" shared your post with ID: "+postId;
                    }

                    if(ans == 4){
                        Report report = new Report();

                        System.out.println("choose the reason of your report: ");
                        System.out.println("1- Pornographic content");
                        System.out.println("2- Fake Account");
                        System.out.println("3- Harassment");
                        System.out.println("4- Disturbing content");
                        System.out.println("5- Scam");

                        int reason = input.nextInt();
                        input.nextLine();

                        switch (reason){
                            case 1:
                                report.setReportCause(ReportCause.PORNOGRAPHIC_CONTENT);
                                break;

                            case 2:
                                report.setReportCause(ReportCause.FAKE_ACCOUNT);
                                break;

                            case 3:
                                report.setReportCause(ReportCause.HARASSMENT);
                                break;

                            case 4:
                                report.setReportCause(ReportCause.DISTURBING_CONTENT);
                                break;

                            case 5:
                                report.setReportCause(ReportCause.SCAM);
                                break;
                        }

                        //report.setReportedEntity(post);
                        report.setReportingProfile(member.getProfile());

                        ReportRepository.addReport(report);

                        System.out.println("Report is made successfully !");
                    }

                    notificationManager.sendNotification(memberProfile.getProfileId(), post.getCreatorId(),
                            EventType.POST_INTERACTION, notificationMessage);
                }
            }

            if(userManager.getFollowingMap().containsKey(member.getProfile()) && !userManager.getFollowingList(member.getProfile()).isEmpty()){

                List<FollowedEntity> followingList = userManager.getFollowingList(member.getProfile());

                for(FollowedEntity followedEntity : followingList){
                    postManager.displayPosts(followedEntity);
                }

                System.out.println("Interact with the post:\n1: Like 2:Share 3:Nothing");
                int ans = input.nextInt();
                input.nextLine();

                if (ans != 3){
                    String notificationMessage = null;

                    System.out.println("Enter the Post ID: ");
                    String postId = input.nextLine();

                    Post post = postManager.getPostById(postId);

                    if(ans == 1){
                        postManager.addLike(post, member.getProfile());
                        notificationMessage = memberProfile.getFullName() + " liked your post with ID: "+ postId;
                    }

                    if(ans == 2){
                        postManager.addPost(member.getProfile(), post);
                        notificationMessage = memberProfile.getFullName() +" shared your post with ID:"+ postId;
                    }

                    notificationManager.sendNotification(memberProfile.getProfileId(), post.getCreatorId(),
                            EventType.POST_INTERACTION, notificationMessage);
                }
            }

            else {
                if(postManager.getPostsMap().containsKey(member.getProfile())
                        &&!postManager.getPostsList(member.getProfile()).isEmpty()) {
                    postManager.displayPosts(member.getProfile());
                }
                else System.out.println("No posts to display.");
            }

            System.out.println("Return to Main Menu? Y/N");
            String c = input.nextLine();

            if(c.equals("Y")) return;
        }
    }

    public static void createPost(FollowedEntity followedEntity){

        Post post = new Post(followedEntity);
        System.out.println("What's on your mind? ");
        String content = input.nextLine();

        post.setContent(content);

        postManager.addPost(followedEntity, post);
    }

    public static void openProfile(Member member){

        Profile profile = member.getProfile();

        System.out.println("\t\t\t Bio ");
        System.out.println("\t\t"+ profile.getBio());
        System.out.println("---------------------------------------------");
        System.out.println("\u2022 School: "+ profile.getSchool());
        System.out.println("\u2022 Work: "+ profile.getWork());
        System.out.println("\u2022 City: "+ profile.getCity());
        System.out.println("\u2022 Country: "+ profile.getCountry());
        System.out.println("\u2022 Gender: "+ profile.getGender());
        System.out.println("\u2022 Religion: "+ profile.getReligion());
        System.out.println("\u2022 Interested in: "+ profile.getSexPreference());
        System.out.println("\u2022 Birthdate: "+ profile.getBirthdate());
        System.out.println("\u2022 Join Date: "+ profile.getJoinDate());
        System.out.println("---------------------------------------------");
        postManager.displayPosts(profile);
        System.out.println("---------------------------------------------");
        System.out.println("Want to edit your profile? Y/N");

        String ans = input.nextLine();

        if(ans.equals("Y")) {
            boolean success = editProfile(member);
            if(success) System.out.println("Edits Saved !");
            else System.out.println("There might have occurred some issues while saving your edits. Please, Try again");
        }
    }

    public static boolean editProfile(Member member){
        Profile profile = member.getProfile();

        while(true) {
            System.out.println("1-Edit Bio");
            System.out.println("2-Edit School");
            System.out.println("3-Edit Work");
            System.out.println("4-Edit Gender");
            System.out.println("5-Edit Religion");
            System.out.println("6-Edit City");
            System.out.println("7-Edit Country");
            System.out.println("8-Edit Birth Date");
            System.out.println("9-Edit Interested in");
            System.out.println("Choose from 1-9 to edit and -1 to cancel");

            int ans = input.nextInt();
            input.nextLine();

            if(ans == -1) break;

            switch (ans){
                case 1:{
                    System.out.println("Write your thoughts: ");
                    String bio = input.nextLine();
                    profile.setBio(bio);
                }
                break;

                case 2:{
                    System.out.println("Enter School/University: ");
                    String school = input.nextLine();
                    profile.setSchool(school);
                }
                break;

                case 3:{
                    System.out.println("Enter work place/position: ");
                    String work = input.nextLine();
                    profile.setWork(work);
                }
                break;

                case 4:{
                    System.out.println("Enter Gender: ");
                    String gender = input.nextLine();
                    profile.setGender(gender);
                }
                break;

                case 5:{
                    System.out.println("Enter Religion: ");
                    String religion = input.nextLine();
                    profile.setReligion(religion);
                }
                break;

                case 6:{
                    System.out.println("Enter City: ");
                    String city = input.nextLine();
                    profile.setCity(city);
                }
                break;

                case 7:{
                    System.out.println("Enter country: ");
                    String country = input.nextLine();
                    profile.setCountry(country);
                }
                break;

                case 8:{
                    System.out.println("Enter Birth Date: ");
                    String birthdate = input.nextLine();
                    profile.setBirthdate(birthdate);
                }
                break;

                case 9:{
                    System.out.println("Enter Sex Preference: ");
                    String sexPreference = input.nextLine();
                    profile.setSexPreference(sexPreference);
                }
            }
        }

        return true;
    }

    public static void explore(Member searcher){

        //people exploring
        Map<String, Member> memberMap = userManager.getMembersRepo();

        System.out.println("**People**");
        for(Map.Entry<String,Member> entry: memberMap.entrySet()){
            String username = entry.getKey();
            Member member = entry.getValue();

            if(searcher == member) continue;

            System.out.printf("%s %s (%s)\n", member.getFirstName(), member.getLastName(), username);
        }

        //pages exploring
        Map<String, Page> pagesMap = pageManager.getPages();

        if(pagesMap != null){
            System.out.println("**Pages**");
            for(Map.Entry<String, Page> entry : pagesMap.entrySet()){
                System.out.println(entry.getKey());
            }
        }
    }

    public static void search(Member member){

        Profile memberProfile = member.getProfile();

        while(true) {
            System.out.println("You want to search for: ");
            System.out.println("1. People");
            System.out.println("2. Pages");
            System.out.println("3. Return");

            int ans = input.nextInt();
            input.nextLine();

            switch (ans) {
                case 1:
                    addPeople(member);
                break;

                case 2:
                    followPages(member);
                    break;

                case 3:
                    return;
            }
        }
    }

    public static void addPeople(Member member){

        Profile memberProfile = member.getProfile();

        System.out.println("Type the username of the friend you want to add/follow: ");
        String friendUsername = input.nextLine();

        System.out.println("1: Add friend 2:Follow ");
        int ans = input.nextInt();
        input.nextLine();


        if(!userManager.isFoundMember(friendUsername)) System.out.println("The username doesn't exist");

        else {
            Profile friendProfile = ProfileManager.getProfileByUsername(friendUsername);
            NotificationManager notificationManager = new NotificationManager(database);

            if (ans == 1) {

                userManager.addFriend(friendProfile, memberProfile);
                System.out.println("your friend request is sent !");

                notificationManager.sendNotification(memberProfile.getProfileId(),
                        friendProfile.getProfileId(), EventType.FRIEND_REQUEST,
                        memberProfile.getFullName() + " sent you a friend request");
            }
            else{
                userManager.follow(friendProfile, memberProfile);

                System.out.println("your follow them now !");

                notificationManager.sendNotification(memberProfile.getProfileId(),
                        friendProfile.getProfileId(), EventType.FOLLOW,
                        memberProfile.getFullName() + " follows you !");
            }
        }
    }

    public static void followPages(Member member){
        Profile memberProfile = member.getProfile();

        System.out.println("Enter the name of the page: ");
        String pageName = input.nextLine();

        Map<String, Page> pages = pageManager.getPages();

        for (Map.Entry<String, Page> entry : pages.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(pageName) || entry.getKey().contains(pageName)) {
                System.out.printf("%s (ID: %s)\n", entry.getValue().getPageName(), entry.getValue().getPageId());
            }
        }

        System.out.println("1.Open Page  2.Follow Page 3.Return");

        int choice = input.nextInt();
        input.nextLine();

        if(choice != 3){
            System.out.println("Enter the id of the page: ");
            String id = input.nextLine();
            Page chosenPage = pageManager.getPageById(id);

            if(choice == 1) openPageProfile(chosenPage);

            else if(choice == 2) {
                userManager.follow(chosenPage, memberProfile);

                System.out.println("You follow the page now !");
                notificationManager.sendNotification(memberProfile.getID(), chosenPage.getPageId(),
                        EventType.FOLLOW, memberProfile.getFullName()+" follows you !");
            }

            else System.out.println("Invalid choice. Try again.");
        }

        else return;
    }

    public static void viewFriends(Member member){
        Profile profile = member.getProfile();

        System.out.println("Friends: ");
        if(userManager.getFriendsMap().containsKey(profile) && !userManager.getFriendsList(profile).isEmpty()) {
            for (Profile friend : userManager.getFriendsList(profile)) {
                System.out.printf("%s %s (%s)\n", friend.getFirstName(),
                        friend.getLastName(), friend.getUsername());
            }
        }
        else System.out.println("No friends to show.");

        System.out.println("Followers: ");
        if(userManager.getFollowersMap().containsKey(profile) && !userManager.getFollowersList(profile).isEmpty()){
            for(FollowedEntity follower : userManager.getFollowersList(profile)){
                System.out.printf("%s\n", follower.getFullName());
            }
        }

        else System.out.println("No followers to show.");

        System.out.println("Following: ");
        if(userManager.getFollowingMap().containsKey(profile) && !userManager.getFollowingList(profile).isEmpty()){
            for(FollowedEntity followed : userManager.getFollowingList(profile)){
                System.out.printf("%s\n", followed.getFullName());
            }
        }

        else System.out.println("You don't follow anyone.");
    }

    public static void openNotification(Member member) {

        System.out.println("Profile notifications: ");
        notificationManager.displayNotification(member.getProfile().getProfileId());

        List<Page> ownedPages = pageManager.getPagesList(member.getProfile());
        if(ownedPages != null && !ownedPages.isEmpty()){

            System.out.println("Pages notifications: ");
            for(Page page : ownedPages) {
                System.out.println(page.getPageName());
                notificationManager.displayNotification(page.getPageId());
            }
        }
    }

    public static void managePages(Member member){

        Profile memberProfile = member.getProfile();

        while(true) {
            System.out.println("Page Management");
            System.out.println("1.Create new page");
            System.out.println("2.View My Pages");
            System.out.println("3.Switch to Page Mode");
            System.out.println("4.Return");

            int ans = input.nextInt();
            input.nextLine();

            switch (ans){
                case 1:
                {
                    System.out.println("Enter the name of the page: ");
                    String name = input.nextLine();
                    System.out.println("Enter the category: ");
                    String category = input.nextLine();
                    System.out.println("Enter the description (Bio): ");
                    String bio = input.nextLine();

                    pageManager.createPage(name,category,bio, memberProfile);
                }
                break;

                case 2:
                    pageManager.displayPagesList(memberProfile);
                break;

                case 3:{
                    System.out.println("Enter the ID of the page: ");
                    String id = input.nextLine();
                    Page page = pageManager.getPageById(id);

                    if(page != null) pageMode(page);
                    else System.out.println("No such page with this name.");
                }
                break;

                case 4:
                    return;
            }
        }
    }

    public static void pageMode(Page page){

        while(true) {
            System.out.printf("Managing Page: %s\n", page.getPageName());

            System.out.println("1. Add Post");
            System.out.println("2. View Page Posts");
            System.out.println("3. View Followers");
            System.out.println("4. Back to Profile");

            System.out.println("Enter your choice: ");

            int ans = input.nextInt();
            input.nextLine();

            switch (ans){
                case 1:
                    createPost(page);
                    break;

                case 2:
                    openPageProfile(page);
                    break;

                case 3:{
                    userManager.displayFollowers(page);
                }
                    break;

                case 4: return;
            }
        }
    }

    public static void openPageProfile(Page page){
        System.out.println("----------"+page.getFullName()+"------------");
        System.out.println("\t\t\tBio: ");
        System.out.println("\t"+page.getBio());
        System.out.println("Category: "+page.getCategory());
        postManager.displayPosts(page);
    }
}