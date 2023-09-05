import { Typography } from '@mui/material';
import React from 'react'
import { useParams } from 'react-router-dom'
import PostForm from '../Post/PostForm';

const User = () => {
    const { userId } = useParams();
  return (
    <>
      <PostForm/>
      <Typography> User {userId} </Typography>
    </>
  )
}

export default User