import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import meetingService from '../services/meetingService';

const initialState = {
  meetings: [],
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
};

export const createMeeting = createAsyncThunk(
  'meetings/create',
  async (meetingData, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await meetingService.createMeeting(meetingData, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const meetingSlice = createSlice({
  name: 'meeting',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(createMeeting.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(createMeeting.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.meetings.push(action.payload);
      })
      .addCase(createMeeting.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      });
  },
});

export const { reset } = meetingSlice.actions;
export default meetingSlice.reducer;
