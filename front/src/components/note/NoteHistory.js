import React from 'react';
import axios from 'axios';
import './note.css';

const NoteHistory = (props) => {
   const { notes, setNotes, patient, updateNote } = props;

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

   const handleClickUpdate = (e, id, note) => {
      e.preventDefault();
      updateNote(id, note);
   };

   return (
      <div className="note-history" >
         <h4>Patient note history: </h4>
         {notes.map((note) =>
            <div key={note.id + patient.patId} className="note-history-item" >
               <pre className="note-content" >
                  {note.note}
               </pre>
               <button onClick={(e) => handleClickDelete(e, note.id)} >Delete</button>
               <button onClick={(e) => handleClickUpdate(e, note.id, note.note)} >Update</button>
            </div>
         )}
      </div>
   );
};

export default NoteHistory;