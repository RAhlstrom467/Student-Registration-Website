import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';

export default function Student() {
  const paperStyle={padding:'50px 20px', width:600,margin:'20px auto'}
  const [name, setName]=React.useState('')
  const [address,setAddress]=React.useState('')
  return (
    <Container>
      <Paper elevation={3} style={paperStyle}>
        <h1 style={{color:"green"}}>Add Student</h1>
    <Box
      component="form"
      sx={{
        '& > :not(style)': { m: 1, width: '25ch' },
      }}
      noValidate
      autoComplete="off"
    >
      <TextField id="outlined-basic" label="Student Name" variant="outlined" value={name}
      onChange={(e)=>setName(e.target.value)} />
      <TextField id="outlined-basic" label="Student Address" variant="outlined" value={address}
      onChange={(e)=>setAddress(e.target.value)} />
      <TextField id="outlined-basic" label="Something" variant="outlined" />
      {/* <TextField id="filled-basic" label="Filled" variant="filled" />
      <TextField id="standard-basic" label="Standard" variant="standard" /> */}

    </Box>
    </Paper>
    {name}
    {address}
    </Container>
    
      );
    }
