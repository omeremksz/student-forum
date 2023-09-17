import { Alert, Avatar, Box, Button, Paper, Snackbar, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import Navbar from '../../components/Navbar'
import { LockClockOutlined } from '@mui/icons-material'
import Footer from '../../components/Footer'
import colors from '../../styles/Colors'
import { Link, useNavigate } from 'react-router-dom'
import { PostWithoutAuth } from '../../services/HttpService'
import { CircularProgress } from '@mui/material';

const Login = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const [authError, setAuthError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const handleUserName = (value) => {
    setUserName(value);
  };

  const handlePassword = (value) => {
      setPassword(value);
  };

  const handleUserLogin = () => {
    if (!userName || !password) {
      setAuthError("Username and/or password cannot be empty!");
      setSnackbarOpen(true);
      return;
    }
    setIsLoading(true);

    sendLoginRequest();
  };

  const sendLoginRequest = () => {
    PostWithoutAuth("/auth/login", {
        userName : userName,
        password: password,
    })
    .then((res) => res.json())
    .then((result) => {
                    localStorage.setItem("userId", result.id);
                    localStorage.setItem("userName", result.userName);
                    localStorage.setItem("tokenKey", result.accessToken);
                    setSuccessMessage('Login successful! Redirecting...');
                    setSnackbarOpen(true);
                    setTimeout(() => {
                      setIsLoading(false);
                      navigate('/user/' + result.id);
                    }, 2000);
                })
    .catch((err) => {
      setIsLoading(false);
      setAuthError("Invalid username or password!");
      setSnackbarOpen(true);
      setUserName("");
      setPassword("");
    })
};

  return (
    <>
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Navbar />
        <Box sx={{ flex: '1 0 auto', display: "flex", alignItems: "center", justifyContent: "center" }}>
          <Box component="form" className='form_style border-style' >
          <Paper elevation={3} sx={{ p: 3, width: '90%', minWidth: '400px' }}>
            <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%" }}>
              <Avatar sx={{ bgcolor: colors.button.darkerBlue, mb: 1, mt: 0, }}>
                  <LockClockOutlined />
              </Avatar>
              <Typography variant="body2" sx={{ fontWeight: 'bold', fontSize:"22px" }}>
                  Log In
              </Typography>
              <TextField sx={{ mb: 3, mt: 3, width: '100%',  maxWidth: '300px' }}
                        fullWidth
                        id="username"
                        label="Username"
                        name='username'
                        InputLabelProps={{
                            shrink: true,
                        }}
                        placeholder="Username"
                        onChange={(i) => handleUserName(i.target.value)}
                        value={userName}
                        disabled={isLoading}
              />
              <TextField sx={{ mb: 3, width: '100%',  maxWidth: '300px' }}
                        fullWidth
                        id="password"
                        name="password"
                        label="Password"
                        type="password"
                        InputLabelProps={{
                            shrink: true,
                        }}
                        placeholder="Password"
                        onChange={(i) => handlePassword(i.target.value)}
                        value={password}
                        disabled={isLoading}
              />
              {isLoading ? (
                <CircularProgress sx={{ mb: 3 }} />
              ) : (
                <Button fullWidth variant="contained" onClick={() => handleUserLogin()} 
                  sx={{
                    mb: 2,
                    color: colors.text.white,
                    backgroundColor: colors.button.darkerBlue,
                    width: '100%',
                    maxWidth: '210px', 
                  }}
                >
                  Log In
                </Button>
              )}
              <Typography variant="body2" color="textSecondary" align="center">
                Don't have an account?&nbsp;
                <Link style={{textDecoration: "none", color: colors.link.main}} to={{ pathname: "/auth/register" }} >
                  Sign up
                </Link>
              </Typography>
            </Box>
            </Paper>
          </Box>
        </Box>
        <Snackbar 
          open={snackbarOpen} 
          autoHideDuration={successMessage ? 2000 : 4000} 
          onClose={() => setSnackbarOpen(false)} 
          anchorOrigin={{vertical: "bottom", horizontal: "center",}}
          style={{ bottom: '75px', left: '50%' }}
        >
          <Alert elevation={6} severity={successMessage ? "success" : "error"} onClose={() => setSnackbarOpen(false)} sx={{ width: '100%' }}>
            {successMessage || authError}
          </Alert>
        </Snackbar>
        <Footer />
      </Box>
    </>
  )
}

export default Login