import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { createMeeting, reset } from '../store/meetingSlice';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  CircularProgress,
} from '@mui/material';
import { useSnackbar } from 'notistack';

const CreateMeetingPage = () => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    startTime: '',
    endTime: '',
    participants: '',
  });

  const { title, description, startTime, endTime, participants } = formData;

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();

  const { isLoading, isError, isSuccess, message } = useSelector(
    (state) => state.meetings
  );

  useEffect(() => {
    if (isError) {
      enqueueSnackbar(message, { variant: 'error' });
    }

    if (isSuccess) {
      enqueueSnackbar('Meeting created successfully!', { variant: 'success' });
      navigate('/dashboard');
    }

    dispatch(reset());
  }, [isError, isSuccess, message, navigate, dispatch, enqueueSnackbar]);

  const onChange = (e) => {
    setFormData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };

  const onSubmit = (e) => {
    e.preventDefault();
    const meetingData = {
      title,
      description,
      startTime,
      endTime,
      participants: participants.split(',').map((email) => email.trim()),
    };
    dispatch(createMeeting(meetingData));
  };

  if (isLoading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center' }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography component="h1" variant="h5">
          Create Meeting
        </Typography>
        <Box component="form" onSubmit={onSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="title"
            label="Title"
            name="title"
            autoFocus
            value={title}
            onChange={onChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="description"
            label="Description"
            name="description"
            value={description}
            onChange={onChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="startTime"
            label="Start Time"
            name="startTime"
            type="datetime-local"
            InputLabelProps={{
              shrink: true,
            }}
            value={startTime}
            onChange={onChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="endTime"
            label="End Time"
            name="endTime"
            type="datetime-local"
            InputLabelProps={{
              shrink: true,
            }}
            value={endTime}
            onChange={onChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="participants"
            label="Participants (comma-separated emails)"
            name="participants"
            value={participants}
            onChange={onChange}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
          >
            Create
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default CreateMeetingPage;
