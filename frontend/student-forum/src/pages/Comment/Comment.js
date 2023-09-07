import { Avatar, Box, Card, CardContent, IconButton, Typography } from '@mui/material'
import { grey, red } from '@mui/material/colors';
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import MoreVertIcon from '@mui/icons-material/MoreVert';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import colors from '../../styles/colors';
import { PostWithoutAuth } from '../../services/HttpService';

const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', options);
  };

const Comment = (props) => {
    const { commentId, userId, userName, contentText, creationDate, commentVotes} = props;

    const formattedCreationDate = formatDate(creationDate);
    const avatarLetter = userName.charAt(0).toUpperCase();

    const [upVoteCount, setUpVoteCount] = useState(0);
    const [downVoteCount, setDownVoteCount] = useState(0);

    const createVote = (isUpVote) => {
        PostWithoutAuth("/votes/comment", {
            userId: 2,
            commentId: commentId,
            isUpVote: isUpVote,
        })
            .then((res) => res.json())
            .then((data) => {
                if (isUpVote) {
                setUpVoteCount(upVoteCount + 1);
                } else {
                setDownVoteCount(downVoteCount + 1);
                }
            })
            .catch((err) => console.log(err));
        };

    useEffect(() => {
        const upVotes = commentVotes.filter((vote) => vote.upVote);
        setUpVoteCount(upVotes.length);
        setDownVoteCount(commentVotes.length - upVotes.length);
    }, [commentVotes]);

    const handleUpVoteClick = () => {
        createVote(true);
    };

    const handleDownVoteClick = () => {
        createVote(false);
    };

  return (
    <Box style={{ display: 'flex', alignItems: 'flex-start', marginBottom: 2 }}>
        <Avatar aria-label="recipe" sx={{ bgcolor: red[500], width: 36, height: 36, fontSize: '16px', marginRight: 2, marginTop: 1 }}>
            <Link style={{ textDecoration: 'none', color: 'white' }} to={{ pathname: '/user/' + userId }}>
                {avatarLetter}
            </Link>
        </Avatar>
        <Card variant="outlined" sx={{ marginBottom: 2, width: '100%', backgroundColor: grey[200] }}>
            <CardContent sx={{ display: 'flex', flexDirection: 'row', alignItems: 'flex-start' }}>
                <Box style={{ flex: 1 }}>
                    <Link 
                        style={{ textDecoration: 'none', color: 'inherit' }}
                        to={{ pathname: '/user/' + userId }}
                        onMouseEnter={(e) => {
                            e.currentTarget.style.textDecoration = 'underline';
                            e.currentTarget.style.borderColor = colors.link.main;
                            e.currentTarget.style.color = colors.link.main;
                        }}
                        onMouseLeave={(e) => {
                            e.currentTarget.style.textDecoration = 'none';
                            e.currentTarget.style.borderColor = 'transparent';
                            e.currentTarget.style.color = 'inherit'; 
                        }}
                    >
                        <Typography sx={{ fontWeight: 'bold' }}>
                            {userName}
                        </Typography>
                    </Link>
                    <Typography variant="body1" sx={{ fontSize: '14px' }}>
                        {contentText}
                    </Typography>
                </Box>
            </CardContent>
            <Box style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: 2 }}>
                <Box style={{ display: 'flex', alignItems: 'center' }}>
                    <IconButton aria-label="upvote" size="small" color="primary">
                        <KeyboardArrowUpIcon fontSize="small" onClick={handleUpVoteClick}/>
                    </IconButton>
                    <Typography sx={{ fontSize: '14px' }}>
                        {upVoteCount - downVoteCount}
                    </Typography>
                    <IconButton aria-label="downvote">
                        <KeyboardArrowDownIcon fontSize="small" onClick={handleDownVoteClick}/>
                    </IconButton>
                </Box>
                <Box style={{ display: 'flex', alignItems: 'center' }}>
                    <Typography variant="body2" color="textSecondary">
                        {formattedCreationDate}
                    </Typography>
                    <IconButton size="small">
                        <MoreVertIcon fontSize="small" />
                    </IconButton>
                </Box>
            </Box>
        </Card>
    </Box>
  )
}

export default Comment