import * as React from 'react';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import { Typography } from '@mui/material';

export default function Course() {
  const paperStyle={padding:'50px 20px', width:600,margin:'20px auto'}
  const [allCourse,setAllCourse]=React.useState([])

  React.useEffect(()=>{
    fetch("http://localhost:8080/courses/allcourses")
    .then(res=>res.json())
    .then((result)=> {
      setAllCourse(result);
    }
    )
},[])

    

  return (
    <Container>
    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>All Course Information</h1>
    <Box component="form" sx={{ '& .MuiTextField-root': { m: 1, width: '25ch' }, }} noValidate autoComplete="off" >
      {allCourse.map(course=>(
      <Paper elevation={6} style={{margin:"8px",padding:"10px",textAlign:"left"}} key={course.name}>
        Name: {course.name}<br></br>
        Start Date: {course.startDate}<br></br>
        End Date: {course.endDate}<br></br>
        <br></br>
        {course.sectionList.length === 0 ? "No Section Available" : course.sectionList.map(s => 
          <Typography key = {s.sectionNumber}>
            Section Number Available: {s.sectionNumber}
          </Typography>)}
          <br></br>
        {course.preReqs.length === 0 ? "No PreReq's" : course.preReqs.map(p =>
        <Typography key={p.name}>
        Pre Reqs: {p.name}
        </Typography>
        )}
      </Paper>
    ))
    }
    </Box>
    </Paper>
    </Container>
  );
}
