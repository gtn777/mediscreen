import React, { useState } from "react";
import { Route, NavLink, Routes } from "react-router-dom";
import Patient from './components/patient/Patient';
import Home from './components/home/Home';
import Note from './components/note/Note';
import './index.css';


function App() {
  const [patient, setPatient] = useState({
    family: "_empty"
  });

  return (
    <div className="app" >
      <nav  id="sidebar">
        <h1>Mediscreen</h1>
        <NavLink to="/">Home</NavLink>
        <NavLink to="/patient">Patient</NavLink>
        <NavLink to="/note">History</NavLink>
      </nav>
      <Routes>
        <Route path="/patient" element={<Patient patient={patient} setPatient={setPatient} />} />
        <Route path="/note" element={<Note patient={patient} />} />
        <Route path="/home" element={<Home setPatient={setPatient} />} />
        <Route path="/" element={<Home setPatient={setPatient} />} />
      </Routes>
    </div>
  );
};

export default App;
