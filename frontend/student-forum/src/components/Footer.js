import React from 'react'
import { Box } from '@mui/material';
import Colors from '../styles/Colors';


const Footer = () => {

  return (
    <Box 
        sx={{
        height: '60px',
        bgcolor: Colors.footer.main,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
    }}
    >
        <Box component='span' sx={{ color: Colors.text.white }}> © 2023 All Rights Reserved </Box>

    </Box>
  )
}

export default Footer