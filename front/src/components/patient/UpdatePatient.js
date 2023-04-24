import React, { useState } from "react";
import axios from "axios";
import './patient.css';

const UpdatePatient = (props) => {
   const setPatient = props.setPatient;
   const patient = props.patient;
   const [state, setState] = useState({
      ...patient
   });

   const handleChange = (e) => {
      const value = e.target.value;
      setState({ ...state, [e.target.name]: value });
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      const userData = { ...state };
      if (userData.family < 2) window.alert("Last name bad entry.")
      else if (userData.given < 2) window.alert("First name bad entry.")
      else if (userData.sex !== "M" && userData.sex !== "F") window.alert("Gender must be selected")
      else if (userData.dob < 8) window.alert("Date of birth must be entried.")
      else {
         axios.post("http://localhost:8081/patient/update", null,{ params: userData })
            .then((response) => {
               console.log(response.data);
               setPatient(response.data);
            })
            .catch((err) => {
               window.alert("Error updating patient: " + err);
            });
      };
   };

   return (
      <div className="update-patient" >
         <h4>Update a patient</h4>
         <form onSubmit={handleSubmit}>
            <label htmlFor="given">
               First Name:
               <input
                  type="text"
                  name="given"
                  value={state.given}
                  onChange={handleChange}
               />
            </label>
            <label htmlFor="family">
               Last Name:
               <input
                  type="text"
                  name="family"
                  value={state.family}
                  onChange={handleChange}
               />
            </label>
            <label htmlFor="dob">
               Date Of Birth:
               <input
                  type="date"
                  name="dob"
                  value={state.dob}
                  onChange={handleChange}
               />
            </label>
            <label>
               <input
                  type="radio"
                  value="M"
                  name="sex"
                  checked={state.sex === "M"}
                  onChange={handleChange}
               />
               Male
            </label>
            <label>
               <input
                  type="radio"
                  value="F"
                  name="sex"
                  checked={state.sex === "F"}
                  onChange={handleChange}
               />
               Female
            </label>
            <label htmlFor="address">
               Address:
               <input
                  type="text"
                  name="address"
                  value={state.address}
                  onChange={handleChange}
               />
            </label>
            <label htmlFor="phone">
               Telephone:
               <input
                  type="text"
                  name="phone"
                  value={state.phone}
                  onChange={handleChange}
               />
            </label>
            <button type="submit">Send</button>
         </form>
      </div>
   )
};

export default UpdatePatient;