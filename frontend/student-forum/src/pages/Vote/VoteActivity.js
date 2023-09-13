import { Box, Card, CardContent, Typography } from '@mui/material';
import React from 'react'

const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', options);
  };

const VoteActivity = (props) => {
    const { firstName, lastName, votedObject, contentText, isUpvote, creationDate } = props;

    const formattedCreationDate = formatDate(creationDate);
  return (
    <Card variant="outlined" sx={{ marginBottom: 2, width: '100%', }}>
        <CardContent sx={{ display: 'flex', flexDirection: 'row', alignItems: 'flex-start' }}>
            <Box>
                {votedObject === 'Post' ? (
                    <Box>
                        {isUpvote === true ? (
                            <>
                                <Typography sx={{ mb: '10px', fontSize: 15 }} color="text.secondary" >
                                    {firstName} {lastName} upvoted this post | {formattedCreationDate}
                                </Typography>
                                <Typography sx={{ mb: '10px' }} variant="body2" color="black">
                                    {contentText}
                                </Typography>
                            </>
                        ) : isUpvote === false ? (
                            <>
                                <Typography sx={{ mb: '10px', fontSize: 15 }} color="text.secondary" >
                                    {firstName} {lastName} downvoted this post | {formattedCreationDate}
                                </Typography>
                                <Typography sx={{ mb: '10px' }} variant="body2" color="black">
                                    {contentText}
                                </Typography>
                            </>
                        ) : (
                            <Typography variant="body2">Unsupported isUpvote value</Typography>
                        )}
                    </Box>
                ) : votedObject === 'Comment' ? (
                    <Box>
                        {isUpvote === true ? (
                            <>
                                <Typography sx={{ mb: '10px', fontSize: 15 }} color="text.secondary" >
                                    {firstName} {lastName} upvoted this comment | {formattedCreationDate}
                                </Typography>
                                <Typography sx={{ mb: '10px' }} variant="body2" color="black">
                                    {contentText}
                                </Typography>
                            </>
                        ) : isUpvote === false ? (
                            <>
                                <Typography sx={{ mb: '10px', fontSize: 15 }} color="text.secondary" >
                                    {firstName} {lastName} downvoted this comment | {formattedCreationDate}
                                </Typography>
                                <Typography sx={{ mb: '10px' }} variant="body2" color="black">
                                    {contentText}
                                </Typography>
                            </>
                        ) : (
                            <Typography variant="body2">Unsupported isUpvote value</Typography>
                        )}
                    </Box>
                ) : (
                <Typography variant="body2">Unsupported votedObject type</Typography>
                )}
            </Box>
        </CardContent>
    </Card>
  )
}

export default VoteActivity