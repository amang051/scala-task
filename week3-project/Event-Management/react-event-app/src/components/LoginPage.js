import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username === 'admin' && password === 'password') {
      navigate('/main');
    } else {
      alert('Invalid credentials!');
    }
  };

  return (
    <Container component="main" maxWidth="xs">
      <Box sx={styles.container}>
        <Typography variant="h5">Login</Typography>
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          label="Username"
          autoFocus
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          variant="outlined"
          margin="normal"
          required
          fullWidth
          label="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          fullWidth
          variant="contained"
          color="primary"
          sx={styles.button}
          onClick={handleLogin}
        >
          Login
        </Button>
      </Box>
    </Container>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
  },
  button: {
    marginTop: '20px',
  },
};

export default LoginPage;