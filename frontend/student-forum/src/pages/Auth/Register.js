import { Alert, Avatar, Box, Button, Checkbox, CircularProgress, FormControlLabel, Paper, Snackbar, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import Footer from '../../components/Footer'
import Navbar from '../../components/Navbar'
import colors from '../../styles/Colors'
import { Link, useNavigate } from 'react-router-dom'
import HowToRegIcon from '@mui/icons-material/HowToReg';
import { PostWithoutAuth } from '../../services/HttpService'

const Register = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [eduEmail, setEduEmail] = useState("");
  const [password, setPassword] = useState("");

  const [acceptTerms, setAcceptTerms] = useState(false);
  const [authError, setAuthError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);


  const handleUserName = (value) => {
    setUserName(value);
  };

  const handleEduEmail= (value) => {
    setEduEmail(value);
  };

  const handlePassword = (value) => {
    setPassword(value);
  };

  const handleAcceptTerms = (event) => {
    setAcceptTerms(event.target.checked);
  };

  const handleUserSignUp = () => {
    if (!userName || !eduEmail || !password) {
      setAuthError("Missing information! Please complete all necessary fields.");
      setSnackbarOpen(true);
      return;
    }
    setIsLoading(true);

    sendRegisterRequest();
  };

  const sendRegisterRequest = () => {
    PostWithoutAuth("/auth/register", {
      userName : userName,
      password: password,
      educationalEmail: eduEmail,
  })
  .then((res) => {
    if (res.status === 201) {
      setSuccessMessage('Sign up successful! Redirecting...');
      setSnackbarOpen(true);
      setTimeout(() => {
        setIsLoading(false);
        navigate('/auth/login');
      }, 2000);
    } else {
      setIsLoading(false);
      setAuthError("Username or email already exists!");
      setSnackbarOpen(true);
      setUserName('');
      setEduEmail('');
      setPassword('');
    }
  })
  .catch((err) => {
    setIsLoading(false);
    setAuthError('An error occurred while registering.');
    setSnackbarOpen(true);
    setUserName('');
    setEduEmail('');
    setPassword('');
  })
  };

  return (
    <>
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Navbar/>
        <Box sx={{ flex: '1 0 auto', display: "flex", alignItems: "center", justifyContent: "center" }}>
          <Box component="form" className='form_style border-style' >
            <Paper elevation={3} sx={{ p: 3, width: '90%', minWidth: '500px' }}>
              <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <Avatar sx={{ bgcolor: colors.button.darkerBlue, mb: 1, mt: 0, }}>
                    <HowToRegIcon />
                </Avatar>
                <Typography variant="body2" sx={{ fontWeight: 'bold', fontSize:"22px", }}>
                  Sign Up
                </Typography>
                <TextField
                  fullWidth
                  id="username"
                  label="Username"
                  name="username"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  sx={{ mb: 3, mt: 3,  width: '100%',  maxWidth: '400px' }}
                  onChange={(i) => handleUserName(i.target.value)}
                  value={userName}
                />
                <TextField
                  fullWidth
                  id="eduEmail"
                  label="Educational Email"
                  name="eduEmail"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  sx={{ mb: 3, width: '100%', maxWidth: '400px' }}
                  onChange={(i) => handleEduEmail(i.target.value)}
                  value={eduEmail}
                />
                <TextField
                  fullWidth
                  id="password"
                  name="password"
                  label="Password"
                  type="password"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  sx={{ mb: 2, width: '100%', maxWidth: '400px' }}
                  onChange={(i) => handlePassword(i.target.value)}
                  value={password}
                />
                <FormControlLabel
                  sx={{ mb: 2, }}
                  control={
                    <Checkbox
                      checked={acceptTerms}
                      onChange={handleAcceptTerms}
                      name="acceptTerms"
                      color="primary"
                    />
                  }
                  label={
                    <Typography variant="body2" sx={{ fontSize: '12px' }}>
                      I accept the terms and conditions
                    </Typography>
                  }
                />
                {isLoading ? (
                  <CircularProgress sx={{ mb: 3 }} />
                ) : (
                <Button fullWidth variant="contained" onClick={() => handleUserSignUp()} 
                  sx={{
                    mb: 2,
                    color: colors.text.white,
                    backgroundColor: colors.button.darkerBlue,
                    width: '100%',
                    maxWidth: '210px', 
                  }}
                  >
                    Sign up
                </Button>
                )}
                <Typography variant="body2" color="textSecondary" align="center" sx={{mb: 0}}>
                  Already have an account?&nbsp;
                <Link style={{textDecoration: "none", color: colors.link.main}} to={{ pathname: "/auth/login" }} >
                  Log in
                </Link>
              </Typography>
              </Box>
            </Paper>
          </Box>
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
      <Footer/>
    </>
  )
}

export default Register