package com.omerfurkan.studentforum.services;


import com.omerfurkan.studentforum.entities.Comment;
import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.repositories.VoteRepository;
import com.omerfurkan.studentforum.requests.UserInteractionCreateRequest;
import com.omerfurkan.studentforum.requests.VoteCreateRequest;
import com.omerfurkan.studentforum.requests.VoteUpdateRequest;
import com.omerfurkan.studentforum.responses.VoteResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;
    private UserInteractionService userInteractionService;

    public VoteService(VoteRepository voteRepository, UserService userService, @Lazy PostService postService, @Lazy CommentService commentService,
                       UserInteractionService userInteractionService) {
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.userInteractionService = userInteractionService;
    }

    public List<VoteResponse> getAllVotes(Optional<Long> userId, Optional<Long> postId, Optional<Long> commentId) {
        List<Vote> voteList;
        if (userId.isPresent() && postId.isPresent() && commentId.isPresent()) {
            voteList = voteRepository.findByUserIdAndPostIdAndCommentId(userId.get(), postId.get(), commentId.get());
        } else if (userId.isPresent() && postId.isPresent()) {
            voteList = voteRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            voteList = voteRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            voteList = voteRepository.findByPostId(postId.get());
        } else if (commentId.isPresent()) {
            voteList = voteRepository.findByCommentId(commentId.get());
        } else {
            voteList = voteRepository.findAll();
        }
        return voteList.stream().map(v -> new VoteResponse(v)).collect(Collectors.toList());
    }

    public Vote getVoteById(Long voteId) {
        return voteRepository.findById(voteId).orElse(null);
    }

    public Vote createNewPostVote(VoteCreateRequest voteCreateRequest) {
        User user = userService.getUserById(voteCreateRequest.getUserId());
        Post post = postService.getPostById(voteCreateRequest.getPostId());

        if (user == null || post == null) {
            return null;
        } else {
            Vote voteToSave = new Vote();

            voteToSave.setUser(user);
            voteToSave.setPost(post);
            voteToSave.setIsUpvote(voteCreateRequest.getIsUpVote());
            voteToSave.setCreationDate(LocalDateTime.now());
            voteToSave.setUpdateDate(LocalDateTime.now());

            voteRepository.save(voteToSave);

            UserInteractionCreateRequest userInteractionCreateRequest = getUserVoteInteractionCreateRequest(user, voteToSave, "post");

            userInteractionService.createNewUserVoteInteraction(userInteractionCreateRequest);

            return voteToSave;
        }
    }

    public Vote createNewCommentVote(VoteCreateRequest voteCreateRequest) {
        User user = userService.getUserById(voteCreateRequest.getUserId());
        Comment comment = commentService.getCommentById(voteCreateRequest.getCommentId());

        if (user == null || comment == null) {
            return null;
        } else {
            Vote voteToSave = new Vote();

            voteToSave.setUser(user);
            voteToSave.setComment(comment);
            voteToSave.setIsUpvote(voteCreateRequest.getIsUpVote());
            voteToSave.setCreationDate(LocalDateTime.now());
            voteToSave.setUpdateDate(LocalDateTime.now());

            voteRepository.save(voteToSave);

            UserInteractionCreateRequest userInteractionCreateRequest = getUserVoteInteractionCreateRequest(user, voteToSave, "comment");

            userInteractionService.createNewUserVoteInteraction(userInteractionCreateRequest);

            return voteToSave;
        }
    }

    private static UserInteractionCreateRequest getUserVoteInteractionCreateRequest(User user, Vote voteToSave, String votedObject) {
        UserInteractionCreateRequest userInteractionCreateRequest = new UserInteractionCreateRequest();

        userInteractionCreateRequest.setUserId(user.getId());
        userInteractionCreateRequest.setReferenceId(voteToSave.getId());
        if (votedObject.equals("post")) {
            userInteractionCreateRequest.setReferenceType("post");
        } else {
            userInteractionCreateRequest.setReferenceType("comment");
        }

        return userInteractionCreateRequest;
    }


    public Vote updateVoteById(Long voteId, VoteUpdateRequest voteUpdateRequest) {

        Optional<Vote> vote = voteRepository.findById(voteId);

        if (vote.isPresent()) {
            Vote voteToUpdate = vote.get();

            voteToUpdate.setIsUpvote(voteUpdateRequest.getIsUpVote());
            voteToUpdate.setUpdateDate(LocalDateTime.now());

            return voteRepository.save(voteToUpdate);
        } else {
            return null;
        }
    }

    public void deleteVoteById(Long voteId) {
        voteRepository.deleteById(voteId);
    }
}
