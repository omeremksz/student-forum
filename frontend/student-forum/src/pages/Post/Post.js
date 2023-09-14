import React, { useCallback, useEffect, useRef, useState } from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton, { IconButtonProps } from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import RepeatIcon from '@mui/icons-material/Repeat';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import CommentIcon from '@mui/icons-material/Comment';
import customImage from "../../images/custom-image.jpg"
import { Link, useNavigate } from 'react-router-dom';
import colors from '../../styles/Colors';
import { GetWithoutAuth, PostWithAuth } from '../../services/HttpService';
import { Container, DialogContentText } from '@mui/material';
import Comment from '../Comment/Comment';
import CommentForm from '../Comment/CommentForm';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean;
  }
  
const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', options);
};

const isUserLoggedIn = () => {
  const userId = localStorage.getItem("userId");
  const userName = localStorage.getItem("userName");
  const tokenKey = localStorage.getItem("tokenKey");
  return userId && userName && tokenKey;
};

const Post = (props) => {
    const { postId, userId, userName, contentText, creationDate, postVotes } = props;

    const [expanded, setExpanded] = useState(false);
    const [commentList, setCommentList] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const isInitialMount = useRef(true);
    const [refresh, setRefresh] = useState(false);
    const navigate = useNavigate();

    const formattedCreationDate = formatDate(creationDate);
    const avatarLetter = userName.charAt(0).toUpperCase();
    
    const [upVoteCount, setUpVoteCount] = useState(0);
    const [downVoteCount, setDownVoteCount] = useState(0);
    const [commentCount, setCommentCount] = useState(0);

    const [loginDialogOpen, setLoginDialogOpen] = useState(false);

    const handleUpVoteClick = () => {
      if (!isUserLoggedIn()) {
        setLoginDialogOpen(true);
      } else {
        createVote(true);
      }
    };

    const handleDownVoteClick = () => {
      if (!isUserLoggedIn()) {
        setLoginDialogOpen(true);
      } else {
        createVote(false);
      }
    };

    const createVote = (isUpVote) => {
      PostWithAuth("/votes/post", {
          userId: localStorage.getItem("userId"),
          postId: postId,
          isUpVote: isUpVote,
      })
          .then((res) => res.json())
          .then((data) => {
            if (isUpVote) {
              setUpVoteCount(upVoteCount + 1);
            } else {
              setDownVoteCount(downVoteCount + 1);
            }
          })
          .catch((err) => console.log(err));
    };

    useEffect(() => {
      const upVotes = postVotes.filter((vote) => vote.isUpvote);
      setUpVoteCount(upVotes.length);
      setDownVoteCount(postVotes.length - upVotes.length);
    }, [postVotes]);

    const setCommentRefresh = () => {
      setRefresh(true);
    }

    const handleExpandClick = () => {
      if (!isUserLoggedIn()) {
        setLoginDialogOpen(true);
      } else {
        setExpanded(!expanded);
        refreshComments();
      }
    };

    const refreshComments = useCallback(() => {
      GetWithoutAuth("/comments?postId="+postId)
        .then(res => res.json())
        .then(
          (result) => {
            setCommentList(result);
            setIsLoaded(true);
          },
          (error) => {
            setIsLoaded(true);
            setError(error);
          }
        );
      setRefresh(false);
    }, [postId]);

    useEffect(() => {
      if (isInitialMount.current) {
        isInitialMount.current = false;
      } else {
        refreshComments();
        setCommentCount(commentList.length);
      }
    }, [refresh, refreshComments, commentList])

    return (
      <Card sx={{ width: 500, mb: 3, mt: 3 }}>
        <CardHeader
          avatar={
            <Avatar sx={{bgcolor: red[500]}} aria-label="recipe">
              <Link 
                style={{textDecoration: "none", color:"white"}} 
                to={{ pathname: '/user/' + userId }} 
                onClick={(e) => {
                  if (!isUserLoggedIn()) {
                    e.preventDefault();
                    setLoginDialogOpen(true);
                  }
                }}
              >
                {avatarLetter}
              </Link>
            </Avatar>
          }
          action={
            <IconButton aria-label="settings">
              <MoreVertIcon />
            </IconButton>
          }
          title={
            <Link 
              style={{ textDecoration: 'none', color: 'inherit' }}
              to={{ pathname: '/user/' + userId }}
              onClick={(e) => {
                if (!isUserLoggedIn()) {
                  e.preventDefault();
                  setLoginDialogOpen(true);
                }
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.textDecoration = 'underline';
                e.currentTarget.style.borderColor = colors.link.main;
                e.currentTarget.style.color = colors.link.main;
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.textDecoration = 'none';
                e.currentTarget.style.borderColor = 'transparent';
                e.currentTarget.style.color = 'inherit'; 
              }}
            >
              <Typography sx={{ fontWeight: 'bold' }}>
                  {userName}
              </Typography>
            </Link>
          }
          subheader={formattedCreationDate}
        />
        <CardMedia
          component="img"
          height="194"
          image={customImage}
          alt="Content Picture"
        />
        <CardContent>
          <Typography variant="body2" color="text.secondary">
            {contentText}
          </Typography>
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label="upvote" onClick={handleUpVoteClick}>
            <KeyboardArrowUpIcon />
          </IconButton>
          <Typography>
            {upVoteCount - downVoteCount}
          </Typography>
          <IconButton aria-label="downvote" onClick={handleDownVoteClick}>
            <KeyboardArrowDownIcon />
          </IconButton>
          <IconButton aria-label="repost">
            <RepeatIcon />
          </IconButton>
          <ExpandMore
            expand={expanded}
            onClick={handleExpandClick}
            aria-expanded={expanded}
            aria-label="show more"
          >
            <Typography style={{ color: 'black', marginRight: '0.5rem' }}>{commentCount}</Typography>
            <CommentIcon />
          </ExpandMore>
        </CardActions>
        <Dialog open={loginDialogOpen} onClose={() => setLoginDialogOpen(false)}>
          <DialogTitle>Login Required</DialogTitle>
          <DialogContent>
            <DialogContentText>
              You need to be logged in to vote or comment.
              Would you like to log in?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setLoginDialogOpen(false)} color="primary">
              Cancel
            </Button>
            <Button
              onClick={() => {
                setLoginDialogOpen(false);
                navigate(`/auth/login`);
              }}
              color="primary"
            >
              Log In
            </Button>
          </DialogActions>
        </Dialog>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <Container >
            {error ? "error" :
            isLoaded ? commentList.map(comment => (
              isUserLoggedIn() ? (
                <Comment
                  commentId={comment.id}
                  userId={comment.userId} 
                  userName={comment.userName} 
                  contentText={comment.contentText} 
                  creationDate={comment.creationDate} 
                  commentVotes={comment.commentVotes}
                />
              ) : null
            )) : "Loading"}
            {isUserLoggedIn() && (
              <CommentForm postId={postId} setCommentRefresh={setCommentRefresh} />
            )}
          </Container>
        </Collapse>
      </Card>
    )
}

export default Post