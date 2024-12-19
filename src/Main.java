import java.util.*;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static Database database = Database.getInstance();
    public static ChatManager chatManager = new ChatManager(database);
    public static UserManager userManager = new UserManager(database);
    public static PostManager postManager = new PostManager(database);
    public static NotificationManager notificationManager = new NotificationManager(database);
    public static PageManager pageManager = new PageManager(database);
    public static GroupManager groupManager = new GroupManager(database);
    public static ProfileManager profileManager = new ProfileManager(database);
    public static PasswordHashingManager passwordManager = new PasswordHashingManager(database);

    public static void main(String[] args) {

        Member member = new Member("shawerma","Shahd", "Mahmoud",
                "shahd@gmail.com", "0123456789","123");
        profileManager.createProfile(member);
        userManager.addMember("shawerma",member);

        while(true) {
            startingMenu();
        }
    }

    public static void startingMenu(){
        System.out.println("\t 1:Register");
        System.out.println("\t 2:Login");

        try {
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) register();
            else if (choice == 2) login();
            else System.out.println("Incorrect Input.");

        } catch (InputMismatchException ex){
            System.out.println("Invalid Input.");
            input.nextLine();
        }
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

        String hashedPassword = passwordManager.getHashedPasswordRegistration(password);
        member.setPassword(hashedPassword);

        profileManager.createProfile(member);
        userManager.addMember(member.getUserName(), member);
        System.out.println("You joined the Family !");

        mainMenu(member);
    }

    public static void login(){

        System.out.println("Username: ");
        String userName = input.nextLine();

        System.out.println("Password: ");
        String enteredPassword = input.nextLine();

        if (userManager.isFoundMember(userName)){
            Member member = userManager.getMember(userName);
            String memberPassword = member.getPassword();

            String hashedEnteredPassword = passwordManager.getHashedPasswordLogin(memberPassword, enteredPassword);

            if(memberPassword.equals(hashedEnteredPassword)) {
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

        String newHashedPassword = passwordManager.getHashedPasswordRegistration(password);
        member.setPassword(newHashedPassword);
        System.out.println("Password has changed !");
    }

    public static void mainMenu(Member member){

        while(true) {
            System.out.printf("Hello, %s !\n", member.getFirstName());

            System.out.print("\t1) Profile");
            System.out.print("\t\t\t\t2) Feed");
            System.out.println("\t\t\t\t3) Inbox");
            System.out.print("\t4) Create post");
            System.out.print("\t\t\t5) Explore");
            System.out.println("\t\t\t6) My Network");
            System.out.print("\t7) View Friend Requests");
            System.out.print("\t8) Manage Pages");
            System.out.println("\t\t9) Discover Groups");
            System.out.print("\t10) Notification");
            System.out.print("\t\t11) Search");
            System.out.println("\t\t\t12) Log out");

            System.out.println("Choose between 1-12: ");
            try {
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
                        myNetwork(member);
                        break;

                    case 7:
                        viewFriendRequests(member);
                        break;

                    case 8:
                        managePages(member);
                        break;

                    case 9:
                        discoverGroups(member);
                        break;

                    case 10:
                        openNotification(member);
                        break;

                    case 11:
                        search(member);
                        break;

                    case 12:
                        return;

                    default:
                        System.out.println("Non-existent choice. Try again");
                        break;
                }
            }
            catch (InputMismatchException ex){
                System.out.println("Invalid input. Try again.");
                input.nextLine();
            }
        }
    }

    public static void openInbox(Member member){

        NotificationManager notificationManager = new NotificationManager(database);

        while(true) {
            System.out.println("Chats: ");
            String memberUsername = member.getUserName();

            Map<String, List<Message>> conversations = chatManager.getConversations();

            conversations.forEach((k, v) -> {
                if (k.contains(memberUsername)) {
                    String[] usernames = k.split("_");
                    String friendUsername = (usernames[0].equals(memberUsername) ? usernames[1] : usernames[0]);
                    System.out.println("\u2022" + friendUsername);
                    System.out.println(v.get(v.size() - 1).getContent());
                    System.out.println("******************************************");
                }
            });

            System.out.println("1: Open Chat 2: Add new Chat 3: Return");
            int ans = input.nextInt();
            input.nextLine();

            if (ans == 1) {
                System.out.println("Enter the username of the chat you want to open: ");
                String friendUsername = input.nextLine();
                String combination = (friendUsername.compareTo(memberUsername) < 0 ? friendUsername + "_" + memberUsername :
                        memberUsername + "_" + friendUsername);

                conversations.forEach((k, v) -> {
                    if (k.equals(combination)) {
                        for (Message message : v) {
                            if (!message.getSender().getUsername().equals(memberUsername)) {
                                System.out.println(message.getSender().getUsername());
                                System.out.println(message.getContent());
                                System.out.println(message.getTimestamp());
                            } else {
                                System.out.println("\t\t\t" + message.getContent());
                                System.out.println("\t\t\t" + message.getTimestamp());
                            }
                        }
                    }
                });

                while (true) {
                    System.out.println("1: Send a message 2: Return");
                    int choice = input.nextInt();
                    input.nextLine();

                    if (choice == 1) {
                        System.out.println("Write your message: ");
                        String content = input.nextLine();
                        Message message = new Message(content, profileManager.getProfileByUsername(friendUsername),
                                profileManager.getProfileByUsername(memberUsername));

                        chatManager.sendDirectMessage(message, profileManager.getProfileByUsername(memberUsername),
                                profileManager.getProfileByUsername(friendUsername));

                        notificationManager.sendNotification(member.getProfile().getProfileId(),
                                profileManager.getProfileByUsername(friendUsername).getProfileId(),
                                EventType.NEW_MESSAGE, member.getFirstName() + " " +
                                        member.getLastName() + " sent you a message");
                    } else {
                        break;
                    }
                }
            }

            else if (ans == 2) {

                if (userManager.getFriendsMap().containsKey(member.getProfile()) &&
                        !userManager.getFriendsList(member.getProfile()).isEmpty()){

                    for (Profile friend : userManager.getFriendsList(member.getProfile())) {
                        System.out.printf("%s %s (%s)\n", friend.getFirstName(), friend.getLastName(),
                                friend.getUsername());
                    }

                System.out.println("Write the username you want to send a message to: ");
                String friendUsername = input.nextLine();

                if (userManager.isFoundMember(friendUsername)) {
                    if (userManager.getFriendsList(member.getProfile())
                            .contains(profileManager.getProfileByUsername(friendUsername))) {
                        System.out.println("Write your first message: ");
                        String content = input.nextLine();

                        Message message = new Message(content, profileManager.getProfileByUsername(friendUsername),
                                member.getProfile());

                        chatManager.sendDirectMessage(message, profileManager.getProfileByUsername(memberUsername),
                                profileManager.getProfileByUsername(friendUsername));

                        notificationManager.sendNotification(member.getProfile().getProfileId(),
                                profileManager.getProfileByUsername(friendUsername).getProfileId(),
                                EventType.NEW_MESSAGE, member.getFirstName() + " " +
                                        member.getLastName() + " sent you a message");
                    } else System.out.println("The user is not in your friends list");
                } else System.out.println("The user does not exist");
            }

                else System.out.println("You have no friends to send messages to.");
        }

            else {
                System.out.println("Non-existent choice.");
                break;
            }
        }
    }

    public static void viewFriendRequests(Member member){

        Profile memberProfile = member.getProfile();

        if(!userManager.getPendingFriendsMap().containsKey(memberProfile) || userManager.getPendingFriendsList(memberProfile).isEmpty())
            System.out.println("No friend requests to show.");

        else{
            Set<Profile> pendingList = userManager.getPendingFriendsList(memberProfile);

        for (Profile friendProfile : pendingList) {

                System.out.printf("%s %s (%s)\n", friendProfile.getFirstName(), friendProfile.getLastName(),
                        friendProfile.getUsername());

                System.out.println("1: Accept 2:Delete 3:Nothing");
                int ans = input.nextInt();
                input.nextLine();

                if (ans == 1) {
                    userManager.acceptPending(memberProfile, friendProfile);

                    NotificationManager notificationManager = new NotificationManager(database);

                    notificationManager.sendNotification(memberProfile.getID(), friendProfile.getProfileId(),
                            EventType.FRIEND_REQUEST, member.getFirstName() + " " + member.getLastName()
                                    + " accepted your friend request. You're friends now !");

                }

                else if (ans == 2) {
                    userManager.deletePending(memberProfile,friendProfile);
                }

                else if (ans == 3) continue;

                else System.out.println("Incorrect input.");
            }
        }
    }

    public static void openFeed(Member member) {

        Profile memberProfile = member.getProfile();
        while (true) {
            System.out.println("Here is Feed !");
            System.out.println();

            try {

                if (userManager.getFriendsMap().containsKey(member.getProfile()) && !userManager.getFriendsList(memberProfile).isEmpty()) {
                    List<Profile> friendProfiles = userManager.getFriendsList(memberProfile);

                    for (Profile friendProfile : friendProfiles) {
                        postManager.displayPosts(friendProfile);
                    }

                    System.out.println("Interact with the post:\n1: Like 2: Comment 3:Share 4:Nothing");
                    int ans = input.nextInt();
                    input.nextLine();

                    if (ans != 4) {
                        String notificationMessage = null;

                        System.out.println("Enter the Post ID: ");
                        String postId = input.nextLine();

                        Post post = postManager.getPostById(postId);

                        if (ans == 1) {
                            postManager.addLike(post, memberProfile);
                            notificationMessage = memberProfile.getFullName() + " liked your post with ID: " + postId;
                        } else if (ans == 2) {
                            Comment comment = new Comment();

                            System.out.println("Enter your comment: ");
                            String commentContent = input.nextLine();

                            comment.setContent(commentContent);
                            comment.setPost(post);
                            comment.setAuthorProfile(member.getProfile());

                            postManager.addComment(post, comment);
                            notificationMessage = memberProfile.getFullName() + " commented on your post with ID: " + postId;
                        } else if (ans == 3) {
                            postManager.addPost(member.getProfile(), post);
                            notificationMessage = memberProfile.getFullName() + " shared your post with ID: " + postId;
                        } else {
                            System.out.println("Incorrect Input. Try again");
                            break;
                        }

                        notificationManager.sendNotification(memberProfile.getProfileId(), post.getCreatorId(),
                                EventType.POST_INTERACTION, notificationMessage);
                    }
                }

                if (userManager.getFollowingMap().containsKey(member.getProfile()) && !userManager.getFollowingList(memberProfile).isEmpty()) {

                    List<FollowedEntity> followingList = userManager.getFollowingList(memberProfile);

                    for (FollowedEntity followedEntity : followingList) {
                        postManager.displayPosts(followedEntity);
                    }

                    System.out.println("Interact with the post:\n1: Like 2:Share 3:Nothing");
                    int ans = input.nextInt();
                    input.nextLine();

                    if (ans != 3) {
                        String notificationMessage = null;

                        System.out.println("Enter the Post ID: ");
                        String postId = input.nextLine();

                        Post post = postManager.getPostById(postId);

                        if (ans == 1) {
                            postManager.addLike(post, member.getProfile());
                            notificationMessage = memberProfile.getFullName() + " liked your post with ID: " + postId;
                        } else if (ans == 2) {
                            postManager.addPost(member.getProfile(), post);
                            notificationMessage = memberProfile.getFullName() + " shared your post with ID:" + postId;
                        } else {
                            System.out.println("Incorrect Input. Try again");
                            break;
                        }

                        notificationManager.sendNotification(memberProfile.getProfileId(), post.getCreatorId(),
                                EventType.POST_INTERACTION, notificationMessage);
                    }
                }

                if (userManager.getJoinedGroups().containsKey(memberProfile) && !userManager.getJoinedGroups(memberProfile).isEmpty()) {
                    List<Group> groups = userManager.getJoinedGroups(memberProfile);

                    for (Group group : groups) {
                        postManager.displayPosts(group);
                    }

                    System.out.println("Interact with the post:\n1: Like 2: Comment 3:Share 4:Nothing");
                    int ans = input.nextInt();
                    input.nextLine();

                    if (ans != 4) {
                        String notificationMessage = null;

                        System.out.println("Enter the Post ID: ");
                        String postId = input.nextLine();

                        Post post = postManager.getPostById(postId);

                        int groupIdIdx = postId.indexOf("G") + 1;
                        String groupId = postId.substring(groupIdIdx);
                        Group group = groupManager.getGroupById(groupId);

                        if (ans == 1) {
                            postManager.addLike(post, memberProfile);
                            notificationMessage = memberProfile.getFullName() + " liked your post with ID: " + postId
                                    + " in group: " + group.getGroupName();
                        } else if (ans == 2) {
                            Comment comment = new Comment();

                            System.out.println("Enter your comment: ");
                            String commentContent = input.nextLine();

                            comment.setContent(commentContent);
                            comment.setPost(post);
                            comment.setAuthorProfile(member.getProfile());

                            postManager.addComment(post, comment);
                            notificationMessage = memberProfile.getFullName() + " commented on your post with ID: " + postId
                                    + " in group: " + group.getGroupName();
                        } else if (ans == 3) {
                            postManager.addPost(member.getProfile(), post);
                            notificationMessage = memberProfile.getFullName() + " shared your post with ID: " + postId
                                    + " in group: " + group.getGroupName();
                        } else {
                            System.out.println("Incorrect Input. Try again.");
                            break;
                        }

                        notificationManager.sendNotification(memberProfile.getProfileId(), post.getCreatorId(),
                                EventType.POST_INTERACTION, notificationMessage);
                    }

                } else {
                    if (postManager.getPostsMap().containsKey(memberProfile)
                            && !postManager.getPostsList(memberProfile).isEmpty()) {
                        postManager.displayPosts(memberProfile);
                    } else System.out.println("No posts to display.");
                }

                System.out.println("Return to Main Menu? Y/N");
                String c = input.nextLine();

                if (c.equals("Y")) return;
            }

            catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
            }
        }
    }

    public static void createPost(FollowedEntity followedEntity){

        Post post = new Post(followedEntity);
        System.out.println("What's on your mind? ");
        String content = input.nextLine();

        post.setContent(content);

        postManager.addPost(followedEntity, post);
    }

    public static void createPost(Group group, Profile profile){
        Post post = new Post(profile,group);
        System.out.println("What's on your mind? ");
        String content = input.nextLine();

        post.setContent(content);

        postManager.addPost(group, post);
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
            System.out.println("Choose from 1-9 to edit and 0 to return");

            try {
                int ans = input.nextInt();
                input.nextLine();

                if (ans == 0) break;

                switch (ans) {
                    case 1: {
                        System.out.println("Write your thoughts: ");
                        String bio = input.nextLine();
                        profile.setBio(bio);
                    }
                    break;

                    case 2: {
                        System.out.println("Enter School/University: ");
                        String school = input.nextLine();
                        profile.setSchool(school);
                    }
                    break;

                    case 3: {
                        System.out.println("Enter work place/position: ");
                        String work = input.nextLine();
                        profile.setWork(work);
                    }
                    break;

                    case 4: {
                        System.out.println("Enter Gender: ");
                        String gender = input.nextLine();
                        profile.setGender(gender);
                    }
                    break;

                    case 5: {
                        System.out.println("Enter Religion: ");
                        String religion = input.nextLine();
                        profile.setReligion(religion);
                    }
                    break;

                    case 6: {
                        System.out.println("Enter City: ");
                        String city = input.nextLine();
                        profile.setCity(city);
                    }
                    break;

                    case 7: {
                        System.out.println("Enter country: ");
                        String country = input.nextLine();
                        profile.setCountry(country);
                    }
                    break;

                    case 8: {
                        System.out.println("Enter Birth Date: ");
                        String birthdate = input.nextLine();
                        profile.setBirthdate(birthdate);
                    }
                    break;

                    case 9: {
                        System.out.println("Enter Sex Preference: ");
                        String sexPreference = input.nextLine();
                        profile.setSexPreference(sexPreference);
                    }

                    default: {
                        System.out.println("Non-existent input. Try again");
                        break;
                    }
                }
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
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

        //groups exploring

        Map<String,Group> groupMap = groupManager.getGroupsById();

        if(groupMap != null){
            System.out.println("**Groups**");
            for(Map.Entry<String,Group> entry : groupMap.entrySet()){
                System.out.println(entry.getValue().getGroupName());
            }
        }
    }

    public static void search(Member member){

        Profile memberProfile = member.getProfile();

        while(true) {
            System.out.println("You want to search for: ");
            System.out.println("1. People");
            System.out.println("2. Pages");
            System.out.println("3. Groups");
            System.out.println("4. Return");

            try {
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
                        joinGroups(member);
                        break;

                    case 4:
                        return;

                    default: {
                        System.out.println("Non-existent input. Try again");
                        break;
                    }
                }
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
            }
        }
    }

    public static void addPeople(Member member){

        Profile memberProfile = member.getProfile();

        System.out.println("Type the username of the friend you want to add/follow: ");
        String friendUsername = input.nextLine();

        try {
            System.out.println("1: Add friend 2:Follow ");
            int ans = input.nextInt();
            input.nextLine();


            if (!userManager.isFoundMember(friendUsername)) System.out.println("The username doesn't exist");

            else {
                Profile friendProfile = profileManager.getProfileByUsername(friendUsername);
                NotificationManager notificationManager = new NotificationManager(database);

                if (ans == 1) {

                    userManager.addFriend(friendProfile, memberProfile);
                    System.out.println("your friend request is sent !");

                    notificationManager.sendNotification(memberProfile.getProfileId(),
                            friendProfile.getProfileId(), EventType.FRIEND_REQUEST,
                            memberProfile.getFullName() + " sent you a friend request");
                } else {
                    userManager.follow(friendProfile, memberProfile);

                    System.out.println("your follow them now !");

                    notificationManager.sendNotification(memberProfile.getProfileId(),
                            friendProfile.getProfileId(), EventType.FOLLOW,
                            memberProfile.getFullName() + " follows you !");
                }
            }
        } catch (InputMismatchException ex){
            System.out.println("Invalid Input.");
            input.nextLine();
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

        try {
            int choice = input.nextInt();
            input.nextLine();

            if (choice != 3) {
                System.out.println("Enter the id of the page: ");
                String id = input.nextLine();
                Page chosenPage = pageManager.getPageById(id);

                if (choice == 1) openPageProfile(chosenPage);

                else if (choice == 2) {
                    userManager.follow(chosenPage, memberProfile);

                    System.out.println("You follow the page now !");
                    notificationManager.sendNotification(memberProfile.getID(), chosenPage.getPageId(),
                            EventType.FOLLOW, memberProfile.getFullName() + " follows you !");
                }

                else System.out.println("Invalid choice. Try again.");

            } else return;

        } catch (InputMismatchException ex){
            System.out.println("Invalid Input.");
            input.nextLine();
        }

        catch (NullPointerException ex){
            System.out.println("There's no such a page.");
        }
    }

    public static void myNetwork(Member member){

        while(true) {
            Profile profile = member.getProfile();

            System.out.println("Friends: ");
            if (userManager.getFriendsMap().containsKey(profile) && !userManager.getFriendsList(profile).isEmpty()) {
                for (Profile friend : userManager.getFriendsList(profile)) {
                    System.out.printf("%s (%s)\n", friend.getFullName(), friend.getID());
                }
            } else System.out.println("No friends to show.");

            System.out.println("Followers: ");
            if (userManager.getFollowersMap().containsKey(profile) && !userManager.getFollowersList(profile).isEmpty()) {
                for (FollowedEntity follower : userManager.getFollowersList(profile)) {
                    System.out.printf("%s (%s)\n", follower.getFullName(), follower.getID());
                }
            } else System.out.println("No followers to show.");

            System.out.println("Following: ");
            if (userManager.getFollowingMap().containsKey(profile) && !userManager.getFollowingList(profile).isEmpty()) {
                for (FollowedEntity followed : userManager.getFollowingList(profile)) {
                    System.out.printf("%s (%s)\n", followed.getFullName(), followed.getID());
                }
            } else System.out.println("You don't follow anyone.");

            System.out.println("Joined Groups: ");
            if (userManager.getJoinedGroups().containsKey(profile) && !userManager.getJoinedGroups(profile).isEmpty()) {
                for (Group group : userManager.getJoinedGroups(profile)) {
                    System.out.printf("%s (%s)\n", group.getGroupName(), group.getGroupId());
                }
            } else System.out.println("No groups joined.");

            System.out.println("1.Remove Friend 2.Unfollow People/Page 3.Exit Group 4.Nothing");

            try {
                int ans = input.nextInt();
                input.nextLine();

                if (ans == 4) return;

                else {
                    if (ans == 1) {
                        System.out.println("Write the ID of the friend you want to remove: ");
                        String id = input.nextLine();

                        Profile friendProfile = profileManager.getProfileById(id);
                        userManager.deleteFriend(profile, friendProfile);

                        System.out.println(friendProfile.getFullName() + " is removed from your network!");
                    } else if (ans == 2) {
                        System.out.println("Write the ID of the friend/page you want to unfollow: ");
                        String id = input.nextLine();

                        FollowedEntity followedEntity = profileManager.getProfileById(id) != null ?
                                profileManager.getProfileById(id) : pageManager.getPageById(id);

                        userManager.unfollow(profile, followedEntity);

                        System.out.println(followedEntity.getFullName() + " is removed from your following list!");
                    } else if (ans == 3) {
                        System.out.println("Write the ID of the group you want to exit: ");
                        String id = input.nextLine();

                        Group group = groupManager.getGroupById(id);

                        userManager.exitGroup(profile, group);
                        groupManager.exitGroup(group, profile);

                        System.out.println("You left group: " + group.getGroupName() + "!");
                    } else {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
            }
        }
    }

    public static void openNotification(Member member) {

        Profile memberProfile = member.getProfile();

        System.out.println("Profile notifications: ");
        notificationManager.displayNotification(memberProfile.getProfileId());

        List<Page> ownedPages = pageManager.getPagesList(memberProfile);
        if(ownedPages != null && !ownedPages.isEmpty()){

            System.out.println("Pages notifications: ");
            for(Page page : ownedPages) {
                System.out.println(page.getPageName());
                notificationManager.displayNotification(page.getPageId());
            }
        }

        List<Group> ownedGroups = groupManager.getGroupsByProfile(memberProfile);
        if(ownedGroups != null && !ownedGroups.isEmpty()){
            System.out.println("Groups notifications:");
            for(Group group : ownedGroups){
                System.out.println(group.getGroupName());
                notificationManager.displayNotification(group.getGroupId());
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

            try {
                int ans = input.nextInt();
                input.nextLine();

                switch (ans) {
                    case 1: {
                        System.out.println("Enter the name of the page: ");
                        String name = input.nextLine();
                        System.out.println("Enter the category: ");
                        String category = input.nextLine();
                        System.out.println("Enter the description (Bio): ");
                        String bio = input.nextLine();

                        pageManager.createPage(name, category, bio, memberProfile);
                    }
                    break;

                    case 2:
                        pageManager.displayPagesList(memberProfile);
                        break;

                    case 3: {
                        System.out.println("Enter the ID of the page: ");
                        String id = input.nextLine();
                        Page page = pageManager.getPageById(id);

                        if (page != null) pageMode(page);
                        else System.out.println("No such page with this name.");
                    }
                    break;

                    case 4:
                        return;

                    default: {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }
            } catch(InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
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

            try {
                int ans = input.nextInt();
                input.nextLine();

                switch (ans) {
                    case 1:
                        createPost(page);
                        break;

                    case 2:
                        openPageProfile(page);
                        break;

                    case 3: {
                        userManager.displayFollowers(page);
                    }
                    break;

                    case 4:
                        return;

                    default: {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }

            } catch (InputMismatchException ex){
                System.out.println("Invalid Input. Try again");
                input.nextLine();
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

    public static void discoverGroups(Member member){

        while (true) {
            System.out.println("1.View All Groups");
            System.out.println("2.View My Groups");
            System.out.println("3.Open a group");
            System.out.println("4.Create a new group");
            System.out.println("5.Return");

            try {
                int ans = input.nextInt();
                input.nextLine();

                switch (ans) {
                    case 1:
                        groupManager.displayGroups();
                        break;

                    case 2:
                        groupManager.displayGroupsOf(member.getProfile());
                        break;

                    case 3: {
                        System.out.println("Enter the ID of the group: ");
                        String id = input.nextLine();

                        if (!groupManager.findGroup(id)) System.out.println("No such a group with this ID.");

                        else {
                            Group group = groupManager.getGroupById(id);
                            viewGroup(group, member.getProfile());
                        }
                    }
                    break;

                    case 4: {
                        System.out.println("Write the name of the group: ");
                        String name = input.nextLine();

                        Group group = groupManager.createGroup(member.getProfile(), name);
                        userManager.joinGroup(member.getProfile(),group);

                        System.out.println("The Group is Created !");
                    }
                    break;

                    case 5:
                        return;

                    default: {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }

            } catch (InputMismatchException ex){
                System.out.println("Invalid Input. Try again");
                input.nextLine();
            }
        }
    }

    public static void viewGroup(Group group, Profile memberProfile){

        if(memberProfile == group.getCreatorAdminProfile()) openGroup(group,memberProfile);

        else {
            while (true) {
                if (group.getPrivacyOption() == PrivacyOption.PRIVATE) {
                    System.out.println("About:");
                    System.out.println(group.getBio());
                    System.out.println("This group is private");
                    System.out.println("Joined Members: " + groupManager.getJoinedMembers(group).size());

                    System.out.println("Join Group ? Y/N");

                    String choice = input.nextLine();

                    if (choice.equals("Y")) {
                        groupManager.joinPrivateGroup(group, memberProfile);
                        System.out.println("Your request is sent and waiting for the admin to approve it!");

                        notificationManager.sendNotification(memberProfile.getProfileId(), group.getGroupId(),
                                EventType.JOIN_REQUEST, memberProfile.getFullName()+" has requested to join the group.");

                        return;
                    }
                    else return;

                } else {
                    openGroup(group, memberProfile);
                    return;
                }
            }
        }
    }

    public static void openGroup(Group group, Profile memberProfile){

        while(true) {
            System.out.println("----------------" + group.getGroupName() + "------------------");
            System.out.println("1.About");
            System.out.println("2.View posts");
            System.out.println("3.Create a post");
            System.out.println("4.View members");
            System.out.println("5.Return");

            if (group.getCreatorAdminProfile() == memberProfile) {
                System.out.println("6.View requests");
                System.out.println("7.Add members");
                System.out.println("8.Settings");
            }

            if (!groupManager.findJoinedMember(group, memberProfile))
                System.out.println("0.Join Group");

            try {
                int ans = input.nextInt();
                input.nextLine();

                switch (ans) {
                    case 1: {
                        System.out.println("About:");
                        System.out.println(group.getBio());
                        System.out.println(group.getPrivacyOption().toString());
                        System.out.println("Joined Members: " + groupManager.getJoinedMembers(group).size());
                    }
                    break;

                    case 2:
                        postManager.displayPosts(group);
                        break;

                    case 3:
                        createPost(group, memberProfile);
                        break;

                    case 4:
                        groupManager.displayMembersOf(group);
                        break;

                    case 5:
                        return;

                    case 6:
                        reviewRequests(group, memberProfile);
                        break;

                    case 7: {
                        System.out.println("Enter username of the profile you want to add: ");
                        String username = input.nextLine();

                        Profile profile = profileManager.getProfileByUsername(username);

                        groupManager.joinGroup(group, profile);
                        userManager.joinGroup(profile, group);

                        notificationManager.sendNotification(group.getCreatorAdminProfile().getProfileId(),
                                profile.getProfileId(), EventType.JOIN_GROUP,
                                group.getCreatorAdminProfile().getFullName() + " added you to group: "
                                        + group.getGroupName());

                        System.out.println(profile.getFullName() + " is added successfully !");
                    }
                    break;

                    case 8:
                        groupSettings(group);
                        break;

                    case 0: {
                        groupManager.joinGroup(group, memberProfile);
                        userManager.joinGroup(memberProfile, group);
                        System.out.println("You've joined the group ! Welcome !");

                        notificationManager.sendNotification(memberProfile.getProfileId(), group.getGroupId(),
                                EventType.JOIN_REQUEST, memberProfile.getFullName()+" has joined the group!");
                    }
                    break;

                    default: {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }

            } catch (InputMismatchException ex){
                System.out.println("Invalid Input. Try again");
                input.nextLine();
            }
        }
    }

    public static void groupSettings(Group group) {
        while (true) {
            System.out.println("1.Edit Name");
            System.out.println("2.Edit Bio");
            System.out.println("3.Edit Privacy option");
            System.out.println("4.Return");

            try {
                int ans = input.nextInt();
                input.nextLine();

                switch (ans) {
                    case 1: {
                        System.out.println("Enter new name: ");
                        String name = input.nextLine();

                        group.setGroupName(name);
                        System.out.println("Group name successfully changed!");
                    }
                    break;

                    case 2: {
                        System.out.println("Enter new bio: ");
                        String bio = input.nextLine();

                        group.setBio(bio);
                        System.out.println("Group Bio successfully changed!");
                    }
                    break;

                    case 3: {
                        System.out.println("Now: " + group.getPrivacyOption().toString());
                        System.out.println("Change ? Y/N");

                        String choice = input.nextLine();

                        if (choice.equals("Y")) {
                            PrivacyOption privacyOption = group.getPrivacyOption() == PrivacyOption.PRIVATE ?
                                    PrivacyOption.PUBLIC : PrivacyOption.PRIVATE;

                            group.setPrivacyOption(privacyOption);

                            System.out.println("Privacy option changed!");
                        }
                    }
                    break;

                    case 4:
                        return;

                    default: {
                        System.out.println("Incorrect Choice. Try again");
                        break;
                    }
                }

            } catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
            }
        }
    }

    public static void reviewRequests(Group group, Profile memberProfile){

        if(groupManager.getPendingMembersByGroup().containsKey(group) && !groupManager.getPendingMembersList(group).isEmpty()){

            List<Profile> pendingMembers = groupManager.getPendingMembersList(group);

            try {
                for (int i = 0 ; i < pendingMembers.size(); ++i) {

                    Profile profile = pendingMembers.get(i);
                    System.out.printf("%s (%s)\n", profile.getFullName(), profile.getUsername());

                    System.out.println("1: Accept 2:Delete 3:Nothing");
                    int ans = input.nextInt();
                    input.nextLine();

                    if (ans == 1) {
                        groupManager.joinGroup(group, profile);
                        groupManager.removeFromPending(group, profile);
                        userManager.joinGroup(profile, group);

                        notificationManager.sendNotification(memberProfile.getProfileId(), profile.getProfileId(),
                                EventType.ACCEPTED_REQUEST,
                                "Your request has been approved by admins! You've joined " + group.getGroupName());
                    } else if (ans == 2) groupManager.removeFromPending(group, profile);
                }
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input.");
                input.nextLine();
            }

        }

        else System.out.println("No requests to show.");
    }

    public static void joinGroups(Member member){
        Profile memberProfile = member.getProfile();

        System.out.println("Enter the name of the group: ");
        String groupName = input.nextLine();

        Map<String, Group> groups = groupManager.getGroupsByName();

        for(Map.Entry<String,Group> entry : groups.entrySet()){
            if (entry.getKey().equalsIgnoreCase(groupName) || entry.getKey().contains(groupName)) {
                System.out.printf("%s (ID: %s)\n", entry.getValue().getGroupName(), entry.getValue().getGroupId());
            }
        }

        System.out.println("1.Open Group 2.Join Group 3.Return");

        try {
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 3) return;

            else {
                System.out.println("Enter the ID of the group: ");
                String id = input.nextLine();

                Group group = groupManager.getGroupById(id);

                if (choice == 1) viewGroup(group, memberProfile);

                else if (choice == 2) {
                    if (group.getPrivacyOption() == PrivacyOption.PRIVATE) {
                        groupManager.joinPrivateGroup(group, memberProfile);
                        System.out.println("This group is private. Your request is sent to the admin.");

                        notificationManager.sendNotification(memberProfile.getProfileId(), group.getGroupId(),
                                EventType.JOIN_REQUEST, memberProfile.getFullName()+" has requested to join the group.");
                    }
                    else {
                        groupManager.joinGroup(group, memberProfile);
                        System.out.println("You've joined the group !");

                        notificationManager.sendNotification(memberProfile.getProfileId(), group.getGroupId(),
                                EventType.JOIN_REQUEST, memberProfile.getFullName()+" has joined the group!");
                    }
                }
                else System.out.println("Incorrect Input. Try again.");
            }
        } catch (InputMismatchException ex){
            System.out.println("Invalid Input.");
            input.nextLine();
        }

        catch (NullPointerException ex){
            System.out.println("There's no such a group.");
        }
    }

}