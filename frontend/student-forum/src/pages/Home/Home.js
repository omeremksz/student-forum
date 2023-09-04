import React from 'react'
import Navbar from '../../components/Navbar';
import Footer from '../../components/Footer';
import { Box, Container, Stack, } from '@mui/material';
import Post from '../Post/Post';

const Home = () => {
  return (
    <>
      <Box sx={{
        minHeight: '95vh', 
        display: 'flex', 
        flexDirection: 'column', 
        alignItems: 'center',
      }}>
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