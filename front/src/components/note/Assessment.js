import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Assessment = (props) => {
   const { patient, notes } = props;
   const [reporting, setReporting] = useState("");

   useEffect(() => {
      axios.post("http://localhost:8080/assess/id",null, { params: { patId: patient.patId } })
         .then((res) => {
            console.log(res.data);
            setReporting(res.data);
         })
         .catch((err) => console.info(err.response.data));
   }, [notes]);

   return (
      <div className='assessment' >
         <h4>{reporting}</h4>
      </div>
   )
};


export default Assessment;