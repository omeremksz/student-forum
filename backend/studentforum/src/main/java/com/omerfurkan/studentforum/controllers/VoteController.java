package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.Vote;
import com.omerfurkan.studentforum.requests.VoteCreateRequest;
import com.omerfurkan.studentforum.requests.VoteUpdateRequest;
import com.omerfurkan.studentforum.services.CommentService;
import com.omerfurkan.studentforum.services.UserService;
import com.omerfurkan.studentforum.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
@AllArgsConstructor
public class VoteController {

    private VoteService voteService;

    @GetMapping
    public List<Vote> getAllVotes(){
        return voteService.getAllVotes();
    }

    @GetMapping("/{voteId}")
    public Vote getVoteById(Long voteId){
        return voteService.getVoteById(voteId);
    }

    @PutMapping("/{voteId}")
    public Vote updateVoteById(@PathVariable Long voteId, @RequestBody VoteUpdateRequest voteUpdateRequest) {
        return voteService.updateVoteById(voteId, voteUpdateRequest);
    }

    @PostMapping
    public Vote createNewVote(@RequestBody VoteCreateRequest voteCreateRequest){
        return voteService.createNewVote(voteCreateRequest);
    }

    @DeleteMapping("/{voteId}")
    public void deleteVoteById(@PathVariable Long voteId) {
        voteService.deleteVoteById(voteId);
    }






}
