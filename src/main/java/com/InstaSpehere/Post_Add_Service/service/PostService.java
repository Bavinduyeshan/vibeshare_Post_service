//package com.InstaSpehere.Post_Add_Service.service;
//
//import com.InstaSpehere.Post_Add_Service.model.*;
//import com.InstaSpehere.Post_Add_Service.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PostService {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private PostLikeRepository postLikeRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private CommentReplyRepository commentReplyRepository;
//
//    @Autowired
//    private PostServiceClient postServiceClient;  // Corrected to UserServiceClient
//
//    // Create a post
//    @Transactional
//    public PostModel createPost(PostModel post) {
//        if (post.getUserProfileId() == null) {
//            throw new IllegalArgumentException("User profile ID is missing");
//        }
//        UserprofileResponse user = postServiceClient.validateProfile(post.getUserProfileId());
//        if (user == null) {
//            throw new IllegalArgumentException("Invalid user profile");
//        }
//        post.setLikeCount(0);
//        post.setCommentCount(0);
//        return postRepository.save(post);
//    }
//
//    // Get post by ID
//    public Optional<PostModel> getPostById(Integer postId) {
//        return postRepository.findById(postId);
//    }
//
//    // Get posts by user profile ID
//    public List<PostModel> getPostsByUserProfileId(Integer userProfileId) {
//        return postRepository.findByUserProfileId(userProfileId);
//    }
//
//    // Add a like
//    @Transactional
//    public void addLike(Integer postId, Integer userProfileId) {
//        if (postLikeRepository.existsByPostIdAndUserProfileId(postId, userProfileId)) {
//            throw new IllegalArgumentException("User already liked this post");
//        }
//        PostModel post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
//        if (user == null) {
//            throw new IllegalArgumentException("Invalid user profile");
//        }
//        PostLike like = new PostLike();
//        like.setPostId(postId);
//        like.setUserProfileId(userProfileId);
//        postLikeRepository.save(like);
//        post.setLikeCount(post.getLikeCount() + 1);
//        postRepository.save(post);
//    }
//
//    // Remove a like
//    @Transactional
//    public void removeLike(Integer postId, Integer userProfileId) {
//        PostLike like = postLikeRepository.findByPostIdAndUserProfileId(postId, userProfileId)
//                .orElseThrow(() -> new RuntimeException("Like not found"));
//        PostModel post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        postLikeRepository.delete(like);
//        post.setLikeCount(post.getLikeCount() - 1);
//        postRepository.save(post);
//    }
//
//    // Add a comment
//    @Transactional
//    public Comment addComment(Integer postId, Integer userProfileId, String content) {
//        PostModel post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
//        if (user == null) {
//            throw new IllegalArgumentException("Invalid user profile");
//        }
//        Comment comment = new Comment();
//        comment.setPostId(postId);
//        comment.setUserProfileId(userProfileId);
//        comment.setContent(content);
//        post.setCommentCount(post.getCommentCount() + 1);
//        postRepository.save(post);
//        return commentRepository.save(comment);
//    }
//
//    // Add a reply to a comment
//    @Transactional
//    public CommentReply addReply(Long commentId, Integer userProfileId, String content) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
//        if (user == null) {
//            throw new IllegalArgumentException("Invalid user profile");
//        }
//        CommentReply reply = new CommentReply();
//        reply.setCommentId(commentId);
//        reply.setUserProfileId(userProfileId);
//        reply.setContent(content);
//        return commentReplyRepository.save(reply);
//    }
//
//    // Get comments for a post
//    public List<Comment> getCommentsByPostId(Integer postId) {
//        return commentRepository.findByPostId(postId);
//    }
//
//    // Get replies for a comment
//    public List<CommentReply> getRepliesByCommentId(Long commentId) {
//        return commentReplyRepository.findByCommentId(commentId);
//    }
//}

package com.InstaSpehere.Post_Add_Service.service;

import com.InstaSpehere.Post_Add_Service.controller.PostController;
import com.InstaSpehere.Post_Add_Service.model.*;
import com.InstaSpehere.Post_Add_Service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private PostServiceClient postServiceClient;


    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AdminAlertRepository adminAlertRepository;


    @Autowired
    private PostReportRepository postReportRepository;

    // Create a post
    @Transactional
    public PostModel createPost(PostModel post) {
        if (post.getUserProfileId() == null) {
            throw new IllegalArgumentException("User profile ID is missing");
        }
        UserprofileResponse user = postServiceClient.validateProfile(post.getUserProfileId());
        if (user == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        post.setLikeCount(0);
        post.setCommentCount(0);
        return postRepository.save(post);
    }

//c
//    public Optional<PostModel> getPostById(Integer postId, Integer requestingUserProfileId) {
//        Optional<PostModel> post = postRepository.findById(postId);
//        if (post.isEmpty()) {
//            return Optional.empty();
//        }
//        PostModel p = post.get();
//        if (p.getPrivate() && !p.getUserProfileId().equals(requestingUserProfileId)) {
//            boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
//            if (!isFollower) {
//                return Optional.empty();
//            }
//        }
//        // Set likedByUserProfileIds for use in controller
//        p.setLikedByUserProfileIds(getLikedByUserProfileIds(postId)); // Assuming you add this field to PostModel temporarily or handle in controller
//        return post;
//    }
public Optional<PostModel> getPostById(Integer postId, Integer requestingUserProfileId) {
    Optional<PostModel> post = postRepository.findById(postId);
    if (post.isEmpty() || post.get().getIsBanned()) { // Check if post is banned
        return Optional.empty();
    }
    PostModel p = post.get();
    if (p.getPrivate() && !p.getUserProfileId().equals(requestingUserProfileId)) {
        boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
        if (!isFollower) {
            return Optional.empty();
        }
    }
    // Set likedByUserProfileIds for use in controller
    p.setLikedByUserProfileIds(getLikedByUserProfileIds(postId));
    return post;
}


//    public List<PostController.PostResponse> getFeed(Integer requestingUserProfileId) {
//        // 1. Get list of users the requester follows
//        List<Integer> followingIds = followRepository.findByFollowerId(requestingUserProfileId)
//                .stream()
//                .map(Follow::getFollowedId)
//                .collect(Collectors.toList());
//
//        // Include the user’s own posts too
//        followingIds.add(requestingUserProfileId);
//
//        // 2. Fetch posts from these users
//        List<PostModel> posts = postRepository.findByUserProfileIdIn(followingIds);
//
//        // 3. Apply access control (only allow private if the requester follows them)
//        return posts.stream()
//                .filter(p -> {
//                    if (Boolean.TRUE.equals(p.getPrivate()) && !p.getUserProfileId().equals(requestingUserProfileId)) {
//                        return followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
//                    }
//                    return true;
//                })
//                .map(p -> new PostController.PostResponse(
//                        p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
//                        p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),
//                        getLikedByUserProfileIds(p.getPostId())
//                ))
//                .collect(Collectors.toList());
//    }

    public List<PostController.PostResponse> getFeed(Integer requestingUserProfileId) {
        // 1. Get list of users the requester follows
        List<Integer> followingIds = followRepository.findByFollowerId(requestingUserProfileId)
                .stream()
                .map(Follow::getFollowedId)
                .collect(Collectors.toList());

        // Include the user’s own posts too
        followingIds.add(requestingUserProfileId);

        // 2. Fetch posts from these users
        List<PostModel> posts = postRepository.findByUserProfileIdIn(followingIds);

        // 3. Apply access control and filter out banned posts
        return posts.stream()
                .filter(p -> !p.getIsBanned()) // Filter out banned posts
                .filter(p -> {
                    if (Boolean.TRUE.equals(p.getPrivate()) && !p.getUserProfileId().equals(requestingUserProfileId)) {
                        return followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
                    }
                    return true;
                })
                .map(p -> new PostController.PostResponse(
                        p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
                        p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),
                        getLikedByUserProfileIds(p.getPostId())
                ))
                .collect(Collectors.toList());
    }


//    public List<PostModel> getPostsByUserProfileId(Integer userProfileId, Integer requestingUserProfileId) {
//        List<PostModel> posts = postRepository.findByUserProfileId(userProfileId);
//        boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, userProfileId);
//        return posts.stream()
//                .filter(p -> !p.getPrivate() || p.getUserProfileId().equals(requestingUserProfileId) || isFollower)
//                .map(p -> {
//                    p.setLikedByUserProfileIds(getLikedByUserProfileIds(p.getPostId())); // Assuming you add this field
//                    return p;
//                })
//                .collect(Collectors.toList());
//    }
public List<PostModel> getPostsByUserProfileId(Integer userProfileId, Integer requestingUserProfileId) {
    List<PostModel> posts = postRepository.findByUserProfileId(userProfileId);
    boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, userProfileId);
    return posts.stream()
            .filter(p -> !p.getIsBanned()) // Filter out banned posts
            .filter(p -> !p.getPrivate() || p.getUserProfileId().equals(requestingUserProfileId) || isFollower)
            .map(p -> {
                p.setLikedByUserProfileIds(getLikedByUserProfileIds(p.getPostId()));
                return p;
            })
            .collect(Collectors.toList());
}



    // Add a like
    @Transactional
    public void addLike(Integer postId, Integer userProfileId) {
        if (postLikeRepository.existsByPostIdAndUserProfileId(postId, userProfileId)) {
            throw new IllegalArgumentException("User already liked this post");
        }
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getPrivate() && !post.getUserProfileId().equals(userProfileId)) {
            boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(userProfileId, post.getUserProfileId());
            if (!isFollower) {
                throw new IllegalArgumentException("Cannot like private post without following");
            }
        }
        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        PostLike like = new PostLike();
        like.setPostId(postId);
        like.setUserProfileId(userProfileId);
        postLikeRepository.save(like);
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    // Remove a like
    @Transactional
    public void removeLike(Integer postId, Integer userProfileId) {
        PostLike like = postLikeRepository.findByPostIdAndUserProfileId(postId, userProfileId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postLikeRepository.delete(like);
        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);
    }

//    @Transactional
//    public void removeLike(Integer postId, Integer userProfileId) {
//        // Check if like exists
//        PostLike like = postLikeRepository.findByPostIdAndUserProfileId(postId, userProfileId)
//                .orElseThrow(() -> new RuntimeException("Like not found"));
//
//        // Get the post
//        PostModel post = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//
//        // Remove like
//        postLikeRepository.delete(like);
//
//        // Decrement like count safely
//        post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
//
//        postRepository.save(post);
//    }


    // Add a comment
    @Transactional
    public Comment addComment(Integer postId, Integer userProfileId, String content) {
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getPrivate() && !post.getUserProfileId().equals(userProfileId)) {
            boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(userProfileId, post.getUserProfileId());
            if (!isFollower) {
                throw new IllegalArgumentException("Cannot comment on private post without following");
            }
        }
        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserProfileId(userProfileId);
        comment.setContent(content);
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);
        return commentRepository.save(comment);
    }

    // Add a reply to a comment
    @Transactional
    public CommentReply addReply(Long commentId, Integer userProfileId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        PostModel post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (post.getPrivate() && !post.getUserProfileId().equals(userProfileId)) {
            boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(userProfileId, post.getUserProfileId());
            if (!isFollower) {
                throw new IllegalArgumentException("Cannot reply to comment on private post without following");
            }
        }
        UserprofileResponse user = postServiceClient.validateProfile(userProfileId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        CommentReply reply = new CommentReply();
        reply.setCommentId(commentId);
        reply.setUserProfileId(userProfileId);
        reply.setContent(content);
        return commentReplyRepository.save(reply);
    }

    // Get comments for a post
//    public List<Comment> getCommentsByPostId(Integer postId) {
//        return commentRepository.findByPostId(postId);
//    }
//
//    // Get replies for a comment
//    public List<CommentReply> getRepliesByCommentId(Long commentId) {
//        return commentRepository.findById(commentId)
//                .map(comment -> commentReplyRepository.findByCommentId(commentId))
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//    }


    // Update getCommentsByPostId

    //usernamefethch
//    public List<Comment> getCommentsByPostId(Integer postId) {
//        List<Comment> comments = commentRepository.findByPostId(postId);
//        return comments.stream()
//                .map(comment -> {
//                    String username = fetchUsernameForPost(comment.getUserProfileId());
//                    comment.setUsername(username != null ? username : "Unknown");
//                    return comment;
//                })
//                .collect(Collectors.toList());
//    }

    //corect
    public List<Comment> getCommentsByPostId(Integer postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(comment -> {
                    UserprofileResponse user = null;
                    try {
                        user = postServiceClient.validateProfile(comment.getUserProfileId());
                    } catch (Exception e) {
                        System.out.println("Failed to fetch user profile for userProfileId "
                                + comment.getUserProfileId() + ": " + e.getMessage());
                    }

                    String username = "Unknown";
                    if (user != null) {
                        username = fetchUsernameForPost(user.getUserId()); // ✅ now using user.getUserId()
                        if (user.getProfilePictureUrl() != null) {
                            comment.setProfilePictureUrl(user.getProfilePictureUrl());
                        } else {
                            comment.setProfilePictureUrl("https://placehold.co/30x30/1a1a1a/ffffff?text=U");
                        }
                    } else {
                        comment.setProfilePictureUrl("https://placehold.co/30x30/1a1a1a/ffffff?text=U");
                    }

                    comment.setUsername(username);
                    return comment;
                })
                .collect(Collectors.toList());
    }

//    public List<PostController.CommentResponse> getCommentsByPostId(Integer postId) {
//        List<Comment> comments = commentRepository.findByPostId(postId);
//
//        return comments.stream()
//                .map(comment -> {
//                    String username = "Unknown";
//                    String profilePictureUrl = "https://placehold.co/30x30/1a1a1a/ffffff?text=U";
//
//                    try {
//                        UserprofileResponse user = postServiceClient.validateProfile(comment.getUserProfileId());
//                        if (user != null) {
//                            username = user.getUsername() != null ? user.getUsername() : username;
//                            profilePictureUrl = user.getProfilePictureUrl() != null ? user.getProfilePictureUrl() : profilePictureUrl;
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch profile for userProfileId " + comment.getUserProfileId() + ": " + e.getMessage());
//                    }
//
//                    return new PostController.CommentResponse(
//                            comment.getCommentId(),
//                            comment.getPostId(),
//                            comment.getUserProfileId(),
//                            username,
//                            profilePictureUrl,
//                            comment.getContent()
//                    );
//                })
//                .collect(Collectors.toList());
//    }


    // Update getRepliesByCommentId

    //commenfetch
//    public List<CommentReply> getRepliesByCommentId(Long commentId) {
//        return commentRepository.findById(commentId)
//                .map(comment -> commentReplyRepository.findByCommentId(commentId).stream()
//                        .map(reply -> {
//                            String username = fetchUsernameForPost(reply.getUserProfileId());
//                            reply.setUsername(username != null ? username : "Unknown");
//                            return reply;
//                        })
//                        .collect(Collectors.toList()))
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//    }


    public List<CommentReply> getRepliesByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> commentReplyRepository.findByCommentId(commentId).stream()
                        .map(reply -> {
                            UserprofileResponse user = null;
                            try {
                                user = postServiceClient.validateProfile(reply.getUserProfileId());
                            } catch (Exception e) {
                                System.out.println("Failed to fetch user profile for userProfileId "
                                        + reply.getUserProfileId() + ": " + e.getMessage());
                            }

                            String username = "Unknown";
                            if (user != null) {
                                username = fetchUsernameForPost(user.getUserId()); // ✅ same as comments
                                if (user.getProfilePictureUrl() != null) {
                                    reply.setProfilePictureUrl(user.getProfilePictureUrl());
                                } else {
                                    reply.setProfilePictureUrl("https://placehold.co/25x25/1a1a1a/ffffff?text=U");
                                }
                            } else {
                                reply.setProfilePictureUrl("https://placehold.co/25x25/1a1a1a/ffffff?text=U");
                            }

                            reply.setUsername(username);
                            return reply;
                        })
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }


    // Delete a post
    @Transactional
    public void deletePost(Integer postId) {
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }

    // Get post count
    public long getPostCount() {
        return postRepository.count();
    }

    // Follow a user
    @Transactional
    public void followUser(Integer followerId, Integer followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }
        if (followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new IllegalArgumentException("Already following this user");
        }
        UserprofileResponse follower = postServiceClient.validateProfile(followerId);
        UserprofileResponse followed = postServiceClient.validateProfile(followedId);
        if (follower == null || followed == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowedId(followedId);
        followRepository.save(follow);
    }

    // Unfollow a user
    @Transactional
    public void unfollowUser(Integer followerId, Integer followedId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new RuntimeException("Follow relationship not found"));
        followRepository.delete(follow);
    }

    // Check if user is a follower
    public boolean isFollower(Integer followerId, Integer followedId) {
        return followRepository.existsByFollowerIdAndFollowedId(followerId, followedId);
    }

    // Get followers of a user
//    public List<Integer> getFollowers(Integer userProfileId) {
//        return followRepository.findByFollowedId(userProfileId)
//                .stream()
//                .map(Follow::getFollowerId)
//                .collect(Collectors.toList());
//    }


    //corect
//    public List<PostController.FollowResponse> getFollowers(Integer userProfileId) {
//        return followRepository.findByFollowedId(userProfileId)
//                .stream()
//                .map(follow -> {
//                    String username = null;
//                    UserprofileResponse user = null;
//
//                    // Fetch username first (like fetchUsernameForPost)
//                    try {
//                        username = fetchUsernameForPost(follow.getFollowerId());
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch username for followerId " + follow.getFollowerId() + ": " + e.getMessage());
//                    }
//
//                    // Fetch profile picture from UserProfileService
//                    try {
//                        user = postServiceClient.validateProfile(follow.getFollowerId());
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch user profile for followerId " + follow.getFollowerId() + ": " + e.getMessage());
//                    }
//
//                    // Determine final username and profile picture
//                    String finalUsername = username != null ? username
//                            : (user != null && user.getUsername() != null ? user.getUsername() : "Unknown");
//                    String profilePictureUrl = (user != null && user.getProfilePictureUrl() != null)
//                            ? user.getProfilePictureUrl()
//                            : "https://placehold.co/30x30/1a1a1a/ffffff?text=U";
//
//                    return new PostController.FollowResponse(follow.getFollowerId(), finalUsername, profilePictureUrl);
//                })
//                .collect(Collectors.toList());
//    }


    // Get users followed by a user
//    public List<Integer> getFollowing(Integer userProfileId) {
//        return followRepository.findByFollowerId(userProfileId)
//                .stream()
//                .map(Follow::getFollowedId)
//                .collect(Collectors.toList());
//    }


    // Update getFollowing method
    //corect
//    public List<PostController.FollowResponse> getFollowing(Integer userProfileId) {
//        return followRepository.findByFollowerId(userProfileId)
//                .stream()
//                .map(follow -> {
//                    String username = null;
//                    UserprofileResponse user = null;
//
//                    // Fetch username first (like fetchUsernameForPost)
//                    try {
//                        username = fetchUsernameForPost(follow.getFollowedId());
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch username for followedId " + follow.getFollowedId() + ": " + e.getMessage());
//                    }
//
//                    // Fetch profile picture from UserProfileService
//                    try {
//                        user = postServiceClient.validateProfile(follow.getFollowedId());
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch user profile for followedId " + follow.getFollowedId() + ": " + e.getMessage());
//                    }
//
//                    // Determine final username and profile picture
//                    String finalUsername = username != null ? username
//                            : (user != null && user.getUsername() != null ? user.getUsername() : "Unknown");
//                    String profilePictureUrl = (user != null && user.getProfilePictureUrl() != null)
//                            ? user.getProfilePictureUrl()
//                            : "https://placehold.co/30x30/1a1a1a/ffffff?text=U";
//
//                    return new PostController.FollowResponse(follow.getFollowedId(), finalUsername, profilePictureUrl);
//                })
//                .collect(Collectors.toList());
//    }

    public List<PostController.FollowResponse> getFollowers(Integer userProfileId) {
        return followRepository.findByFollowedId(userProfileId)
                .stream()
                .map(follow -> {
                    String username = null;
                    UserprofileResponse user = null;

                    try {
                        user = postServiceClient.validateProfile(follow.getFollowerId());
                        if (user != null && user.getUserId() != null) {
                            username = postServiceClient.getUsername(user.getUserId());
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to fetch user profile or username for followerId "
                                + follow.getFollowerId() + ": " + e.getMessage());
                    }

                    String finalUsername = username != null ? username
                            : (user != null && user.getUsername() != null ? user.getUsername() : "Unknown");
                    String profilePictureUrl = (user != null && user.getProfilePictureUrl() != null)
                            ? user.getProfilePictureUrl()
                            : "https://placehold.co/30x30/1a1a1a/ffffff?text=U";

                    return new PostController.FollowResponse(
                            follow.getFollowerId(), // The user who follows
                            userProfileId, // The user being followed (current profile)
                            finalUsername,
                            profilePictureUrl
                    );
                })
                .collect(Collectors.toList());
    }

    // Get users followed by a userProfileId
    public List<PostController.FollowResponse> getFollowing(Integer userProfileId) {
        return followRepository.findByFollowerId(userProfileId)
                .stream()
                .map(follow -> {
                    String username = null;
                    UserprofileResponse user = null;

                    try {
                        user = postServiceClient.validateProfile(follow.getFollowedId());
                        if (user != null && user.getUserId() != null) {
                            username = postServiceClient.getUsername(user.getUserId());
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to fetch user profile or username for followedId "
                                + follow.getFollowedId() + ": " + e.getMessage());
                    }

                    String finalUsername = username != null ? username
                            : (user != null && user.getUsername() != null ? user.getUsername() : "Unknown");
                    String profilePictureUrl = (user != null && user.getProfilePictureUrl() != null)
                            ? user.getProfilePictureUrl()
                            : "https://placehold.co/30x30/1a1a1a/ffffff?text=U";

                    return new PostController.FollowResponse(
                            userProfileId, // The user who is following (current user)
                            follow.getFollowedId(), // The user being followed
                            finalUsername,
                            profilePictureUrl
                    );
                })
                .collect(Collectors.toList());
    }


    // Send a message
    @Transactional
    public Message sendMessage(Integer senderId, Integer receiverId, String content) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot send message to yourself");
        }
        // Check mutual follow status
        boolean senderFollowsReceiver = followRepository.existsByFollowerIdAndFollowedId(senderId, receiverId);
        boolean receiverFollowsSender = followRepository.existsByFollowerIdAndFollowedId(receiverId, senderId);
        if (!senderFollowsReceiver || !receiverFollowsSender) {
            throw new IllegalArgumentException("Messaging is allowed only between mutual followers");
        }
        UserprofileResponse sender = postServiceClient.validateProfile(senderId);
        UserprofileResponse receiver = postServiceClient.validateProfile(receiverId);
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setIsRead(false);
        return messageRepository.save(message);
    }

    // Get conversation between two users
    public List<Message> getConversation(Integer userId1, Integer userId2) {
        boolean mutualFollow = followRepository.existsByFollowerIdAndFollowedId(userId1, userId2) &&
                followRepository.existsByFollowerIdAndFollowedId(userId2, userId1);
        if (!mutualFollow) {
            throw new IllegalArgumentException("Conversation accessible only between mutual followers");
        }
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(userId1, userId2, userId2, userId1);
    }

    // Get all messages received by a user
    public List<Message> getReceivedMessages(Integer receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    // Get all messages sent by a user
    public List<Message> getSentMessages(Integer senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    // Mark message as read
    @Transactional
    public void markMessageAsRead(Long messageId, Integer userProfileId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        if (!message.getReceiverId().equals(userProfileId)) {
            throw new IllegalArgumentException("Only the receiver can mark the message as read");
        }
        message.setIsRead(true);
        messageRepository.save(message);
    }


    // Create a story
    @Transactional
    public Story createStory(Story story) {
        if (story.getUserProfileId() == null) {
            throw new IllegalArgumentException("User profile ID is missing");
        }
        UserprofileResponse user = postServiceClient.validateProfile(story.getUserProfileId());
        if (user == null) {
            throw new IllegalArgumentException("Invalid user profile");
        }
        return storyRepository.save(story);
    }

    // Get story by ID with visibility check
    public Optional<Story> getStoryById(Long storyId, Integer requestingUserProfileId) {
        Optional<Story> story = storyRepository.findById(storyId);
        if (story.isEmpty()) {
            return Optional.empty();
        }
        Story s = story.get();
        if (s.getCreatedAt().isBefore(LocalDateTime.now().minusHours(24))) {
            return Optional.empty(); // Story expired
        }
        if (s.getIsPrivate() && !s.getUserProfileId().equals(requestingUserProfileId)) {
            boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, s.getUserProfileId());
            if (!isFollower) {
                return Optional.empty(); // Deny access to private story
            }
        }
        return story;
    }

    // Get active stories by user profile ID with visibility check
    public List<Story> getStoriesByUserProfileId(Integer userProfileId, Integer requestingUserProfileId) {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(24);
        List<Story> stories = storyRepository.findActiveStoriesByUserProfileId(userProfileId, expiryTime);
        boolean isFollower = followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, userProfileId);
        return stories.stream()
                .filter(s -> !s.getIsPrivate() || s.getUserProfileId().equals(requestingUserProfileId) || isFollower)
                .collect(Collectors.toList());
    }

    // Get active stories from followed users
    public List<Story> getFollowedUsersStories(Integer requestingUserProfileId) {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(24);
        List<PostController.FollowResponse> followedIds = getFollowing(requestingUserProfileId);
        List<Story> stories = storyRepository.findActiveStories(expiryTime);
        return stories.stream()
                .filter(s -> s.getUserProfileId().equals(requestingUserProfileId) ||
                        (!s.getIsPrivate() || followedIds.contains(s.getUserProfileId())))
                .collect(Collectors.toList());
    }

    // Delete a story
    @Transactional
    public void deleteStory(Long storyId, Integer userProfileId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        if (!story.getUserProfileId().equals(userProfileId)) {
            throw new IllegalArgumentException("Only the story owner can delete the story");
        }
        storyRepository.delete(story);
    }

    private List<Integer> getLikedByUserProfileIds(Integer postId) {
        return postLikeRepository.findByPostId(postId)
                .stream()
                .map(PostLike::getUserProfileId)
                .collect(Collectors.toList());
    }

    public boolean isPostLikedByUser(Integer postId, Integer userProfileId) {
        return postLikeRepository.existsByPostIdAndUserProfileId(postId, userProfileId);
    }


    public String fetchUsernameForPost(Integer userId) {
        try {
            // This calls UserProfileService /username/{userId} via Feign
            return postServiceClient.getUsername(userId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch username: " + e.getMessage());
        }
    }


    @Transactional
    public void deleteComment(Long commentId, Integer requestingUserProfileId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Only comment owner or post owner can delete the comment
        PostModel post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!comment.getUserProfileId().equals(requestingUserProfileId) &&
                !post.getUserProfileId().equals(requestingUserProfileId)) {
            throw new IllegalArgumentException("Unauthorized: only comment owner or post owner can delete");
        }

        // Delete all replies associated with this comment
        List<CommentReply> replies = commentReplyRepository.findByCommentId(commentId);
        commentReplyRepository.deleteAll(replies);

        // Delete the comment
        commentRepository.delete(comment);

        // Decrement post comment count safely
        post.setCommentCount(Math.max(0, post.getCommentCount() - 1));
        postRepository.save(post);
    }


    @Transactional
    public void deleteCommentReply(Long replyId, Integer requestingUserProfileId) {
        CommentReply reply = commentReplyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        Comment comment = commentRepository.findById(reply.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        PostModel post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!reply.getUserProfileId().equals(requestingUserProfileId) &&
                !post.getUserProfileId().equals(requestingUserProfileId)) {
            throw new IllegalArgumentException("Unauthorized: only reply owner or post owner can delete");
        }

        commentReplyRepository.delete(reply);
    }


    // In com.InstaSpehere.Post_Add_Service.service.PostService
//    public List<PostController.EnhancedPostResponse> getEnhancedFeed(Integer requestingUserProfileId) {
//        // 1. Get list of users the requester follows
//        List<Integer> followingIds = followRepository.findByFollowerId(requestingUserProfileId)
//                .stream()
//                .map(Follow::getFollowedId)
//                .collect(Collectors.toList());
//
//        // Include the user’s own posts too
//        followingIds.add(requestingUserProfileId);
//
//        // 2. Fetch posts from these users
//        List<PostModel> posts = postRepository.findByUserProfileIdIn(followingIds);
//
//        // 3. Apply access control (only allow private if the requester follows them)
//        return posts.stream()
//                .filter(p -> {
//                    if (Boolean.TRUE.equals(p.getPrivate()) && !p.getUserProfileId().equals(requestingUserProfileId)) {
//                        return followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
//                    }
//                    return true;
//                })
//                .map(p -> {
//                    UserprofileResponse user = null;
//                    try {
//                        user = postServiceClient.validateProfile(p.getUserProfileId());
//                    } catch (Exception e) {
//                        System.out.println("Failed to fetch user profile for userProfileId " + p.getUserProfileId() + ": " + e.getMessage());
//                    }
//
//                    String username = "Unknown";
//                    String profilePictureUrl = "https://placehold.co/30x30/1a1a1a/ffffff?text=U";
//                    if (user != null) {
//                        username = postServiceClient.getUsername(user.getUserId());
//                        profilePictureUrl = user.getProfilePictureUrl() != null ? user.getProfilePictureUrl() : profilePictureUrl;
//                    }
//
//                    return new PostController.EnhancedPostResponse(
//                            p.getPostId(), p.getUserProfileId(), username, profilePictureUrl,
//                            p.getCaption(), p.getMediaUrl(), p.getMediaType(), p.getPrivate(),
//                            p.getLikeCount(), p.getCommentCount(), getLikedByUserProfileIds(p.getPostId())
//                    );
//                })
//                .collect(Collectors.toList());
//    }

    public List<PostController.EnhancedPostResponse> getEnhancedFeed(Integer requestingUserProfileId) {
        // 1. Get list of users the requester follows
        List<Integer> followingIds = followRepository.findByFollowerId(requestingUserProfileId)
                .stream()
                .map(Follow::getFollowedId)
                .collect(Collectors.toList());

        // Include the user’s own posts too
        followingIds.add(requestingUserProfileId);

        // 2. Fetch posts from these users
        List<PostModel> posts = postRepository.findByUserProfileIdIn(followingIds);

        // 3. Apply access control and filter out banned posts
        return posts.stream()
                .filter(p -> !p.getIsBanned()) // Filter out banned posts
                .filter(p -> {
                    if (Boolean.TRUE.equals(p.getPrivate()) && !p.getUserProfileId().equals(requestingUserProfileId)) {
                        return followRepository.existsByFollowerIdAndFollowedId(requestingUserProfileId, p.getUserProfileId());
                    }
                    return true;
                })
                .map(p -> {
                    UserprofileResponse user = null;
                    try {
                        user = postServiceClient.validateProfile(p.getUserProfileId());
                    } catch (Exception e) {
                        System.out.println("Failed to fetch user profile for userProfileId " + p.getUserProfileId() + ": " + e.getMessage());
                    }

                    String username = "Unknown";
                    String profilePictureUrl = "https://placehold.co/30x30/1a1a1a/ffffff?text=U";
                    if (user != null) {
                        username = postServiceClient.getUsername(user.getUserId());
                        profilePictureUrl = user.getProfilePictureUrl() != null ? user.getProfilePictureUrl() : profilePictureUrl;
                    }

                    return new PostController.EnhancedPostResponse(
                            p.getPostId(), p.getUserProfileId(), username, profilePictureUrl,
                            p.getCaption(), p.getMediaUrl(), p.getMediaType(), p.getPrivate(),
                            p.getLikeCount(), p.getCommentCount(), getLikedByUserProfileIds(p.getPostId())
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void reportPost(Integer postId, Integer userProfileId) {
        // Check if post exists
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if user has already reported this post
        if (postReportRepository.existsByPostIdAndUserProfileId(postId, userProfileId)) {
            throw new IllegalArgumentException("You have already reported this post");
        }

        // Create a new report
        PostReport report = new PostReport();
        report.setPostId(postId);
        report.setUserProfileId(userProfileId);
        postReportRepository.save(report);

        // Check report count and create alert if threshold met
        long reportCount = postReportRepository.countByPostId(postId);
        if (reportCount >= 1) { // Lowered threshold to 1 for testing
            // Check if an alert already exists for this post
            if (!adminAlertRepository.existsByPostId(postId)) {
                AdminAlert alert = new AdminAlert();
                alert.setPostId(postId);
                alert.setMessage("Post with ID " + postId + " has been reported by " + reportCount + " users.");
                alert.setCreatedAt(LocalDateTime.now()); // Explicitly set created_at
                alert.setIsResolved(false); // Explicitly set is_resolved
                adminAlertRepository.save(alert);
            }
        }
    }
    @Transactional
    public void banPost(Integer postId, Integer adminUserProfileId) {
        // Verify admin (you may have a separate way to check admin privileges)
        // For simplicity, assuming adminUserProfileId is validated elsewhere

        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Ban the post
        post.setIsBanned(true);
        postRepository.save(post);

        // Mark related alerts as resolved
        List<AdminAlert> alerts = adminAlertRepository.findByPostId(postId);
        for (AdminAlert alert : alerts) {
            alert.setIsResolved(true);
            adminAlertRepository.save(alert);
        }
    }

    @Transactional
    public void unbanPost(Integer postId, Integer adminUserProfileId) {
        // Verify admin (assumed validated elsewhere)
        PostModel post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Unban the post
        post.setIsBanned(false);
        postRepository.save(post);
    }

    public List<AdminAlert> getPendingAlerts() {
        return adminAlertRepository.findByIsResolvedFalse();
    }

    public List<PostModel> getBannedPosts() {
        return postRepository.findByIsBannedTrue();
    }
}