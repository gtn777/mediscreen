import React, { useState } from 'react';
import axios from 'axios';
import './note.css';

const CreateNote = (props) => {
   const { setNotes, patient } = props;
   const [state, setState] = useState({
      patId: patient.patId,
      e: ""
   });

   const handleChange = (e) => {
      e.preventDefault();
      const value = e.target.value;
      setState({ ...state, [e.target.name]: value });
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      const userData = { ...state };
      axios.post("http://localhost:8082/patHistory/add", null, { params: userData })
         .then((response) => {
            console.log(response.data);
            setNotes((n) => [...n, response.data]);
            setState({
               patId: patient.patId,
               e: ""
            });
         })
         .catch((err, response) => {
            window.alert("Error creating note: " + err.response.data);
         });
   };

   return (
      <div className='create-note' >
         <form onSubmit={handleSubmit} >
            <h4>Create a new note for {patient.given + " " + patient.family}.</h4>
            <textarea id="e" name="e" rows="8" cols="40" onChange={handleChange}></textarea>
            <input type="submit" />
         </form>
      </div>
   )
};

export default CreateNote;