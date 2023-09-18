import { ThemeProvider } from "@emotion/react";
import { CssBaseline } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home/Home";
import theme from "./styles/Theme";
import Login from "./pages/Auth/Login";
import Register from "./pages/Auth/Register";
import User from "./pages/Profile/User/User";
import Verify from "./pages/Auth/Verify";

function App() {
  return (
    <ThemeProvider theme={ theme }>
      <CssBaseline>
        <BrowserRouter>
          <Routes>

            <Route path="/" element={<Home/>}/>

            <Route path="/user/:userId" element={<User/>}/>

            <Route path="/auth/login" element={<Login/>}/>
            <Route path="/auth/register" element={<Register/>}/>
            <Route path="/auth/verify" element={<Verify/>}/> 

          </Routes>
        </BrowserRouter>
      </CssBaseline>
    </ThemeProvider>
  );
}

export default App;
