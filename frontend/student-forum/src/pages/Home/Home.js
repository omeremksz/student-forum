import React from 'react'
import Navbar from '../../components/Navbar';
import Footer from '../../components/Footer';
import { Box, Container, Stack, makeStyles } from '@mui/material';
import Post from '../Post/Post';

const useStyles = makeStyles(() => ({
  root: {
    minHeight: '95vh', 
    display: 'flex', 
    flexDirection: 'column', 
    alignItems: 'center',
  }
}))

const Home = () => {
  const { classes } = useStyles();
  return (
    <>
      <Box className={classes.root}>
        <Navbar/>
        <Container>
          <Stack
            direction="column"
            alignItems="center"
            justifyContent="center" 
            minHeight="100vh"
          >
            <Box sx={{flex:5, p:2}}>
              <Post/>
              <Post/>
            </Box>
          </Stack >
        </Container>
      </Box>
      <Footer/>
    </>
  )
}

export default Home