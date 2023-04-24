import React, { useState } from 'react';
import axios from 'axios';
import './note.css'


const UpdateNote = (props) => {
   const { noteToUpdate, setNotes, setNoteToUpdate } = props;
   const [state, setState] = useState({ ...noteToUpdate });

   const handleChange = (e) => {
      e.preventDefault();
      const value = e.target.value;
      setState({ ...state, [e.target.name]: value });
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      const data = { ...state };
      axios.post("http://localhost:8082/patHistory/update", null, { params: data })
         .then((response) => {
            console.log(response.data);
            setNotes((n) => n.map((note) => {
               if (note.id === state.id) {note.note = state.note};
               return note;
            }));
            setNoteToUpdate({id:"", note:""});
         })
         .catch((err, response) => {
            window.alert("Error updating note: " + err.response.data);
         });
   };

   return (
      <div className='create-note' >
         <form onSubmit={handleSubmit} >
         <h4>Update note:</h4>
            <textarea id="note" name="note" rows="8" cols="40" onChange={handleChange} value={state.note}></textarea>
            <input type="submit" />
         </form>
      </div>
   )
};

export default UpdateNote;