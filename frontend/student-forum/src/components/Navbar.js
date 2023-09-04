import { React } from 'react'
import { AppBar, Box, Button, Container, IconButton, Toolbar, Typography, } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import Colors from '../styles/Colors';

const Navbar = () => {

  return (
    <AppBar position='static' >
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box sx={{
            display: 'flex', 
            alignItems: 'center', 
            justifyContent: 'space-between', 
            flexGrow: 1,
          }}>
            <Box sx={{
              display: 'flex',
              alignItems: 'center',
            }}>
            <IconButton edge="start" color="inherit" aria-label="menu">
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" sx={{
              fontSize: '16px',
              fontWeight: 'bold',
              color: Colors.text.main,
              textTransform: 'uppercase',
              letterSpacing: '0.05rem',
            }}>
              Student Forum
            </Typography>
            </Box>
            <Box>
                <Button variant="contained" sx={{
                  color: Colors.text.lighterBlue, 
                  backgroundColor: Colors.button.lighterGray, 
                  marginRight: '0.5rem',
                }}>
                  Log in
                </Button>
                <Button variant="contained" sx={{
                  color: Colors.text.white, 
                  backgroundColor: Colors.button.darkerBlue, 
                  marginLeft: '0.5rem',
                }}>
                  Sign up
                </Button>
            </Box>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  )
}

export default Navbar;