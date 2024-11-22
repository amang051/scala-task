import React, { useState, useEffect } from 'react';
import api from '../services/ApiService'; // Import the API functions

// Material-UI Components
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle, List, ListItem, ListItemText, Typography, Grid, Box, MenuItem, Select, InputLabel, FormControl } from '@mui/material';

const EventManagement = () => {
  const [todayEvents, setTodayEvents] = useState([]);
  const [upcomingEvents, setUpcomingEvents] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [teams, setTeams] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(0);
  const [showCreateEventForm, setShowCreateEventForm] = useState(false);
  const [showUpdateEventForm, setShowUpdateEventForm] = useState(false);
  const [showCreateTaskForm, setShowCreateTaskForm] = useState(false);
  const [showCreateIssueForm, setShowCreateIssueForm] = useState(false);
  const [showTaskList, setShowTaskList] = useState(false);
  const [newEventData, setNewEventData] = useState({ eventType: '', eventName: '', eventDate: '', eventStatus: '', guestCount: 0, slotNumber: 0, specialRequirements: '' });
  const [newTaskData, setNewTaskData] = useState({ teamId: '', taskDescription: '', deadLine: '', specialInstructions: '' });
  const [newIssueData, setNewIssueData] = useState({ issueTitle: '', description: '', taskId: '' });

  // Fetch today's and upcoming events
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const upcomingEvents = await api.upcomingEvents();
        const todayEvents = await api.listEvents({ eventDate: new Date().toISOString().split('T')[0] });
        setTodayEvents([...todayEvents]);
        setUpcomingEvents([...upcomingEvents]);
      } catch (error) {
        console.error('Error fetching events:', error);
      }
    };

    const fetchTeams = async () => {
      try {
        const teamList = await api.listTeams({});
        setTeams(teamList);
      } catch (error) {
        console.error('Error fetching teams:', error);
      }
    };

    fetchEvents();
    fetchTeams();
  }, []);

  // Show task list for a specific event
  const handleShowTaskList = async (eventId) => {
    try {
      const taskList = await api.getTasksForEventId(eventId);
      setTasks(taskList);
      setShowTaskList(true);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };

  // Handle Create Event form submission
  const handleCreateEvent = async () => {
    try {
      await api.createEvent(newEventData);
      setShowCreateEventForm(false);
      setTodayEvents(await api.listEvents({ eventDate: new Date().toISOString().split('T')[0] }));
      setUpcomingEvents(await api.upcomingEvents());
    } catch (error) {
      console.error('Error creating event:', error);
    }
  };

  // Handle Update Event form submission
  const handleUpdateEvent = async () => {
    try {
      await api.updateEvent(selectedEvent, newEventData);
      setShowUpdateEventForm(false);
      setTodayEvents(await api.listEvents({ eventDate: new Date().toISOString().split('T')[0] }));
      setUpcomingEvents(await api.upcomingEvents());
    } catch (error) {
      console.error('Error updating event:', error);
    }
  };

  // Handle Create Task form submission with new data format
  const handleCreateTask = async () => {
    try {
      const taskData = {
        eventId: parseInt(selectedEvent, 10),
        tasks: [
          {
            teamId: newTaskData.teamId,
            taskDescription: newTaskData.taskDescription,
            deadLine: newTaskData.deadLine,
            specialInstructions: newTaskData.specialInstructions,
          }
        ]
      };
      await api.assignTasks(taskData);
      setShowCreateTaskForm(false);
      handleShowTaskList(selectedEvent);  // Refresh task list after task is added
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  // Handle Create Issue form submission
  const handleCreateIssue = async () => {
    try {
      await api.createIssue(newIssueData);
      setShowCreateIssueForm(false);
    } catch (error) {
      console.error('Error creating issue:', error);
    }
  };

  // Set event data for update form
  const handleEditEvent = (event) => {
    setSelectedEvent(event.id);
    setNewEventData({
      eventType: event.eventType,
      eventName: event.eventName,
      eventDate: event.eventDate,
      eventStatus: event.eventStatus,
      guestCount: event.guestCount,
      slotNumber: event.slotNumber,
      specialRequirements: event.specialRequirements,
    });
    setShowUpdateEventForm(true);
  };

  const handleCreateTask1 = (event) => {
    setSelectedEvent(event.id);
    setNewEventData({
      eventType: event.eventType,
      eventName: event.eventName,
      eventDate: event.eventDate,
      eventStatus: event.eventStatus,
      guestCount: event.guestCount,
      slotNumber: event.slotNumber,
      specialRequirements: event.specialRequirements,
    });
    setShowCreateTaskForm(true);
  };

  return (
    <Box sx={{ padding: 2 }}>
      <Typography variant="h4" gutterBottom>Event Management</Typography>

      {/* Event Management Actions */}
      <Box sx={{ marginBottom: 2 }}>
        <Button variant="contained" color="primary" onClick={() => setShowCreateEventForm(true)}>Create New Event</Button>
      </Box>

      {/* Event List */}
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <Typography variant="h6">Today's Events</Typography>
          <List>
            {todayEvents.map((event) => (
              <ListItem key={event.id}>
                <ListItemText primary={event.eventType} secondary={event.eventDate} />
                <Button variant="outlined" size="small" onClick={() => handleShowTaskList(event.id)}>Show Tasks</Button>
                <Button variant="outlined" size="small" onClick={() => handleEditEvent(event)} sx={{ marginLeft: 1 }}>Update Event</Button>
                <Button variant="outlined" size="small" onClick={() => handleCreateTask1(event)} sx={{ marginLeft: 1 }}>Assign Task</Button>
              </ListItem>
            ))}
          </List>
        </Grid>
        <Grid item xs={6}>
          <Typography variant="h6">Upcoming Events</Typography>
          <List>
            {upcomingEvents.map((event) => (
              <ListItem key={event.id}>
                <ListItemText primary={event.eventType} secondary={event.eventDate} />
                <Button variant="outlined" size="small" onClick={() => handleShowTaskList(event.id)}>Show Tasks</Button>
                <Button variant="outlined" size="small" onClick={() => handleEditEvent(event)} sx={{ marginLeft: 1 }}>Update Event</Button>
                <Button variant="outlined" size="small" onClick={() => setShowCreateTaskForm(true)} sx={{ marginLeft: 1 }}>Assign Task</Button>
              </ListItem>
            ))}
          </List>
        </Grid>
      </Grid>

      {/* Create Event Dialog */}
      <Dialog open={showCreateEventForm} onClose={() => setShowCreateEventForm(false)}>
        <DialogTitle>Create Event</DialogTitle>
        <DialogContent>
          <TextField
            label="Event Type"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, eventType: e.target.value })}
          />
          <TextField
            label="Event Name"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, eventName: e.target.value })}
          />
          <TextField
            label="Event Date"
            type="date"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, eventDate: e.target.value })}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Status"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, eventStatus: e.target.value })}
          />
          <TextField
            label="Guest Count"
            type="number"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, guestCount: parseInt(e.target.value, 10) })}
          />
          <TextField
            label="Slot Number"
            type="number"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, slotNumber: parseInt(e.target.value, 10) })}
          />
          <TextField
            label="Special Requirements"
            fullWidth
            margin="dense"
            onChange={(e) => setNewEventData({ ...newEventData, specialRequirements: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setShowCreateEventForm(false)}>Cancel</Button>
          <Button onClick={handleCreateEvent} color="primary">Create</Button>
        </DialogActions>
      </Dialog>

      {/* Update Event Dialog */}
      <Dialog open={showUpdateEventForm} onClose={() => setShowUpdateEventForm(false)}>
        <DialogTitle>Update Event</DialogTitle>
        <DialogContent>
          <TextField
            label="Event Type"
            fullWidth
            margin="dense"
            value={newEventData.eventType}
            onChange={(e) => setNewEventData({ ...newEventData, eventType: e.target.value })}
          />
          <TextField
            label="Event Name"
            fullWidth
            margin="dense"
            value={newEventData.eventName}
            onChange={(e) => setNewEventData({ ...newEventData, eventName: e.target.value })}
          />
          <TextField
            label="Event Date"
            type="date"
            fullWidth
            margin="dense"
            value={newEventData.eventDate}
            onChange={(e) => setNewEventData({ ...newEventData, eventDate: e.target.value })}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Status"
            fullWidth
            margin="dense"
            value={newEventData.eventStatus}
            onChange={(e) => setNewEventData({ ...newEventData, eventStatus: e.target.value })}
          />
          <TextField
            label="Guest Count"
            type="number"
            fullWidth
            margin="dense"
            value={newEventData.guestCount}
            onChange={(e) => setNewEventData({ ...newEventData, guestCount: parseInt(e.target.value, 10) })}
          />
          <TextField
            label="Slot Number"
            type="number"
            fullWidth
            margin="dense"
            value={newEventData.slotNumber}
            onChange={(e) => setNewEventData({ ...newEventData, slotNumber: parseInt(e.target.value, 10) })}
          />
          <TextField
            label="Special Requirements"
            fullWidth
            margin="dense"
            value={newEventData.specialRequirements}
            onChange={(e) => setNewEventData({ ...newEventData, specialRequirements: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setShowUpdateEventForm(false)}>Cancel</Button>
          <Button onClick={handleUpdateEvent} color="primary">Update</Button>
        </DialogActions>
      </Dialog>

      {/* Create Task Dialog */}
      <Dialog open={showCreateTaskForm} onClose={() => setShowCreateTaskForm(false)}>
        <DialogTitle>Assign Task</DialogTitle>
        <DialogContent>
          <FormControl fullWidth margin="dense">
            <InputLabel>Team</InputLabel>
            <Select
              value={newTaskData.teamId}
              onChange={(e) => setNewTaskData({ ...newTaskData, teamId: e.target.value })}
              label="Team"
            >
              {teams.map((team) => (
                <MenuItem key={team.id} value={team.id}>
                  {team.teamName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <TextField
            label="Task Description"
            fullWidth
            margin="dense"
            onChange={(e) => setNewTaskData({ ...newTaskData, taskDescription: e.target.value })}
          />
          <TextField
            label="Deadline"
            type="datetime-local"
            fullWidth
            margin="dense"
            onChange={(e) => setNewTaskData({ ...newTaskData, deadLine: e.target.value })}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Special Instructions"
            fullWidth
            margin="dense"
            onChange={(e) => setNewTaskData({ ...newTaskData, specialInstructions: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setShowCreateTaskForm(false)}>Cancel</Button>
          <Button onClick={handleCreateTask} color="primary">Assign Task</Button>
        </DialogActions>
      </Dialog>

      {/* Task List Dialog */}
      <Dialog open={showTaskList} onClose={() => setShowTaskList(false)}>
  <DialogTitle>Task List</DialogTitle>
  <DialogContent>
    <List>
      {tasks.map((task) => (
        <ListItem key={task.eventId}>
          <ListItemText
            primary={<strong>{task.taskDescription}</strong>}  // Display task description as the main title
            secondary={
              <>
                <div><strong>Assigned to:</strong> Team {task.teamId}</div>
                <div><strong>Deadline:</strong> {new Date(task.deadLine).toLocaleString()}</div>
                <div><strong>Status:</strong> {task.status}</div>
                <div><strong>Special Instructions:</strong> {task.specialInstructions}</div>
                <div><strong>Created At:</strong> {new Date(task.createdAt).toLocaleString()}</div>
              </>
            }
          />
        </ListItem>
      ))}
    </List>
  </DialogContent>
  <DialogActions>
    <Button onClick={() => setShowTaskList(false)}>Close</Button>
  </DialogActions>
</Dialog>
    </Box>
  );
};

export default EventManagement;
