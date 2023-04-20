import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import './App.css';
import './index.css';
import PatientList from './components/PatientList';
import Patient from './components/patient/Patient';


function App() {
  console.info("-App 1");
  return (
    <div className="App">
      {console.info("-App 2")}
      <Routes>
        <Route path="/home" element={<Patient />} />
        <Route path="/" element={<Patient />} />
      </Routes>
    </div>
  );
};

export default App;
