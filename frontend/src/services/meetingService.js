import axios from 'axios';

const API_URL = '/api/meetings/';

const createMeeting = async (meetingData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.post(API_URL, meetingData, config);

  return response.data;
};

const meetingService = {
  createMeeting,
};

export default meetingService;
