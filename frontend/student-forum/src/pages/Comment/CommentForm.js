import React, { useState } from 'react';
import { Avatar, Box, Button, Card, CardActions, CardContent, TextField, Typography } from '@mui/material';
import { grey, red } from '@mui/material/colors';
import { PostWithAuth } from '../../services/HttpService';

const CommentForm = (props) => {
    const { postId, setCommentRefresh } = props;

    const [contentText, setContentText] = useState("");

    const userName = localStorage.getItem("userName");
    const userId = localStorage.getItem("userId")
    const avatarLetter = userName.charAt(0).toUpperCase();

    const handleContentText = (value) => {
        setContentText(value);
    };

    const handleCommentButton = () => {
        createComment();
        setContentText("");
        setCommentRefresh();
    };

    const createComment = () => {
        PostWithAuth("/comments", {
            userId: userId,
            postId: postId,
            contentText: contentText,
        })
        .then((res) => res.json())
        .catch((err) => console.log(err))
    };

    return (
        <Box style={{ display: 'flex', alignItems: 'flex-start', marginBottom: 2 }}>
            <Avatar aria-label="recipe" sx={{ bgcolor: red[500], width: 36, height: 36, fontSize: '16px', marginRight: 2, marginTop: 1 }}>
                {avatarLetter}
            </Avatar>
            <Card variant="outlined" sx={{ marginBottom: 2, width: '100%', backgroundColor: grey[200] }}>
                <CardContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                    <Typography sx={{ fontWeight: 'bold', marginBottom: 1 }}>
                        {userName}
                    </Typography>
                    <TextField
                        id="comment-text"
                        label="Write a comment..."
                        multiline
                        rows={3}
                        fullWidth
                        variant="outlined"
                        value={contentText}
                        onChange={ (i) => handleContentText(i.target.value) }
                    />
                </CardContent>
                <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                    <CardActions>
                        <Button variant="contained" size="small" onClick={handleCommentButton} >
                            Comment
                        </Button>
                    </CardActions>
                </Box>
            </Card>
        </Box>
    );
};

export default CommentForm;
