import axios from 'axios';

// Set up the base URL for the Play Framework API
const API_BASE_URL = 'http://34.133.198.126:9000';  // Replace with your backend URL

// Create an instance of axios
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000,  // Timeout after 5 seconds
  headers: {
    'Content-Type': 'application/json',
  },
});

// ==================== Event APIs ====================

// Create an event
const createEvent = async (eventData) => {
  try {
    const response = await api.post('/event', eventData);
    return response.data;
  } catch (error) {
    console.error('Error creating event:', error);
    throw error;
  }
};

// Get an event by ID
const getEventById = async (eventId) => {
  try {
    const response = await api.get(`/event/${eventId}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching event with ID ${eventId}:`, error);
    throw error;
  }
};

// Update an event by ID
const updateEvent = async (eventId, eventData) => {
  try {
    const response = await api.put(`/event/${eventId}`, eventData);
    return response.data;
  } catch (error) {
    console.error(`Error updating event with ID ${eventId}:`, error);
    throw error;
  }
};

// Update the status of an event by ID
const updateEventStatus = async (eventId, statusData) => {
  try {
    const response = await api.patch(`/event/${eventId}`, statusData);
    return response.data;
  } catch (error) {
    console.error(`Error updating event status with ID ${eventId}:`, error);
    throw error;
  }
};

// List events with optional filters
const listEvents = async (filters) => {
  const { eventType, status, eventDate, slotNumber } = filters;
  const query = new URLSearchParams();
  if (eventType) query.append('eventType', eventType);
  if (status) query.append('status', status);
  if (eventDate) query.append('eventDate', eventDate);
  if (slotNumber) query.append('slotNumber', slotNumber);
  
  try {
    const response = await api.get(`/event?${query.toString()}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching event list:', error);
    throw error;
  }
};

// Get upcoming events
const upcomingEvents = async () => {
    try {
        const response = await api.get(`/upcomingEvents`);
        return response.data;
      } catch (error) {
        console.error(`Error fetching upcoming`, error);
        throw error;
      }
  };

// Get tasks for a specific event ID
const getTasksForEventId = async (eventId) => {
  try {
    const response = await api.get(`/event/${eventId}/tasks`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching tasks for event with ID ${eventId}:`, error);
    throw error;
  }
};

// ==================== Task APIs ====================

// Create a new task
const createTask = async (taskData) => {
  try {
    const response = await api.post('/task', taskData);
    return response.data;
  } catch (error) {
    console.error('Error creating task:', error);
    throw error;
  }
};

// Get a task by ID
const getTaskById = async (taskId) => {
  try {
    const response = await api.get(`/task/${taskId}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching task with ID ${taskId}:`, error);
    throw error;
  }
};

// Update task status by ID
const updateTaskStatus = async (taskId, statusData) => {
  try {
    const response = await api.put(`/task/${taskId}`, statusData);
    return response.data;
  } catch (error) {
    console.error(`Error updating task status with ID ${taskId}:`, error);
    throw error;
  }
};

// Assign tasks to a team or individual
const assignTasks = async (taskAssignmentData) => {
  try {
    const response = await api.post('/task/assign', taskAssignmentData);
    return response.data;
  } catch (error) {
    console.error('Error assigning tasks:', error);
    throw error;
  }
};

// ==================== Team APIs ====================

// Create a new team
const createTeam = async (teamData) => {
  try {
    const response = await api.post('/team', teamData);
    return response.data;
  } catch (error) {
    console.error('Error creating team:', error);
    throw error;
  }
};

// Get team details by ID
const getTeamDetails = async (teamId) => {
  try {
    const response = await api.get(`/team/${teamId}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching team with ID ${teamId}:`, error);
    throw error;
  }
};

// List teams with optional filters
const listTeams = async (filters) => {
  const { teamType } = filters;
  const query = new URLSearchParams();
  if (teamType) query.append('teamType', teamType);

  try {
    const response = await api.get(`/team?${query.toString()}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching teams:', error);
    throw error;
  }
};

// ==================== Issue APIs ====================

// Create a new issue
const createIssue = async (issueData) => {
  try {
    const response = await api.post('/issue', issueData);
    return response.data;
  } catch (error) {
    console.error('Error creating issue:', error);
    throw error;
  }
};

// Get an issue by ID
const getIssueById = async (issueId) => {
  try {
    const response = await api.get(`/issue/${issueId}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching issue with ID ${issueId}:`, error);
    throw error;
  }
};

// Export all service functions
export default {
  createEvent,
  getEventById,
  updateEvent,
  updateEventStatus,
  listEvents,
  upcomingEvents,
  getTasksForEventId,
  createTask,
  getTaskById,
  updateTaskStatus,
  assignTasks,
  createTeam,
  getTeamDetails,
  listTeams,
  createIssue,
  getIssueById,
};
