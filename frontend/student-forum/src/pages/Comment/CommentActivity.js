import { Box, Card, CardContent, Typography } from '@mui/material';
import React from 'react'

const formatDate = (dateString) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', options);
  };

const CommentActivity = (props) => {
    const { firstName, lastName, contentText, creationDate} = props;

    const formattedCreationDate = formatDate(creationDate);

  return (
    <Card variant="outlined" sx={{ marginBottom: 2, width: '100%', }}>
        <CardContent sx={{ display: 'flex', flexDirection: 'row', alignItems: 'flex-start' }}>
            <Box style={{ flex: 1 }}>
            <Typography  sx={{ mb: '10px', fontSize: 15 }}  color="text.secondary">
                {firstName} {lastName} commented on a post | {formattedCreationDate}
            </Typography>
            <Typography  sx={{ mb: '10px' }} variant="body2" color="black">
                {contentText}
            </Typography>
            </Box>
        </CardContent>
    </Card>
  )
}

export default CommentActivity