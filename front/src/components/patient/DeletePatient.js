import axios from 'axios';
import React from 'react';

const DeletePatient = (props) => {

   const patient = props.patient;

   const handleClickDeletePatient = () => {      
      const userData = { family: patient.family, given: patient.given };
      axios.post("http://localhost:8081/patient/delete", null, { params: userData })
         .then((response) => {
            console.log(response);
         })
         .catch((err, response) => {
            window.alert("Error deleting patient: " + err.response.data);
         });
   };

   return (
      <div className="delete-patient" >
         <h4>Delete patient:</h4>
         <button onClick={handleClickDeletePatient} >Delete Here</button>
      </div>
   )
};

export default DeletePatient