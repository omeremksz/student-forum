import { AppBar, Box, Button, Container, IconButton, Menu, MenuItem, Toolbar, Tooltip, Typography } from '@mui/material'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import colors from '../../../styles/Colors';
import GroupsIcon from '@mui/icons-material/Groups';
import MenuIcon from '@mui/icons-material/Menu';

const ProfileHeader = () => {
    const navigate = useNavigate();
    const [anchorElUser, setAnchorElUser] = useState(null)

    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const handleLogOut = () => {
        localStorage.removeItem("userId");
        localStorage.removeItem("userName");
        localStorage.removeItem("tokenKey");
        setTimeout(() => {
            navigate('/auth/login');
        }, 300)
    };

  return (
    <AppBar position='static' >
        <Container maxWidth="xl">
            <Toolbar disableGutters>
                <Box sx={{
                    display: 'flex', 
                    alignItems: 'center', 
                    justifyContent: 'space-between', 
                    flexGrow: 1,
                }}>
                    <Button
                        component={Link}
                        to='/'
                        variant='text'
                        sx={{
                            color: colors.text.main,
                            textTransform: 'uppercase',
                            letterSpacing: '0.05rem',
                            textDecoration: 'none',
                            fontSize: '14px',
                            fontWeight: 'bold',
                            display: 'flex',
                            alignItems: 'center',
                    }}>
                        <GroupsIcon sx={{ mr: '0.5rem' }} />
                        Student Forum
                    </Button>
                    <Box>
                        <Tooltip title="Open settings">
                        <IconButton edge="start" color="inherit" aria-label="menu" onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                            <MenuIcon />
                        </IconButton>
                        </Tooltip>
                        <Menu
                            sx={{ mt: '45px' }}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >
                            <MenuItem onClick={handleCloseUserMenu}>
                                <Typography textAlign="center">
                                <Link style={{textDecoration: "none", color:"black"}}  to={{ pathname: "user/" + localStorage.getItem("userId")}}>
                                    Profile
                                </Link>
                                </Typography>
                            </MenuItem>
                            <MenuItem onClick={handleLogOut}>
                                <Typography textAlign="center" sx={{fontSize:"16px"}}>Log Out</Typography>
                            </MenuItem>
                        </Menu>
                    </Box>
                </Box>
            </Toolbar>
        </Container>
    </AppBar>
  )
}

export default ProfileHeader