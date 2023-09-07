import React, { useState } from 'react'
import { Box, Button, Card, CardActions, CardContent, Input, OutlinedInput, Typography } from '@mui/material'
import { PostWithoutAuth } from '../../services/HttpService';
import { useParams } from 'react-router-dom';

const PostForm = () => {
    const { userId } = useParams();
    const [contentText, setContentText] = useState("");
    const [contentImageURL, setContentImageURL] = useState(null);

    const handleContentText = (value) => {
        setContentText(value);
    }

    const handleContentImage = (event) => {
        const selectedImage = event.target.files[0];
        if (selectedImage) {
          const imageUrl = URL.createObjectURL(selectedImage);
          setContentImageURL(imageUrl);
        }
    };

    const handlePostButton = () => {
        createPost();
        setContentText("");
        setContentImageURL(null);
    }

    const createPost = () => {

        PostWithoutAuth("/posts", {
            userId: userId,
            postPreferencesId: 1,
            contentText: contentText,
            contentPictureURL: contentImageURL,
        })
        .then((res) => res.json())
        .catch((err) => console.log(err))
    }

  return (
    <Box sx={{ width:"50%" }}>
        <Card sx={{ minWidth: "275px", mb: "16px", mt: "16px", }}>
            <CardContent>
                <Typography sx={{ fontSize: "15px", color: "text.secondary" }}>
                    {<OutlinedInput 
                                    multiline
                                    placeholder='What do you want to talk about ?'
                                    inputProps={{maxLength:500}}
                                    fullWidth
                                    minRows={4}
                                    value={contentText}
                                    onChange={ (i) => handleContentText(i.target.value) }
                                    sx={{
                                        width: '100%',
                                    }}
                    />}
                    <label htmlFor="image-upload" style={{ marginTop: '16px' }}>
                        <Input
                            id="image-upload"
                            type="file"
                            accept="image/*"
                            style={{ display: 'none' }}
                            onChange={ handleContentImage }
                        />
                        <Button
                        variant="contained"
                        component="span"
                        size="small"
                        sx={{
                            marginTop: '16px',
                            display: 'block', 
                            marginLeft: 'auto',
                            marginRight: 'auto',
                        }}
                        >
                            {contentImageURL ? 'Image Selected' : 'Upload Image'}
                        </Button>
                    </label>
                </Typography>
            </CardContent>
            <Box display="flex" justifyContent="flex-end" alignItems="flex-end">
                <CardActions>
                    <Button variant="contained" size="small" onClick={handlePostButton}>
                        Post
                    </Button>
                </CardActions>
            </Box>
        </Card>
    </Box>
  )
}

export default PostForm