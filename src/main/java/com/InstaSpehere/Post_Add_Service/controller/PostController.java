//package com.InstaSpehere.Post_Add_Service.controller;
//
//import com.InstaSpehere.Post_Add_Service.service.PostService;
//
//
//import com.InstaSpehere.Post_Add_Service.model.*;
//import com.InstaSpehere.Post_Add_Service.service.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
//@RestController
//@RequestMapping("/api/posts")
//public class PostController {
//
//    @Autowired
//    private PostService postService;
//
//    // DTO for post response
//    public static class PostResponse {
//        private Integer postId;
//        private Integer userProfileId;
//        private String caption;
//        private String mediaUrl;
//        private PostModel.MediaType mediaType;
//        private Boolean isPrivate;
//        private Integer likeCount;
//        private Integer commentCount;
//
//        public PostResponse(Integer postId, Integer userProfileId, String caption, String mediaUrl,
//                            PostModel.MediaType mediaType, Boolean isPrivate, Integer likeCount, Integer commentCount) {
//            this.postId = postId;
//            this.userProfileId = userProfileId;
//            this.caption = caption;
//            this.mediaUrl = mediaUrl;
//            this.mediaType = mediaType;
//            this.isPrivate = isPrivate;
//            this.likeCount = likeCount;
//            this.commentCount = commentCount;
//        }
//
//        // Getters
//        public Integer getPostId() { return postId; }
//        public Integer getUserProfileId() { return userProfileId; }
//        public String getCaption() { return caption; }
//        public String getMediaUrl() { return mediaUrl; }
//        public PostModel.MediaType getMediaType() { return mediaType; }
//        public Boolean getIsPrivate() { return isPrivate; }
//        public Integer getLikeCount() { return likeCount; }
//        public Integer getCommentCount() { return commentCount; }
//    }
//
//    // DTO for comment response
//    public static class CommentResponse {
//        private Long commentId;
//        private Integer postId;
//        private Integer userProfileId;
//        private String content;
//
//        public CommentResponse(Long commentId, Integer postId, Integer userProfileId, String content) {
//            this.commentId = commentId;
//            this.postId = postId;
//            this.userProfileId = userProfileId;
//            this.content = content;
//        }
//
//        // Getters
//        public Long getCommentId() { return commentId; }
//        public Integer getPostId() { return postId; }
//        public Integer getUserProfileId() { return userProfileId; }
//        public String getContent() { return content; }
//    }
//
//    // DTO for comment reply response
//    public static class CommentReplyResponse {
//        private Long replyId;
//        private Long commentId;
//        private Integer userProfileId;
//        private String content;
//
//        public CommentReplyResponse(Long replyId, Long commentId, Integer userProfileId, String content) {
//            this.replyId = replyId;
//            this.commentId = commentId;
//            this.userProfileId = userProfileId;
//            this.content = content;
//        }
//
//        // Getters
//        public Long getReplyId() { return replyId; }
//        public Long getCommentId() { return commentId; }
//        public Integer getUserProfileId() { return userProfileId; }
//        public String getContent() { return content; }
//    }
//
//    // DTO for comment and reply request
//    public static class CommentRequest {
//        private Integer userProfileId;
//        private String content;
//
//        public Integer getUserProfileId() { return userProfileId; }
//        public void setUserProfileId(Integer userProfileId) { this.userProfileId = userProfileId; }
//        public String getContent() { return content; }
//        public void setContent(String content) { this.content = content; }
//    }
//
//    // Create a post
//    @PostMapping("/add")
//    public ResponseEntity<String> addPost(@RequestBody PostModel post) {
//        try {
//            postService.createPost(post);
//            return ResponseEntity.ok("Post added successfully!");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Get post by ID
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
//        try {
//            Optional<PostModel> post = postService.getPostById(id);
//            if (post.isPresent()) {
//                PostModel p = post.get();
//                return ResponseEntity.ok(new PostResponse(
//                        p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
//                        p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount()));
//            } else {
//                return new ResponseEntity<>("Post not found", HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
//        }
//    }
//
//    // Get posts by user profile ID
//    @GetMapping("/byUserProfileId/{userProfileId}")
//    public ResponseEntity<List<PostResponse>> getPostsByUserProfileId(@PathVariable Integer userProfileId) {
//        try {
//            List<PostModel> posts = postService.getPostsByUserProfileId(userProfileId);
//            List<PostResponse> response = posts.stream()
//                    .map(p -> new PostResponse(
//                            p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
//                            p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Validate post existence by ID
//    @GetMapping("/validate/{id}")
//    public ResponseEntity<Boolean> validatePostById(@PathVariable Integer id) {
//        boolean exists = postService.getPostById(id).isPresent();
//        return ResponseEntity.ok(exists);
//    }
//
//    // Update a post
//    @PutMapping("/update/{postId}")
//    public ResponseEntity<String> updatePost(@PathVariable Integer postId, @RequestBody PostModel post) {
//        try {
//            Optional<PostModel> existingPost = postService.getPostById(postId);
//            if (existingPost.isEmpty()) {
//                return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
//            }
//            post.setPostId(postId);
//            postService.createPost(post); // Reuses createPost for simplicity, as it includes validation
//            return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
////    // Delete a post
////    @DeleteMapping("/delete/{id}")
////    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
////        try {
////            Optional<PostModel> existingPost = postService.getPostById(id);
////            if (existingPost.isPresent()) {
////                postService.deletePost(id);
////                return ResponseEntity.ok("Post deleted successfully!");
////            } else {
////                return ResponseEntity.ok("No post found");
////            }
////        } catch (Exception e) {
////            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
////        }
////    }
//
//    // Add a like
//    @PostMapping("/{postId}/likes")
//    public ResponseEntity<String> addLike(@PathVariable Integer postId, @RequestBody CommentRequest request) {
//        try {
//            postService.addLike(postId, request.getUserProfileId());
//            return ResponseEntity.ok("Like added successfully!");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Remove a like
//    @DeleteMapping("/{postId}/likes")
//    public ResponseEntity<String> removeLike(@PathVariable Integer postId, @RequestBody CommentRequest request) {
//        try {
//            postService.removeLike(postId, request.getUserProfileId());
//            return ResponseEntity.ok("Like removed successfully!");
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Add a comment
//    @PostMapping("/{postId}/comments")
//    public ResponseEntity<String> addComment(@PathVariable Integer postId, @RequestBody CommentRequest request) {
//        try {
//            postService.addComment(postId, request.getUserProfileId(), request.getContent());
//            return ResponseEntity.ok("Comment added successfully!");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Add a reply to a comment
//    @PostMapping("/comments/{commentId}/replies")
//    public ResponseEntity<String> addReply(@PathVariable Long commentId, @RequestBody CommentRequest request) {
//        try {
//            postService.addReply(commentId, request.getUserProfileId(), request.getContent());
//            return ResponseEntity.ok("Reply added successfully!");
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Get comments for a post
//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Integer postId) {
//        try {
//            List<Comment> comments = postService.getCommentsByPostId(postId);
//            List<CommentResponse> response = comments.stream()
//                    .map(c -> new CommentResponse(c.getCommentId(), c.getPostId(), c.getUserProfileId(), c.getContent()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Get replies for a comment
//    @GetMapping("/comments/{commentId}/replies")
//    public ResponseEntity<List<CommentReplyResponse>> getRepliesByCommentId(@PathVariable Long commentId) {
//        try {
//            List<CommentReply> replies = postService.getRepliesByCommentId(commentId);
//            List<CommentReplyResponse> response = replies.stream()
//                    .map(r -> new CommentReplyResponse(r.getReplyId(), r.getCommentId(), r.getUserProfileId(), r.getContent()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Get post count
////    @GetMapping("/count")
////    public ResponseEntity<Long> getPostCount() {
////        try {
////            long count = postService.getPostCount();
////            return ResponseEntity.ok(count);
////        } catch (Exception e) {
////            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//}

package com.InstaSpehere.Post_Add_Service.controller;

import com.InstaSpehere.Post_Add_Service.model.*;
import com.InstaSpehere.Post_Add_Service.repository.CommentRepository;
import com.InstaSpehere.Post_Add_Service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;


    @Autowired
    private CommentRepository commentRepository;

    // DTO for post response
    public static class PostResponse {
        private Integer postId;
        private Integer userProfileId;
        private String caption;
        private String mediaUrl;
        private PostModel.MediaType mediaType;
        private Boolean isPrivate;
        private Integer likeCount;
        private Integer commentCount;

        private List<Integer> likedByUserProfileIds;

        public PostResponse(Integer postId, Integer userProfileId, String caption, String mediaUrl, PostModel.MediaType mediaType, Boolean isPrivate, Integer likeCount, Integer commentCount, List<Integer> likedByUserProfileIds) {
            this.postId = postId;
            this.userProfileId = userProfileId;
            this.caption = caption;
            this.mediaUrl = mediaUrl;
            this.mediaType = mediaType;
            this.isPrivate = isPrivate;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.likedByUserProfileIds = likedByUserProfileIds;
        }

        public Integer getPostId() { return postId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getCaption() { return caption; }
        public String getMediaUrl() { return mediaUrl; }
        public PostModel.MediaType getMediaType() { return mediaType; }
        public Boolean getIsPrivate() { return isPrivate; }
        public Integer getLikeCount() { return likeCount; }
        public Integer getCommentCount() { return commentCount; }

        public List<Integer> getLikedByUserProfileIds() { return likedByUserProfileIds; }

    }

    // In com.InstaSpehere.Post_Add_Service.controller.PostController
    public static class EnhancedPostResponse {
        private Integer postId;
        private Integer userProfileId;
        private String username;
        private String profilePictureUrl;
        private String caption;
        private String mediaUrl;
        private PostModel.MediaType mediaType;
        private Boolean isPrivate;
        private Integer likeCount;
        private Integer commentCount;
        private List<Integer> likedByUserProfileIds;

        public EnhancedPostResponse(Integer postId, Integer userProfileId, String username, String profilePictureUrl,
                                    String caption, String mediaUrl, PostModel.MediaType mediaType, Boolean isPrivate,
                                    Integer likeCount, Integer commentCount, List<Integer> likedByUserProfileIds) {
            this.postId = postId;
            this.userProfileId = userProfileId;
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
            this.caption = caption;
            this.mediaUrl = mediaUrl;
            this.mediaType = mediaType;
            this.isPrivate = isPrivate;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.likedByUserProfileIds = likedByUserProfileIds;
        }

        public Integer getPostId() { return postId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getUsername() { return username; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
        public String getCaption() { return caption; }
        public String getMediaUrl() { return mediaUrl; }
        public PostModel.MediaType getMediaType() { return mediaType; }
        public Boolean getIsPrivate() { return isPrivate; }
        public Integer getLikeCount() { return likeCount; }
        public Integer getCommentCount() { return commentCount; }
        public List<Integer> getLikedByUserProfileIds() { return likedByUserProfileIds; }
    }
    // DTO for comment response
    public static class CommentResponse {
        private Long commentId;
        private Integer postId;
        private Integer userProfileId;
        private String username;
        private String profilePictureUrl;
        private String content;

        private LocalDateTime createdAt;

        // Reorder parameters to match mapping: commentId, postId, userProfileId, username, profilePictureUrl, content
        public CommentResponse(Long commentId, Integer postId, Integer userProfileId, String content,String username, String profilePictureUrl,LocalDateTime createdAt) {
            this.commentId = commentId;
            this.postId = postId;
            this.userProfileId = userProfileId;
            this.content = content;
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
            this.createdAt=createdAt;

        }

        public Long getCommentId() { return commentId; }
        public Integer getPostId() { return postId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getContent() { return content; }

        public String getUsername() {
            return username;
        }

        public String getProfilePictureUrl() {
            return profilePictureUrl;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    // DTO for comment reply response
    public static class CommentReplyResponse {
        private Long replyId;
        private Long commentId;
        private Integer userProfileId;
        private String username;
        private String profilePictureUrl;
        private String content;

        private LocalDateTime createdAt;


        // Reorder parameters to match mapping: replyId, commentId, userProfileId, username, profilePictureUrl, content
        public CommentReplyResponse(Long replyId, Long commentId, Integer userProfileId, String username, String profilePictureUrl, String content,LocalDateTime createdAt) {
            this.replyId = replyId;
            this.commentId = commentId;
            this.userProfileId = userProfileId;
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
            this.content = content;
            this.createdAt=createdAt;
        }

        public Long getReplyId() { return replyId; }
        public Long getCommentId() { return commentId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getContent() { return content; }

        public String getUsername() {
            return username;
        }

        public String getProfilePictureUrl() {
            return profilePictureUrl;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    // DTO for comment and reply request
    public static class CommentRequest {
        private Integer userProfileId;
        private String content;

        public Integer getUserProfileId() { return userProfileId; }
        public void setUserProfileId(Integer userProfileId) { this.userProfileId = userProfileId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }


    // New DTO for follow response
    public static class FollowResponse {
        private Integer followerId; // The user who is following (current user for following, follower for followers)
        private Integer followedId; // The user being followed (followedId for following, followedId for followers)
        private String username;
        private String profilePictureUrl;

        public FollowResponse(Integer followerId, Integer followedId, String username, String profilePictureUrl) {
            this.followerId = followerId;
            this.followedId = followedId;
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
        }

        public Integer getFollowerId() { return followerId; }
        public Integer getFollowedId() { return followedId; }
        public String getUsername() { return username; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
    }
    // DTO for follow request
    public static class FollowRequest {
        private Integer followerId;
        private Integer followedId;

        public Integer getFollowerId() { return followerId; }
        public void setFollowerId(Integer followerId) { this.followerId = followerId; }
        public Integer getFollowedId() { return followedId; }
        public void setFollowedId(Integer followedId) { this.followedId = followedId; }
    }

    // DTO for message request
    public static class MessageRequest {
        private Integer senderId;
        private Integer receiverId;
        private String content;

        public Integer getSenderId() { return senderId; }
        public void setSenderId(Integer senderId) { this.senderId = senderId; }
        public Integer getReceiverId() { return receiverId; }
        public void setReceiverId(Integer receiverId) { this.receiverId = receiverId; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    // DTO for message response
    public static class MessageResponse {
        private Long messageId;
        private Integer senderId;
        private Integer receiverId;
        private String content;
        private LocalDateTime createdAt;
        private Boolean isRead;

        public MessageResponse(Long messageId, Integer senderId, Integer receiverId, String content,
                               LocalDateTime createdAt, Boolean isRead) {
            this.messageId = messageId;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.content = content;
            this.createdAt = createdAt;
            this.isRead = isRead;
        }

        public Long getMessageId() { return messageId; }
        public Integer getSenderId() { return senderId; }
        public Integer getReceiverId() { return receiverId; }
        public String getContent() { return content; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public Boolean getIsRead() { return isRead; }
    }



    // DTO for story request
    public static class StoryRequest {
        private Integer userProfileId;
        private String mediaUrl;
        private Story.MediaType mediaType;
        private Boolean isPrivate;

        public Integer getUserProfileId() { return userProfileId; }
        public void setUserProfileId(Integer userProfileId) { this.userProfileId = userProfileId; }
        public String getMediaUrl() { return mediaUrl; }
        public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }
        public Story.MediaType getMediaType() { return mediaType; }
        public void setMediaType(Story.MediaType mediaType) { this.mediaType = mediaType; }
        public Boolean getIsPrivate() { return isPrivate; }
        public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }
    }

    // DTO for story response
    public static class StoryResponse {
        private Long storyId;
        private Integer userProfileId;
        private String mediaUrl;
        private Story.MediaType mediaType;
        private Boolean isPrivate;
        private LocalDateTime createdAt;

        public StoryResponse(Long storyId, Integer userProfileId, String mediaUrl,
                             Story.MediaType mediaType, Boolean isPrivate, LocalDateTime createdAt) {
            this.storyId = storyId;
            this.userProfileId = userProfileId;
            this.mediaUrl = mediaUrl;
            this.mediaType = mediaType;
            this.isPrivate = isPrivate;
            this.createdAt = createdAt;
        }

        public Long getStoryId() { return storyId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getMediaUrl() { return mediaUrl; }
        public Story.MediaType getMediaType() { return mediaType; }
        public Boolean getIsPrivate() { return isPrivate; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }

    public static class EnhancedStoryResponse {
        private Long storyId;
        private Integer userProfileId;
        private String username;
        private String profilePictureUrl;
        private String mediaUrl;
        private Story.MediaType mediaType;
        private Boolean isPrivate;
        private LocalDateTime createdAt;

        public EnhancedStoryResponse(Long storyId, Integer userProfileId, String username, String profilePictureUrl,
                                     String mediaUrl, Story.MediaType mediaType, Boolean isPrivate, LocalDateTime createdAt) {
            this.storyId = storyId;
            this.userProfileId = userProfileId;
            this.username = username;
            this.profilePictureUrl = profilePictureUrl;
            this.mediaUrl = mediaUrl;
            this.mediaType = mediaType;
            this.isPrivate = isPrivate;
            this.createdAt = createdAt;
        }

        public Long getStoryId() { return storyId; }
        public Integer getUserProfileId() { return userProfileId; }
        public String getUsername() { return username; }
        public String getProfilePictureUrl() { return profilePictureUrl; }
        public String getMediaUrl() { return mediaUrl; }
        public Story.MediaType getMediaType() { return mediaType; }
        public Boolean getIsPrivate() { return isPrivate; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }

    // Create a post
    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody PostModel post) {
        try {
            postService.createPost(post);
            return ResponseEntity.ok("Post added successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id, @RequestParam Integer requestingUserProfileId) {
        try {
            Optional<PostModel> post = postService.getPostById(id, requestingUserProfileId);
            if (post.isPresent()) {
                PostModel p = post.get();
                return ResponseEntity.ok(new PostResponse(
                        p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
                        p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),
                        p.getLikedByUserProfileIds()));
            } else {
                return new ResponseEntity<>("Post not found or access denied", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }


    @GetMapping("/feed")
    public ResponseEntity<?> getUserFeed(@RequestParam Integer requestingUserProfileId) {
        try {
            List<PostResponse> feed = postService.getFeed(requestingUserProfileId);
            return ResponseEntity.ok(feed);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Get post by ID
//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getPostById(@PathVariable Integer id, @RequestParam Integer requestingUserProfileId) {
//        try {
//            Optional<PostModel> post = postService.getPostById(id, requestingUserProfileId);
//            if (post.isPresent()) {
//                PostModel p = post.get();
//                return ResponseEntity.ok(new PostResponse(
//                        p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
//                        p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),p.get));
//            } else {
//                return new ResponseEntity<>("Post not found or access denied", HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
//        }
//    }



    @GetMapping("/byUserProfileId/{userProfileId}")
    public ResponseEntity<List<PostResponse>> getPostsByUserProfileId(@PathVariable Integer userProfileId,
                                                                      @RequestParam Integer requestingUserProfileId) {
        try {
            List<PostModel> posts = postService.getPostsByUserProfileId(userProfileId, requestingUserProfileId);
            List<PostResponse> response = posts.stream()
                    .map(p -> new PostResponse(
                            p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
                            p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),
                            p.getLikedByUserProfileIds()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Get posts by user profile ID
//    @GetMapping("/byUserProfileId/{userProfileId}")
//    public ResponseEntity<List<PostResponse>> getPostsByUserProfileId(@PathVariable Integer userProfileId,
//                                                                      @RequestParam Integer requestingUserProfileId) {
//        try {
//            List<PostModel> posts = postService.getPostsByUserProfileId(userProfileId, requestingUserProfileId);
//            List<PostResponse> response = posts.stream()
//                    .map(p -> new PostResponse(
//                            p.getPostId(), p.getUserProfileId(), p.getCaption(), p.getMediaUrl(),
//                            p.getMediaType(), p.getPrivate(), p.getLikeCount(), p.getCommentCount(),p.getLikedByUserProfileIds()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Validate post existence by ID
    @GetMapping("/validate/{id}")
    public ResponseEntity<Boolean> validatePostById(@PathVariable Integer id) {
        boolean exists = postService.getPostById(id, null).isPresent();
        return ResponseEntity.ok(exists);
    }

    // Update a post
    @PutMapping("/update/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Integer postId, @RequestBody PostModel post) {
        try {
            Optional<PostModel> existingPost = postService.getPostById(postId, post.getUserProfileId());
            if (existingPost.isEmpty()) {
                return new ResponseEntity<>("Post not found or access denied", HttpStatus.NOT_FOUND);
            }
            post.setPostId(postId);
            postService.createPost(post);
            return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id, @RequestParam Integer requestingUserProfileId) {
        try {
            Optional<PostModel> existingPost = postService.getPostById(id, requestingUserProfileId);
            if (existingPost.isEmpty()) {
                return new ResponseEntity<>("Post not found or access denied", HttpStatus.OK);
            }
            if (!existingPost.get().getUserProfileId().equals(requestingUserProfileId)) {
                return new ResponseEntity<>("Only the post owner can delete this post", HttpStatus.FORBIDDEN);
            }
            postService.deletePost(id);
            return ResponseEntity.ok("Post deleted successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a like
    @PostMapping("/{postId}/likes")
    public ResponseEntity<String> addLike(@PathVariable Integer postId, @RequestBody CommentRequest request) {
        try {
            postService.addLike(postId, request.getUserProfileId());
            return ResponseEntity.ok("Like added successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove a like
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<String> removeLike(@PathVariable Integer postId, @RequestBody CommentRequest request) {
        try {
            postService.removeLike(postId, request.getUserProfileId());
            return ResponseEntity.ok("Like removed successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/{postId}/unlikes")
//    public ResponseEntity<String> removeLike(@PathVariable Integer postId, @RequestBody Map<String, Integer> payload) {
//        try {
//            Integer userProfileId = payload.get("userProfileId");
//            if (userProfileId == null) {
//                return new ResponseEntity<>("Missing userProfileId", HttpStatus.BAD_REQUEST);
//            }
//
//            postService.removeLike(postId, userProfileId);
//            return ResponseEntity.ok("Like removed successfully!");
//
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    // Add a comment
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addComment(@PathVariable Integer postId, @RequestBody CommentRequest request) {
        try {
            postService.addComment(postId, request.getUserProfileId(), request.getContent());
            return ResponseEntity.ok("Comment added successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a reply to a comment
    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<String> addReply(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        try {
            postService.addReply(commentId, request.getUserProfileId(), request.getContent());
            return ResponseEntity.ok("Reply added successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get comments for a post
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Integer postId,
                                                                     @RequestParam Integer requestingUserProfileId) {
        try {
            Optional<PostModel> post = postService.getPostById(postId, requestingUserProfileId);
            if (post.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            List<Comment> comments = postService.getCommentsByPostId(postId);
            List<CommentResponse> response = comments.stream()
                    .map(c -> new CommentResponse(c.getCommentId(), c.getPostId(), c.getUserProfileId(), c.getContent(),c.getUsername(),c.getProfilePictureUrl(),c.getCreatedAt()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //
//    // Get replies for a comment
    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<List<CommentReplyResponse>> getRepliesByCommentId(@PathVariable Long commentId,
                                                                            @RequestParam Integer requestingUserProfileId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
            Optional<PostModel> post = postService.getPostById(comment.getPostId(), requestingUserProfileId);
            if (post.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            List<CommentReply> replies = postService.getRepliesByCommentId(commentId);
            List<CommentReplyResponse> response = replies.stream()
                    .map(r -> new CommentReplyResponse(r.getReplyId(), r.getCommentId(), r.getUserProfileId(),r.getUsername(),r.getProfilePictureUrl(),r.getContent(),r.getCreatedAt()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Update getCommentsByPostId
//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Integer postId,
//                                                                     @RequestParam Integer requestingUserProfileId) {
//        try {
//            Optional<PostModel> post = postService.getPostById(postId, requestingUserProfileId);
//            if (post.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.OK);
//            }
//            List<Comment> comments = postService.getCommentsByPostId(postId);
//            List<CommentResponse> response = comments.stream()
//                    .map(c -> new CommentResponse(c.getCommentId(), c.getPostId(), c.getUserProfileId(), c.getUsername(), c.getProfilePictureUrl(), c.getContent()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/{postId}/comments")
//    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(
//            @PathVariable Integer postId,
//            @RequestParam Integer requestingUserProfileId) {
//
//        try {
//            // Check if post exists
//            Optional<PostModel> post = postService.getPostById(postId, requestingUserProfileId);
//            if (post.isEmpty()) {
//                return ResponseEntity.ok(Collections.emptyList()); // return empty list if post not found
//            }
//
//            // Fetch comments from service (already returns CommentResponse with username & profile picture)
//            List<PostController.CommentResponse> comments = postService.getCommentsByPostId(postId);
//
//            return ResponseEntity.ok(comments);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    // Update getRepliesByCommentId
//    @GetMapping("/comments/{commentId}/replies")
//    public ResponseEntity<List<CommentReplyResponse>> getRepliesByCommentId(@PathVariable Long commentId,
//                                                                            @RequestParam Integer requestingUserProfileId) {
//        try {
//            Comment comment = commentRepository.findById(commentId)
//                    .orElseThrow(() -> new RuntimeException("Comment not found"));
//            Optional<PostModel> post = postService.getPostById(comment.getPostId(), requestingUserProfileId);
//            if (post.isEmpty()) {
//                return new ResponseEntity<>(null, HttpStatus.OK);
//            }
//            List<CommentReply> replies = postService.getRepliesByCommentId(commentId);
//            List<CommentReplyResponse> response = replies.stream()
//                    .map(r -> new CommentReplyResponse(r.getReplyId(), r.getCommentId(), r.getUserProfileId(), r.getUsername(), r.getProfilePictureUrl(), r.getContent()))
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // Get post count
    @GetMapping("/count")
    public ResponseEntity<Long> getPostCount() {
        try {
            long count = postService.getPostCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Follow a user
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestBody FollowRequest request) {
        try {
            postService.followUser(request.getFollowerId(), request.getFollowedId());
            return ResponseEntity.ok("Followed successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Unfollow a user
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestBody FollowRequest request) {
        try {
            postService.unfollowUser(request.getFollowerId(), request.getFollowedId());
            return ResponseEntity.ok("Unfollowed successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if user is a follower
    @GetMapping("/isFollower")
    public ResponseEntity<Boolean> isFollower(@RequestParam Integer followerId, @RequestParam Integer followedId) {
        try {
            boolean isFollower = postService.isFollower(followerId, followedId);
            return ResponseEntity.ok(isFollower);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get followers of a user
//    @GetMapping("/followers/{userProfileId}")
//    public ResponseEntity<List<Integer>> getFollowers(@PathVariable Integer userProfileId) {
//        try {
//            List<Integer> followers = postService.getFollowers(userProfileId);
//            return ResponseEntity.ok(followers);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/followers/{userProfileId}")
    public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable Integer userProfileId) {
        try {
            List<FollowResponse> followers = postService.getFollowers(userProfileId);
            return ResponseEntity.ok(followers);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    // Get users followed by a user
//    @GetMapping("/following/{userProfileId}")
//    public ResponseEntity<List<Integer>> getFollowing(@PathVariable Integer userProfileId) {
//        try {
//            List<Integer> following = postService.getFollowing(userProfileId);
//            return ResponseEntity.ok(following);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("/following/{userProfileId}")
    public ResponseEntity<List<FollowResponse>> getFollowing(@PathVariable Integer userProfileId) {
        try {
            List<FollowResponse> following = postService.getFollowing(userProfileId);
            return ResponseEntity.ok(following);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Send a message
    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        try {
            postService.sendMessage(request.getSenderId(), request.getReceiverId(), request.getContent());
            return ResponseEntity.ok("Message sent successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get conversation between two users
    @GetMapping("/messages/conversation")
    public ResponseEntity<List<MessageResponse>> getConversation(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        try {
            List<Message> messages = postService.getConversation(userId1, userId2);
            List<MessageResponse> response = messages.stream()
                    .map(m -> new MessageResponse(m.getMessageId(), m.getSenderId(), m.getReceiverId(),
                            m.getContent(), m.getCreatedAt(), m.getIsRead()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get received messages
    @GetMapping("/messages/received/{userProfileId}")
    public ResponseEntity<List<MessageResponse>> getReceivedMessages(@PathVariable Integer userProfileId) {
        try {
            List<Message> messages = postService.getReceivedMessages(userProfileId);
            List<MessageResponse> response = messages.stream()
                    .map(m -> new MessageResponse(m.getMessageId(), m.getSenderId(), m.getReceiverId(),
                            m.getContent(), m.getCreatedAt(), m.getIsRead()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get sent messages
    @GetMapping("/messages/sent/{userProfileId}")
    public ResponseEntity<List<MessageResponse>> getSentMessages(@PathVariable Integer userProfileId) {
        try {
            List<Message> messages = postService.getSentMessages(userProfileId);
            List<MessageResponse> response = messages.stream()
                    .map(m -> new MessageResponse(m.getMessageId(), m.getSenderId(), m.getReceiverId(),
                            m.getContent(), m.getCreatedAt(), m.getIsRead()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark message as read
    @PutMapping("/messages/{messageId}/read")
    public ResponseEntity<String> markMessageAsRead(@PathVariable Long messageId, @RequestBody MessageRequest request) {
        try {
            postService.markMessageAsRead(messageId, request.getReceiverId());
            return ResponseEntity.ok("Message marked as read!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Create a story
    @PostMapping("/stories")
    public ResponseEntity<String> createStory(@RequestBody StoryRequest request) {
        try {
            Story story = new Story();
            story.setUserProfileId(request.getUserProfileId());
            story.setMediaUrl(request.getMediaUrl());
            story.setMediaType(request.getMediaType());
            story.setIsPrivate(request.getIsPrivate());
            postService.createStory(story);
            return ResponseEntity.ok("Story created successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get story by ID
    @GetMapping("/stories/{id}")
    public ResponseEntity<?> getStoryById(@PathVariable Long id, @RequestParam Integer requestingUserProfileId) {
        try {
            Optional<Story> story = postService.getStoryById(id, requestingUserProfileId);
            if (story.isPresent()) {
                Story s = story.get();
                return ResponseEntity.ok(new StoryResponse(
                        s.getStoryId(), s.getUserProfileId(), s.getMediaUrl(),
                        s.getMediaType(), s.getIsPrivate(), s.getCreatedAt()));
            } else {
                return new ResponseEntity<>("Story not found, expired, or access denied", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    // Get stories by user profile ID
    @GetMapping("/stories/byUserProfileId/{userProfileId}")
    public ResponseEntity<List<StoryResponse>> getStoriesByUserProfileId(@PathVariable Integer userProfileId,
                                                                         @RequestParam Integer requestingUserProfileId) {
        try {
            List<Story> stories = postService.getStoriesByUserProfileId(userProfileId, requestingUserProfileId);
            List<StoryResponse> response = stories.stream()
                    .map(s -> new StoryResponse(
                            s.getStoryId(), s.getUserProfileId(), s.getMediaUrl(),
                            s.getMediaType(), s.getIsPrivate(), s.getCreatedAt()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get stories from followed users
    @GetMapping("/stories/followed")
    public ResponseEntity<List<StoryResponse>> getFollowedUsersStories(@RequestParam Integer requestingUserProfileId) {
        try {
            List<Story> stories = postService.getFollowedUsersStories(requestingUserProfileId);
            List<StoryResponse> response = stories.stream()
                    .map(s -> new StoryResponse(
                            s.getStoryId(), s.getUserProfileId(), s.getMediaUrl(),
                            s.getMediaType(), s.getIsPrivate(), s.getCreatedAt()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a story
    @DeleteMapping("/stories/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id, @RequestParam Integer requestingUserProfileId) {
        try {
            postService.deleteStory(id, requestingUserProfileId);
            return ResponseEntity.ok("Story deleted successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{postId}/isLiked")
    public ResponseEntity<Boolean> isPostLikedByUser(@PathVariable Integer postId, @RequestParam Integer userProfileId) {
        try {
            boolean isLiked = postService.isPostLikedByUser(postId, userProfileId);
            return ResponseEntity.ok(isLiked);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{userId}/username")
    public ResponseEntity<String> getAuthorUsername(@PathVariable Integer userId) {
        try {
            String username = postService.fetchUsernameForPost(userId);
            return ResponseEntity.ok(username);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    // Delete a comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @RequestParam Integer requestingUserProfileId) {
        try {
            postService.deleteComment(commentId, requestingUserProfileId);
            return ResponseEntity.ok("Comment deleted successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a reply to a comment
    @DeleteMapping("/comments/replies/{replyId}")
    public ResponseEntity<String> deleteCommentReply(@PathVariable Long replyId,
                                                     @RequestParam Integer requestingUserProfileId) {
        try {
            postService.deleteCommentReply(replyId, requestingUserProfileId);
            return ResponseEntity.ok("Reply deleted successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // In com.InstaSpehere.Post_Add_Service.controller.PostController
    @GetMapping("/enhanced-feed")
    public ResponseEntity<List<EnhancedPostResponse>> getEnhancedUserFeed(@RequestParam Integer requestingUserProfileId) {
        try {
            List<EnhancedPostResponse> feed = postService.getEnhancedFeed(requestingUserProfileId);
            return ResponseEntity.ok(feed);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get enhanced stories feed (own and followed users' stories)
    @GetMapping("/stories/enhanced-feed")
    public ResponseEntity<List<EnhancedStoryResponse>> getEnhancedStoriesFeed(@RequestParam Integer requestingUserProfileId) {
        try {
            List<EnhancedStoryResponse> stories = postService.getEnhancedStoriesFeed(requestingUserProfileId);
            return ResponseEntity.ok(stories);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public static class ReportRequest {
        private Integer postId;
        private Integer userProfileId;

        public Integer getPostId() { return postId; }
        public void setPostId(Integer postId) { this.postId = postId; }
        public Integer getUserProfileId() { return userProfileId; }
        public void setUserProfileId(Integer userProfileId) { this.userProfileId = userProfileId; }
    }

    // DTO for ban request
    public static class BanRequest {
        private Integer postId;
        private Integer adminUserProfileId;

        public Integer getPostId() { return postId; }
        public void setPostId(Integer postId) { this.postId = postId; }
        public Integer getAdminUserProfileId() { return adminUserProfileId; }
        public void setAdminUserProfileId(Integer adminUserProfileId) { this.adminUserProfileId = adminUserProfileId; }
    }

    // Report a post
    @PostMapping("/report")
    public ResponseEntity<String> reportPost(@RequestBody ReportRequest request) {
        try {
            postService.reportPost(request.getPostId(), request.getUserProfileId());
            return ResponseEntity.ok("Post reported successfully!");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Ban a post (admin only)
    @PostMapping("/ban")
    public ResponseEntity<String> banPost(@RequestBody BanRequest request) {
        try {
            postService.banPost(request.getPostId(), request.getAdminUserProfileId());
            return ResponseEntity.ok("Post banned successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get pending alerts (admin only)
    // Unban a post (admin only)
    @PostMapping("/unban")
    public ResponseEntity<String> unbanPost(@RequestBody BanRequest request) {
        try {
            postService.unbanPost(request.getPostId(), request.getAdminUserProfileId());
            return ResponseEntity.ok("Post unbanned successfully!");
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get pending alerts (admin only)
    @GetMapping("/admin/alerts")
    public ResponseEntity<List<AdminAlert>> getPendingAlerts() {
        try {
            List<AdminAlert> alerts = postService.getPendingAlerts();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get banned posts (admin only)
    @GetMapping("/admin/banned")
    public ResponseEntity<List<PostResponse>> getBannedPosts() {
        try {
            List<PostModel> bannedPosts = postService.getBannedPosts();
            List<PostResponse> response = bannedPosts.stream()
                    .map(p -> new PostResponse(
                            p.getPostId(),
                            p.getUserProfileId(),
                           // Assuming PostModel has getUsername()
                            p.getCaption(),
                            p.getMediaUrl(),
                            p.getMediaType(),
                            p.getPrivate(),
                            p.getLikeCount(),
                            p.getCommentCount(),
                            p.getLikedByUserProfileIds()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
