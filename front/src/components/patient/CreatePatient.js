import React, { useState } from "react";
import axios from "axios";
import './patient.css';


const CreatePatient = (props) => {

   const setPatient = props.setPatient;

   const [state, setState] = useState({
      family: "",
      given: "",
      dob: "",
      sex: "",
      address: "",
      phone: ""
   });


   const handleChange = (e) => {
      const value = e.target.value;
      setState({
         ...state,
         [e.target.name]: value
      });
   };


   const handleSubmit = (e) => {
      e.preventDefault();
      const userData = {...state};
      if (userData.family < 2) window.alert("Last name bad entry.")
      else if (userData.given < 2) window.alert("First name bad entry.")
      else if (userData.sex !== "M" && userData.sex !== "F") window.alert("Gender must be selected")
      else if (userData.dob < 8) window.alert("Date of birth must be entried.")
      else {
         axios.post("http://localhost:8081/patient/add", null, { params: { ...userData } })
            .then((response) => {
               setPatient(response.data);
            })
            .catch((err, response) => {
               window.alert("Error creating patient: " + err.response.data);
            });
      };
   };


   return (
      <div className="create-patient" >
         <h4>Create a patient</h4>
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
                  onChange={handleChange}
               />
               Male
            </label>
            <label>
               <input
                  type="radio"
                  value="F"
                  name="sex"
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

export default CreatePatient;