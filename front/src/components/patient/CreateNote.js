import React, { useState } from 'react';
import axios from 'axios';

const CreateNote = (props) => {

   const patId = props.patient.id;

   const [state, setState] = useState({
      patId: props.patient.id,
      e: ""
   });

   const handleChange = (e) => {
      const value = e.target.value;
      setState({ ...state, [e.target.name]: value });
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      const userData = { ...state };
      axios.post("http://localhost:8082/patHistory/add", null, { params: userData })
         .then((response) => {
            
         })
         .catch((err, response) => {
            window.alert("Error updating patient: " + err.response.data);
         });
   };

   return (
      <div className='create-note' >
         <h4>Update a patient</h4>
         <form onSubmit={handleSubmit} >
            <label for="note">Add Comments:</label>
            <textarea id="note" name="note" rows="12" cols="50" onChange={handleChange}></textarea>
            <input type="submit" />
         </form>
      </div>
   )

};

export default CreateNote;