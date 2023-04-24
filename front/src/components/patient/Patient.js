import React from 'react';
import './patient.css';
import PatientInfoDetails from './PatientInfoDetails';
import UpdatePatient from './UpdatePatient';
import DeletePatient from './DeletePatient';

const Patient = (props) => {
   const { patient, setPatient } = props;

   return (
      <div className='patient' >
         <PatientInfoDetails patient={patient} />
         <UpdatePatient patient={patient} setPatient={setPatient} />
         <DeletePatient patient={patient} />
      </div>
   )
};

export default Patient;
