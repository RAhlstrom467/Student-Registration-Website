import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import { Typography } from '@mui/material';

export default function NewStudent() {
  const paperStyle={padding:'50px 20px', width:600,margin:'20px auto'}
  const [studentFirstName, setSFName]=React.useState('')
  const [studentLastName,setSLName]=React.useState('')
  const [courseName, setCName]=React.useState('')
  const [studentUcid, setSUCID]=React.useState('')
  const [sectionNumber, setSecNum]=React.useState('')
  const [findCourse,setFindCourse]=React.useState([])
  const [enroll, setEnrollStudent]=React.useState('')
  const [unenroll, setUnEnrollStudent]=React.useState('')
  const [s_course_list, setStudentCList]=React.useState([])
  // let findCourse = ([]);

  const addStudent=(e)=>{
    e.preventDefault()
    const student={studentUcid,studentFirstName,studentLastName}
    console.log(student)
    fetch("http://localhost:8080/students/add/",{
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(student)
    }).then(()=>{
      console.log("New Student Added")
    })
    }

    const courseSearch=(e)=>{
      e.preventDefault()
      const course={courseName}
      console.log(course)
      fetch("http://localhost:8080/courses/find/"+courseName)
        .then(response=>response.json())
        .then((result)=>{
        console.log(course);
        console.log("token: ", result);
        setFindCourse(result);
        console.log("Searched Course" , findCourse)
      })
    }
  

    // const enrollStudent=(e)=>{
    //   e.preventDefault()
    //   const enroll={courseName,sectionNumber,studentUcid}
    //   console.log(enroll)
    //   let enroll_text = ("http://localhost:8080/enrollments/register/" ,{courseName} , "/" , {sectionNumber} , "/" ,{studentUcid})
    //   fetch(enroll_text)
    //     .then(response=>response)
    //     .then((result)=>{
    //     let token = result.token;
    //     console.log(enroll);
    //     console.log("token: ", token);
    //     setEnrollStudent(token);
    //     console.log("Enrolled Student",enroll)
    //   })
    // }

    const enrollStudent=(e)=>{
      e.preventDefault()
      const enroll={courseName,sectionNumber,studentUcid}
      // console.log(un_enroll)
      let enroll_text = ("http://localhost:8080/enrollments/register/" + courseName + "/" + sectionNumber + "/" + studentUcid)
      fetch((enroll_text),{
        method:"POST",
        headers:{"Content-Type": "application.json"},
        // body:JSON.stringify(un_enroll)
      }).then(()=>{
        console.log("Enrolled Student")
      })
      }

    const unEnrollStudent=(e)=>{
      e.preventDefault()
      const un_enroll={courseName,studentUcid}
      // console.log(un_enroll)
      let un_enroll_text = ("http://localhost:8080/enrollments/delete/" + courseName + "/" + studentUcid)
      fetch((un_enroll_text),{
        method:"DELETE",
        headers:{"Content-Type": "application.json"},
        // body:JSON.stringify(un_enroll)
      }).then(()=>{
        console.log("UnEnrolled")
      })
      }

    const studentCourse=(e)=>{
      e.preventDefault()
      const student_course={studentUcid}
      console.log(student_course)
      fetch("http://localhost:8080/students/findcourses/"+studentUcid)
        .then(response=>response.json())
        .then((result)=>{
        console.log(student_course);
        setStudentCList(result);
        console.log(s_course_list[0].section)
        console.log("Course List: ",s_course_list)
      })
    }


  return (
    <Container>
    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Add Student: </h1>
    <Box
      component="form"
      sx={{
        '& .MuiTextField-root': { m: 1, width: '25ch' },
      }}
      noValidate
      autoComplete="off"
    >
      <div>
        <TextField
          required
          id="outlined-required"
          label="Student First Name"
          // defaultValue="Course Name"
          helperText="EX: ENSF"
          value={studentFirstName}
          onChange={(e)=>setSFName(e.target.value)}
        />
        <TextField
          required
          id="outlined-number"
          label="Student Last Name"
          helperText="EX: 607"
          value={studentLastName}
          onChange={(e)=>setSLName(e.target.value)}
        />
        <TextField
          required
          id="outlined-number"
          label="UCID"
          helperText="EX: 12345678"
          value={studentUcid}
          onChange={(e)=>setSUCID(e.target.value)}
        />
       
        
      </div>
      {/* <Button variant="outlined">
        Validation Input Form   URL : Enrolling Course Name, Section Number, UCID  |   Unenroll: Coursename UCID,    |   Search Done,   Add Student Done  |   Get Student Courses:  UCID
      </Button> */}
      
      <Button variant="outlined" onClick={addStudent}>Add Student </Button>
      {enroll}
    </Box>  
    </Paper>

{/* ENROLL IN COURSE: */}

    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Enroll In Course: </h1>
      <TextField
          id="outlined-read-only-input"
          label="Course Name"
          helperText="EX: ENSF607"
          value={courseName}
          onChange={(e)=>setCName(e.target.value)}       
        />
      <TextField
          id="outlined-read-only-input"
          label="Section Number"
          helperText="EX: 1"
          value={sectionNumber}
          onChange={(e)=>setSecNum(e.target.value)}       
        />
        <TextField
          required
          id="outlined-number"
          label="UCID"
          helperText="EX: 12345678"
          value={studentUcid}
          onChange={(e)=>setSUCID(e.target.value)}
        />
        <br></br>
        <Button variant="outlined" onClick={enrollStudent}> Enroll Student </Button>
        {enroll}
    </Paper>

    {/* UN-ENROLL IN COURSE: */}

    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Unenroll In Course: </h1>
      <TextField
          id="outlined-read-only-input"
          label="Course Name"
          helperText="EX: ENSF607"
          value={courseName}
          onChange={(e)=>setCName(e.target.value)}       
        />
          <TextField
          required
          id="outlined-number"
          label="UCID"
          helperText="EX: 12345678"
          value={studentUcid}
          onChange={(e)=>setSUCID(e.target.value)}
        />
        <br></br>
      <Button variant="outlined" onClick={unEnrollStudent}> Un-enroll Student </Button>
      {unenroll}

    </Paper>

      {/* Search For Course: */}

    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Search Course: </h1>
      <TextField
          id="outlined-read-only-input"
          label="Course Name"
          helperText="EX: ENSF607"
          value={courseName}
          onChange={(e)=>setCName(e.target.value)}       
        />
        <br></br>
        <Button variant="outlined" onClick={courseSearch}>Search Course</Button>
        <br></br><Typography>
        Course Name: {findCourse.name}
        <br></br>
        Start Date: {findCourse.startDate}
        <br></br>
        End Date: {findCourse.endDate}
        <br></br>
        </Typography>
    </Paper>

      {/* Add Course: */}

    {/* <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Add Course: </h1>
      <br></br>
      <Button variant="outlined" onClick={enrollStudent}> Add Course To DB </Button>
    </Paper> */}

      {/* Display All Courses:: */}

    <Paper elevation={3} style={paperStyle}>
      <h1 style={{color:"green"}}>Display All Courses For Student: </h1>
      <TextField
          required
          id="outlined-number"
          label="UCID"
          helperText="EX: 12345678"
          value={studentUcid}
          onChange={(e)=>setSUCID(e.target.value)}
        />
        <br></br>
        <Button variant="outlined" onClick={studentCourse}> Display All Courses For Student </Button>
        <br></br><Typography>
          {/* {JSON.stringify(s_course_list)} */}
        {s_course_list.length === 0 ? "Please Click Button" : s_course_list.map(s=>
        <Typography>
          Course Name: {s.course.name}<br></br>
          Section Numbers: {s.sectionNumber}<br></br>
          </Typography>
          )}
    
        
        </Typography>
    </Paper>
    </Container>
  );
}
