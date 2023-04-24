import React, { useState, useEffect } from 'react';
import axios from 'axios';
import NoteHistory from './NoteHistory';
import CreateNote from './CreateNote';
import UpdateNote from './UpdateNote';
import './note.css'
import Assessment from './Assessment';

const Note = (props) => {
   const [notes, setNotes] = useState([]);
   const [noteToUpdate, setNoteToUpdate] = useState({ id: "", note: "" });
   const patient = props.patient;

   useEffect(() => {
      axios.get("http://localhost:8082/patHistory/all", { params: { patId: patient.patId } })
         .then((res) => {
            console.log(res.data.noteDtos);
            setNotes(res.data.noteDtos);
         })
         .catch((err) => console.info(err.response.data));
   }, []);

   const updateNote = (id, note) => {
      setNoteToUpdate({ id: id, note: note });
   };

   return (
      <div className="note" >
         <Assessment patient={patient} notes={notes} />
         <NoteHistory patient={patient} notes={notes} setNotes={setNotes} updateNote={updateNote} />
         {(patient.patId === null || noteToUpdate.id !== "") ? null : <CreateNote patient={patient} notes={notes} setNotes={setNotes} />}
         {noteToUpdate.id === "" ? null : <UpdateNote noteToUpdate={noteToUpdate} notes={notes} setNoteToUpdate={setNoteToUpdate}  setNotes={setNotes} />}
      </div>
   )
};

export default Note;