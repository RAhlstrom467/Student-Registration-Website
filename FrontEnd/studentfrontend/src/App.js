import './App.css';
import Appbar from './components/Appbar'
// import Student from './components/Student'
import NewStudent from './components/NewStudent'
import Course from './components/Course'
import React from 'react';


function App() {
  return (
  <div>
    <div id='bar'>
    <Appbar/>
    </div>
    <div id="parent">
      <div id="left">
      <NewStudent/>
      </div>
      <div id="right">
        <Course/>
      </div>
    </div>
    </div>

    // <React.Fragment>
    //   <Appbar/>
    //   <NewStudent/>
    //   <Course/>
    // </React.Fragment>


  );
}

export default App;
