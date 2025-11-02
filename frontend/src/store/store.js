import { configureStore } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import meetingReducer from './meetingSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    meetings: meetingReducer,
  },
});
