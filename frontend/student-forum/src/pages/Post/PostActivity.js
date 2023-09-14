import { Box, Card, CardContent, Typography } from '@mui/material';
import React from 'react';

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', options);
};

const PostActivity = (props) => {
  const { firstName, lastName, contentText, creationDate } = props;

  const formattedCreationDate = formatDate(creationDate);

  const maxCharacters = 200;

  let truncatedContent = contentText;
  let lastSpacePosition = -1;

  if (contentText.length > maxCharacters) {
    for (let i = maxCharacters - 1; i >= 0; i--) {
      if (contentText.charAt(i) === ' ') {
        lastSpacePosition = i;
        break;
      }
    }

    if (lastSpacePosition !== -1) {
      truncatedContent = contentText.substring(0, lastSpacePosition) + ' ...';
    } else {
      truncatedContent = contentText.substring(0, maxCharacters) + ' ...';
    }
  }

  return (
    <Card variant="outlined" sx={{ marginBottom: 2, width: '100%' }}>
      <CardContent
        sx={{
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'flex-start',
        }}
      >
        <Box style={{ flex: 1 }}>
          <Typography sx={{ mb: '10px', fontSize: 15 }} color="text.secondary">
            {firstName} {lastName} posted this | {formattedCreationDate}
          </Typography>
          <Typography
            sx={{
              mb: '10px',
              fontSize: 14,
            }}
            variant="body2"
            color="black"
          >
            {truncatedContent}
          </Typography>
        </Box>
      </CardContent>
    </Card>
  );
};

export default PostActivity;
