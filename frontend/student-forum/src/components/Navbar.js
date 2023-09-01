import React from 'react'
import { AppBar, Box, Button, Container, IconButton, Toolbar, Typography, makeStyles } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import Colors from '../styles/Colors';

const useStyles = makeStyles(() => ({
  root: {
    display: 'flex', 
    alignItems: 'center', 
    justifyContent: 'space-between', 
    flexGrow: 1,
  },
  menu: {
    display: 'flex',
    alignItems: 'center',
  },
  title: {
    fontSize: '16px',
    fontWeight: 'bold',
    color: Colors.text.main,
    textTransform: 'uppercase',
    letterSpacing: '0.05rem',
  },
  login: {
    color: Colors.text.lighterBlue, 
    backgroundColor: Colors.button.lighterGray, 
    marginRight: '0.5rem',
  },
  signup: {
    color: Colors.text.white, 
    backgroundColor: Colors.button.darkerBlue, 
    marginLeft: '0.5rem',
  }

}))

const Navbar = () => {
  const { classes } = useStyles();

  return (
    <AppBar position='static' >
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box className={classes.root}>
            <Box className={classes.menu}>
            <IconButton edge="start" color="inherit" aria-label="menu">
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" component="div" className={classes.title}>
              Student Forum
            </Typography>
            </Box>
            <Box>
                <Button variant="contained" className={classes.login}>Log in</Button>
                <Button variant="contained" className={classes.signup}>Sign up</Button>
            </Box>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  )
}

export default Navbar;