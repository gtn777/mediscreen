import React, { useState, useEffect } from 'react';
import axios from 'axios'

const NoteHistory = (props) => {
   const [notes, setNotes] = useState([]);
   const patient = props.patient;

   useEffect(() => {
      axios.get("http://localhost:8082/patHistory/all", { params: { patId: patient.patId } })
         .then((res) => {
            setNotes(res.data.noteDtos);
         })
         .catch((err) => console.info(err.response.data));
   }, []);

   const handleClickDelete = (e, noteToDeleteId) => {
      e.preventDefault();
      axios.post("http://localhost:8082/patHistory/delete", null, { params: { id: noteToDeleteId } })
         .then((response) => {
            console.log(response);
            setNotes((notes) => notes.filter((note) => note.id !== noteToDeleteId));
         })
         .catch((err) => {
            window.alert("Error deleting note: " + err.response.data);
         });
   };

   return (
      <div className="note-history" >
         <h4>Patient note history: </h4>
         {notes.map((note) =>
            <div key={note.id + patient.patId} className="note-history-item" >
               {/* <p>
                  {note.id + " " + note.patId}
               </p> */}
               <p>
                  {note.note}
               </p>
               <button onClick={(e) => handleClickDelete(e, note.id)} >Delete</button>
            </div>
         )}
      </div>
   );
};

export default NoteHistory;