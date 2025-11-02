import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate, Link } from 'react-router-dom';
import {
  Container,
  Typography,
  Button,
  Box,
} from '@mui/material';

const DashboardPage = () => {
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);

  useEffect(() => {
    if (!user) {
      navigate('/login');
    }
  }, [user, navigate]);

  return (
    <Container>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h4" component="h1">
          Dashboard
        </Typography>
        <Button variant="contained" color="primary" component={Link} to="/create-meeting">
          Create Meeting
        </Button>
      </Box>
      {user && <Typography variant="h6">Welcome, {user.username}! Here are your upcoming meetings.</Typography>}
    </Container>
  );
};

export default DashboardPage;
