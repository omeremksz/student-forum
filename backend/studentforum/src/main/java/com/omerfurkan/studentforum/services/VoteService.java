package com.omerfurkan.studentforum.services;


import com.omerfurkan.studentforum.entities.Post;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.repositories.VoteRepository;
import com.omerfurkan.studentforum.requests.VoteCreateRequest;
import com.omerfurkan.studentforum.requests.VoteUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VoteService {

    private VoteRepository voteRepository;
    private UserService userService;
    private PostService postService;

    public List<Vote> getAllVotes(){
        return voteRepository.findAll();
    }
    public Vote getVoteById(Long voteId){
        return voteRepository.findById(voteId).orElse(null);
    }

    public Vote createNewVote(VoteCreateRequest voteCreateRequest){
        User user = userService.getUserById(voteCreateRequest.getUserId());
        Post post = postService.getPostById(voteCreateRequest.getPostId());

        if (user == null || post == null){
            return null;
        } else {
            Vote voteToSave = new Vote();

            voteToSave.setUser(user);
            voteToSave.setPost(post);
            voteToSave.setUpvote(voteCreateRequest.getIsUpVote());
            voteToSave.setCreationDate(LocalDateTime.now());
            voteToSave.setUpdateDate(LocalDateTime.now());
            return voteRepository.save(voteToSave);
        }

    }
    public Vote updateVoteById(Long voteId, VoteUpdateRequest voteUpdateRequest){
        Optional<Vote> vote = voteRepository.findById(voteId);

        if (vote.isPresent()){
            Vote voteToUpdate = vote.get();

            voteToUpdate.setUpvote(voteUpdateRequest.getIsUpVote());
            voteToUpdate.setUpdateDate(LocalDateTime.now());

            return voteRepository.save(voteToUpdate);
        } else {
            return null;
        }
    }

    public void deleteVoteById(Long voteId){
        voteRepository.deleteById(voteId);
    }


}
