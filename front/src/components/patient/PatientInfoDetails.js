import React, { useState } from "react";
import './patient.css';

const PatientInfoDetails = ({ patient }) => {
   console.info("- - PatientInfoDetails 1");

   const { family, given, sex, dob, address, phone, patId } = patient;

   return (
      <div className="patient-info-details" >
         {console.info("- - PatientInfoDetails 2")}
         <p>First name: {given}</p>
         <p>Last name: {family}</p>
         <p>Genre: {sex}</p>
         <p>Date of birth: {dob}</p>
         <p>Address: {address}</p>
         <p>Phone: {phone}</p>
         <p>Patid: {patId}</p>
      </div>)
}

export default PatientInfoDetails;