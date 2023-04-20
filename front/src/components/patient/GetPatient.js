import React, { useState } from "react";
import axios from "axios";
import './patient.css';

const GetPatient = (props) => {
   console.info("- - PatientFormRequest 1");

   const setPatient = props.setPatient;

   const [state, setState] = useState({ family: "", given: "" });

   const handleChange = (e) => {
      const value = e.target.value;
      setState({
         ...state,
         [e.target.name]: value
      });
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      const userData = {
         family: state.family,
         given: state.given
      };
      console.info(userData.given + " "+state.given);
      if (userData.family < 1) window.alert("Last name bad entry.")
      else {
         axios.get("http://localhost:8081/patient/name/" + userData.family,{ params: userData })
            .then((response) => {
               setPatient(response.data);
            })
            .catch((err) => window.alert("Error accessing patient datas: " + err.response.data));
      };
   };

   return (
      <div className="get-patient" >
         <h4>Request for patient</h4>
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
            <button type="submit">Send</button>
         </form>
      </div>
   );
};

export default GetPatient;