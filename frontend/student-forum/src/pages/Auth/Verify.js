import React, { useEffect, useState } from "react";
import { Box, Button, TextField, Typography, CircularProgress, Snackbar, Alert } from "@mui/material";
import Navbar from "../../components/Navbar";
import Footer from "../../components/Footer";
import colors from "../../styles/Colors";
import { Link, useNavigate } from "react-router-dom";
import { PostWithoutAuth } from "../../services/HttpService";

const Verify = () => {
  const navigate = useNavigate();
  const [verificationCode, setVerificationCode] = useState("");
  const [authError, setAuthError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  


  const handleVerify = () => {
    if (!verificationCode) {
      setAuthError("Verification code cannot be empty!");
      setSnackbarOpen(true);
      return;
    }
    setIsLoading(true);

    // Simulate a verification process
    setTimeout(() => {
      setIsLoading(false);
      if (verificationCode === "123456") {
        setSuccessMessage("Verification successful! Redirecting...");
        setSnackbarOpen(true);
        setTimeout(() => {
          navigate("/auth/login");
        }, 2000);
      } else {
        setAuthError("Invalid verification code!");
        setSnackbarOpen(true);
      }
    }, 1500);
  };

  return (
    <>
      <Navbar />
      <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", minHeight: "calc(100vh - 64px)" }}>
        <Typography variant="h4" sx={{ fontWeight: "bold", mt: 3 }}>
          Verify Your Educational Mail
        </Typography>
        <Typography variant="body1" color="textSecondary" align="center" sx={{ mt: 2, maxWidth: "400px" }}>
          Please enter the verification code sent to your educational mail.
        </Typography>
        <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", width: "100%", maxWidth: "400px", mt: 3 }}>
          <TextField
            fullWidth
            id="verificationCode"
            label="Verification Code"
            type="text"
            placeholder="Enter your verification code"
            value={verificationCode}
            // onChange={(e) => handleVerificationCodeChange(e.target.value)}
            disabled={isLoading}
          />
          {isLoading ? (
            <CircularProgress sx={{ mt: 2 }} />
          ) : (
            <Button
              variant="contained"
              onClick={handleVerify}
              sx={{
                mt: 3,
                color: colors.text.white,
                backgroundColor: colors.button.darkerBlue,
                width: "100%",
                maxWidth: "210px",
              }}
            >
              Verify
            </Button>
          )}
          <Typography variant="body2" color="textSecondary" align="center" sx={{ mt: 2 }}>
            Already have an account?{" "}
            <Link style={{ textDecoration: "none", color: colors.link.main }} to={{ pathname: "/auth/login" }}>
              Login
            </Link>
          </Typography>
        </Box>
      </Box>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={successMessage ? 2000 : 4000}
        onClose={() => setSnackbarOpen(false)}
        anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
      >
        <Alert elevation={6} severity={successMessage ? "success" : "error"} onClose={() => setSnackbarOpen(false)} sx={{ width: "100%" }}>
          {successMessage || authError}
        </Alert>
      </Snackbar>
      <Footer />
    </>
  );
};

export default Verify;
