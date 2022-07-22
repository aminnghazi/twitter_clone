//package views;
//
//import controllers.PostsViewController;
//import enums.Dialog;
//import enums.Messages;
//import javafx.stage.Stage;
//import models.DB;
//import models.Post;
//
//import java.util.ArrayList;
//
//public class PostsView extends View {
//    private static PostsViewController controller = new PostsViewController();
//    @Override
//    public void run() {
//    showOptions();
//    String chosenOption= getChoice();
//        switch (chosenOption){
//            case "1":
//            case "see posts":
//                this.seePosts(controller.getAllPosts(),false);// TODO: 7/21/2022 replace all posts with only ones they follow
//                break;
//            case "2":
//            case "add new post":
//                this.addNewPost();
//                break;
//            case "3":
//            case "exit":
//                return;
//            default:
//                System.out.println(Dialog.INVALID_CHOICE);
//                run();//loop
//        }
//    }
//    @Override
//    public void showOptions() {
//        System.out.println("choose an option to continue");
//        System.out.println("1. see posts");// TODO: 7/21/2022 only show posts of whom you follow
//        System.out.println("2. add new post");
//        System.out.println("3. exit");
//    }
//
//    public void addNewPost() {
//        String text = getInput("Type your posts text");
//        Dialog dialog = controller.addNewPost(text, null);
//        if (dialog != Dialog.SUCCESS)
//            System.out.println(dialog);
//        else
//            System.out.println("Post added successfully!");
//        run();
//    }
//
//    private void seePosts(ArrayList<Post> posts,boolean isComment) {
//        if (posts.size() == 0) {
//            if (!isComment)
//                System.out.println(Messages.NO_POSTS_TO_SHOW);
//            else
//                System.out.println(Messages.NO_COMMENTS_TO_SHOW);
//       }else {
//            if (!isComment)
//                System.out.println("Choose a post number to interact:");
//            else
//                System.out.println("Choose a comment number to interact:");
//
//            int i = 0;
//            for (Post post : posts) {
//                i++;
//                System.out.println(i + ". " +post.getSenderUsername()+ ": ");
//                System.out.println("    { " + post.getText() + " }");
//            }
//            String choice = getChoice();
//            try {
//                int postNumber = Integer.parseInt(choice)-1;
//                postOptions(posts.get(postNumber));
//            }catch (Exception e){
//                System.out.println(Dialog.INVALID_CHOICE);
//            }
//        }
//        run();//loop
//    }
//
//    public void postOptions(Post post){
//        System.out.println(post.getText());
//        System.out.println("Likes :" + DB.getLikesCount(post.getID()));
//        // TODO: 7/21/2022 add comments count
//        showPostOptions();
//        String choice = getChoice();
//        Dialog dialog;
//
//        switch (choice){
//            case "1":
//            case "like":
//                dialog =controller.handleLikingPost(getLoggedInUser().getUserName(), post.getID());
//                System.out.println(dialog);
//                break;
//            case "2":
//            case "see comments":
//                 ArrayList<Post> posts = controller.handleSeeComments(post.getID());
//                 seePosts(posts,true);
//                break;
//            case "3":
//            case "add comment":
//                String text = getInput("Enter your comments text:");
//                dialog = controller.handleAddComment(text, post);
//                System.out.println(dialog);
//                break;
//            case "4":
//            case "exit":
//                return;
//            default:
//                dialog = Dialog.INVALID_CHOICE;
//                System.out.println(dialog);
//        }
//    }
//    public void showPostOptions(){
//        System.out.println("select an option to continue:");
//        System.out.println("1. like");// TODO: 7/21/2022 add remove like
//        System.out.println("2. see comments");
//        System.out.println("3. add comment");
//        System.out.println("4. exit");
//    }
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }
//}
