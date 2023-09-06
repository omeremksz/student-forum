import React, { useEffect, useState } from 'react'
import Navbar from '../../components/Navbar';
import Footer from '../../components/Footer';
import { Box, Container, Stack, } from '@mui/material';
import Post from '../Post/Post';
import { GetWithoutAuth } from '../../services/HttpService';

const Home = () => {
  const [postList, setPostList] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [error, setError] = useState(null);

  const refreshPosts = () => {

    GetWithoutAuth("/posts")
    .then(res => res.json())
    .then(
        (result) => {
          setIsLoaded(true);
          setPostList(result);
        },
        (error) => {
          setIsLoaded(true);
          setError(error);
        }
    ) 
  }

  useEffect(() => {
    refreshPosts();
  },[])


  if (error) {
    return <Box> ERROR! </Box>
  } else if (!isLoaded) {
    return <Box> LOADING... </Box>
  } else {
    return (
      <>
        <Box sx={{
          minHeight: '95vh', 
          display: 'flex', 
          flexDirection: 'column', 
          alignItems: 'center',
        }}>
          <Navbar/>
          <Container >
            <Stack
              direction="column"
              alignItems="center"
              justifyContent="center" 
              minHeight="100vh"
            >
              <Box sx={{flex:5, p:2}}>
                {postList.map(post => (
                  <Post postId = {post.id} userId = {post.userId} userName = {post.userName} contentText = {post.contentText} creationDate = {post.creationDate} />
                ))}
              </Box>
            </Stack >
          </Container>
        </Box>
        <Footer/>
      </>
    )
  }
}

export default Home