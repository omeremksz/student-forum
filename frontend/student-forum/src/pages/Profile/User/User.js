import React, { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ProfileHeader from '../Global/ProfileHeader';
import customImage from '../../../images/custom-image2.jpg';
import { red } from '@mui/material/colors';
import { GetWithAuth } from '../../../services/HttpService';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import EmailIcon from '@mui/icons-material/Email';
import InstagramIcon from '@mui/icons-material/Instagram';
import TwitterIcon from '@mui/icons-material/Twitter';
import { Avatar, Box, Button, Card, CardActions, CardContent, CardMedia, Container, Stack, Typography, } from '@mui/material';
import CommentActivity from '../../Comment/CommentActivity';
import PostActivity from '../../Post/PostActivity';
import VoteActivity from '../../Vote/VoteActivity';

const User = () => {
  const { userId } = useParams();
  const [userProfile, setUserProfile] = useState(null);
  const [userPostList, setUserPostList] = useState([]);
  const [userVoteList, setUserVoteList] = useState([]);
  const [userCommentList, setUserCommentList] = useState([]);
  const [activeTab, setActiveTab] = useState('Posts'); 

  const getUserVotes = useCallback(() => {
    GetWithAuth('/votes?userId=' + userId)
      .then((res) => res.json())
      .then(
        (result) => {
          console.log(result)
          setUserVoteList(result);
        },
        (err) => {
          console.log(err);
        }
      );
  }, [userId]);

  useEffect(() => {
    getUserVotes();
  }, [getUserVotes]);

  const getUserComments = useCallback(() => {
    GetWithAuth('/comments?userId=' + userId)
      .then((res) => res.json())
      .then(
        (result) => {
          setUserCommentList(result);
        },
        (err) => {
          console.log(err);
        }
      );
  }, [userId]);

  useEffect(() => {
    getUserComments();
  }, [getUserComments]);

  const getUserPosts = useCallback(() => {
    GetWithAuth('/posts?userId=' + userId)
      .then((res) => res.json())
      .then(
        (result) => {
          setUserPostList(result);
        },
        (err) => {
          console.log(err);
        }
      );
  }, [userId]);

  useEffect(() => {
    getUserPosts();
  }, [getUserPosts]);

  const getUserProfile = useCallback(() => {
    GetWithAuth('/profiles/' + userId)
      .then((res) => res.json())
      .then(
        (result) => {
          setUserProfile(result);
        },
        (err) => {
          console.log(err);
        }
      );
  }, [userId]);

  useEffect(() => {
    getUserProfile();
  }, [getUserProfile]);

  return (
    <>
      <Box
        sx={{
          minHeight: '95vh',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <ProfileHeader />
        <Container>
          <Stack
            direction="column"
            alignItems="center"
            justifyContent="center"
            marginTop="48px"
          >
            {userProfile ? (
              <>
                <Card sx={{ width: 750, marginBottom: 2 }}>
                  <CardMedia
                    sx={{ height: 200 }}
                    image={customImage}
                    title="green iguana"
                  />
                  <CardContent
                    sx={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      textAlign: 'center',
                    }}
                  >
                    <Avatar
                      sx={{
                        bgcolor: red[500],
                        width: '64px',
                        height: '64px',
                        fontSize: '32px',
                        marginBottom: '5px',
                      }}
                      aria-label="recipe"
                    >
                      {userProfile.userName.charAt(0).toUpperCase()}
                    </Avatar>
                    <Typography variant="h5" component="div">
                      {userProfile.firstName} {userProfile.lastName}
                    </Typography>
                    <Typography
                      sx={{ mb: '10px', fontSize: 15 }}
                      color="text.secondary"
                    >
                      @{userProfile.userName}
                    </Typography>
                    <Typography
                      sx={{ mb: '10px' }}
                      variant="body2"
                      color="text.secondary"
                    >
                      {userProfile.about}
                    </Typography>
                    <CardActions>
                      <Button size="small">
                        <InstagramIcon />
                      </Button>
                      <Button size="small">
                        <TwitterIcon />
                      </Button>
                      <Button size="small">
                        <LinkedInIcon />
                      </Button>
                      <Button size="small">
                        <EmailIcon />
                      </Button>
                    </CardActions>
                  </CardContent>
                </Card>
                <Card sx={{ width: 750 }}>
                  <CardContent
                    sx={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      textAlign: 'center',
                    }}
                  >
                    <Typography
                      variant="h5"
                      component="div"
                      sx={{ mb: '10px' }}
                    >
                      Activity
                    </Typography>
                    <CardActions>
                      {['Posts', 'Comments', 'Reactions'].map((tabName) => (
                        <Button
                          key={tabName}
                          size="small"
                          onClick={() => setActiveTab(tabName)}
                          variant={
                            activeTab === tabName ? 'contained' : 'outlined'
                          }
                          sx={{ marginRight: '8px' }}
                        >
                          {tabName}
                        </Button>
                      ))}
                    </CardActions>
                  </CardContent>
                    <Container>
                      {userPostList && userCommentList && userVoteList ? (
                        activeTab === 'Posts' ? (
                          <Box sx={{
                            display: 'flex', 
                            flexDirection: 'column', 
                            alignItems: 'center',
                            }}>
                            {userPostList.map(post => (
                              <PostActivity 
                                firstName = {userProfile.firstName}
                                lastName = {userProfile.lastName} 
                                contentText = {post.contentText} 
                                creationDate = {post.creationDate} 
                              />
                            ))}
                          </Box>
                        ) : activeTab === 'Comments' ? (
                          <Box sx={{
                            display: 'flex', 
                            flexDirection: 'column', 
                            alignItems: 'center',
                            }}>
                            {userCommentList.map(comment => (
                              <CommentActivity
                                firstName = {userProfile.firstName}
                                lastName = {userProfile.lastName}
                                contentText={comment.contentText} 
                                creationDate={comment.creationDate} 
                              />
                            ))}
                          </Box>
                        ) : (
                          <Box sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                          }}>
                            {userVoteList.map((vote) => (
                              <>
                                {vote.post ? (
                                  <VoteActivity
                                  firstName = {userProfile.firstName}
                                  lastName = {userProfile.lastName}
                                  votedObject = {"Post"}
                                  contentText = {vote.post.contentText} 
                                  isUpvote = {vote.isUpvote}
                                  creationDate={vote.creationDate} 
                                  />
                                ) : (
                                  <VoteActivity
                                  firstName = {userProfile.firstName}
                                  lastName = {userProfile.lastName}
                                  votedObject = {"Comment"}
                                  contentText={vote.comment.contentText} 
                                  isUpvote = {vote.isUpvote}
                                  creationDate={vote.creationDate} 
                                  />
                                )}
                              </>
                            ))}
                          </Box>
                        )
                      ) : (
                        <Typography>Loading posts list...</Typography>
                      )}
                    </Container>
                </Card>
              </>
            ) : (
              <Typography variant="h6">Loading user data...</Typography>
            )}
          </Stack>
        </Container>
      </Box>
    </>
  );
};

export default User;
