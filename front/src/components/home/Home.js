import React from 'react';
import PatientFormRequest from './GetPatient';
import PatientList from './PatientList';
import CreatePatient from './CreatePatient';
import './home.css';

const Home = (props) => {
   const setPatient = props.setPatient;

   return (
      <div className='home' >
         <CreatePatient setPatient={setPatient} />
         <PatientFormRequest setPatient={setPatient} />
         <PatientList />
      </div>
   )
};

export default Home;