import React from 'react'
import { Box } from '@mui/material';
import colors from '../styles/Colors';

const Footer = () => {

  return (
    <Box sx={{
      height: '60px',
      bgcolor: colors.footer.main,
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    }}>
        <Box component='span' sx={{ color: colors.text.white }}> © 2023 All Rights Reserved </Box>

    </Box>
  )
}

export default Footer