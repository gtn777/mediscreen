import React, { useState } from 'react';
import './patient.css';
import PatientFormRequest from './GetPatient';
import PatientInfoDetails from './PatientInfoDetails';
import PatientList from '../PatientList';
import CreatePatient from './CreatePatient';
import UpdatePatient from './UpdatePatient';
import DeletePatient from './DeletePatient';
import NoteHistory from './NoteHistory';
import CreateNote from './CreateNote';


const Patient = () => {

   const [patient, setPatient] = useState({
      family: "_empty"
   });

   return (
      <div className='patient' >
         {patient.family === "_empty" ?
            <>
               <CreatePatient setPatient={setPatient} />
               <PatientFormRequest setPatient={setPatient} />
               <PatientList />
            </>
            :
            <>
               <PatientInfoDetails patient={patient} />
               <DeletePatient patient={patient} />
               <UpdatePatient patient={patient} setPatient={setPatient} />
               <NoteHistory patient={patient} />
               <CreateNote patient={patient} />
            </>
         }
      </div>
   )
};

export default Patient;
