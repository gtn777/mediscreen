import axios from "axios";
import React, { useEffect, useState } from "react";
import './home.css';

const PatientList = () => {   
   const [users, setUsers] = useState([]);

   useEffect(() => {
      axios.get('http://localhost:8081/patient/all')
         .then((res) => {
            setUsers(res.data);
         })
         .catch((err) => console.info(err.message));
   },[]);

   return (
      <div className="patient-list" >
         <h4>All existing patient: </h4>
         {users.map((user) =>
            <div key={user.given + user.family} className="patient-list-item" >
               <p>
                  {user.given + " " + user.family}
               </p>
               <p>
                  {user.dob}
               </p>
            </div>
         )}
      </div>
   );
};

export default PatientList;

