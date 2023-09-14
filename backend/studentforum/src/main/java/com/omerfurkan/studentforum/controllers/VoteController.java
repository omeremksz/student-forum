package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.requests.VoteCreateRequest;
import com.omerfurkan.studentforum.requests.VoteUpdateRequest;
import com.omerfurkan.studentforum.responses.VoteResponse;
import com.omerfurkan.studentforum.services.VoteService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping
    public List<VoteResponse> getAllVotes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId,
                                          @RequestParam Optional<Long> commentId) {
        return voteService.getAllVotes(userId, postId, commentId);
    }

    @GetMapping("/{voteId}")
    public Vote getVoteById(@PathVariable Long voteId) {
        return voteService.getVoteById(voteId);
    }

    @PostMapping("/post")
    public Vote createNewPostVote(@RequestBody VoteCreateRequest voteCreateRequest) {
        return voteService.createNewPostVote(voteCreateRequest);
    }

    @PostMapping("/comment")
    public Vote createNewCommentVote(@RequestBody VoteCreateRequest voteCreateRequest) {
        return voteService.createNewCommentVote(voteCreateRequest);
    }

    @PutMapping("/{voteId}")
    public Vote updateVoteById(@PathVariable Long voteId, @RequestBody VoteUpdateRequest voteUpdateRequest) {
        return voteService.updateVoteById(voteId, voteUpdateRequest);
    }

    @DeleteMapping("/{voteId}")
    public void deleteVoteById(@PathVariable Long voteId) {
        voteService.deleteVoteById(voteId);
    }

}
