import { Typography } from '@mui/material';
import React from 'react'
import { useParams } from 'react-router-dom'

const User = () => {
    const { userId } = useParams();
  return (
    <>
      <Typography> User {userId} </Typography>
    </>
  )
}

export default User