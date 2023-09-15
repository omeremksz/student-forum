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
import Footer from '../../../components/Footer';

const User = () => {
  const { userId } = useParams();
  const [userProfile, setUserProfile] = useState(null);
  const [userPostList, setUserPostList] = useState([]);
  const [userVoteList, setUserVoteList] = useState([]);
  const [userCommentList, setUserCommentList] = useState([]);
  const [activeTab, setActiveTab] = useState('Posts'); 

  const fetchData = useCallback(async () => {
    try {
      const userProfileResponse = await GetWithAuth(`/profiles/${userId}`);
      const userProfileData = await userProfileResponse.json();
      setUserProfile(userProfileData);

      const userVoteResponse = await GetWithAuth(`/votes?userId=${userId}`);
      const userVoteData = await userVoteResponse.json();
      setUserVoteList(userVoteData);

      const userCommentResponse = await GetWithAuth(`/user-interactions/comment/${userId}`);
      const userCommentData = await userCommentResponse.json();
      setUserCommentList(userCommentData);

      const userPostResponse = await GetWithAuth(`/user-interactions/post/${userId}`);
      const userPostData = await userPostResponse.json();
      setUserPostList(userPostData);
    } catch (error) {
      console.error(error);
    }
  }, [userId]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

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
            marginBottom="48px"
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
                <Card sx={{ width: 750, height: 450 }}>
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
                      {['Posts', 'Comments',].map((tabName) => (
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
                        <Box
                          sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                          }}
                        >
                          {userPostList.slice(0, 2).map((post) => (
                            <PostActivity
                              key={post.id}
                              firstName={userProfile.firstName}
                              lastName={userProfile.lastName}
                              contentText={post.contentText}
                              creationDate={post.creationDate}
                            />
                          ))}
                        </Box>
                      ) : activeTab === 'Comments' ? (
                        <Box
                          sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                          }}
                        >
                          {userCommentList.slice(0, 2).map((comment) => (
                            <CommentActivity
                              key={comment.id}
                              firstName={userProfile.firstName}
                              lastName={userProfile.lastName}
                              contentText={comment.contentText}
                              creationDate={comment.creationDate}
                            />
                          ))}
                        </Box>
                      ) : (
                        <Typography>Loading posts and comments...</Typography>
                      )
                    ) : (
                      <Typography>Loading posts and comment list...</Typography>
                    )}
                     <Box sx={{ display: 'flex', justifyContent: 'center',  }}>
                      <Button size="small" variant="contained">
                        Show all posts
                      </Button>
                     </Box>
                  </Container>
                </Card>
              </>
            ) : (
              <Typography variant="h6">Loading user data...</Typography>
            )}
          </Stack>
        </Container>
      </Box>
      <Footer/>
    </>
  );
};

export default User;