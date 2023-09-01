import { ThemeProvider } from "@emotion/react";
import { CssBaseline } from "@mui/material";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home/Home";
import theme from "./styles/Theme";

function App() {
  return (
    <ThemeProvider theme={ theme }>
      <CssBaseline>
        <BrowserRouter>
          <Routes>

            <Route path='/' element={<Home/>}/>

          </Routes>
        </BrowserRouter>
      </CssBaseline>
    </ThemeProvider>
  );
}

export default App;
