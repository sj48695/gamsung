import * as axios from 'axios';

export const getUserList = () => {
    return new Promise( (resolve, reject) => {
        axios.get("/member/list")
             .then( (result) =>{
                 console.log(result.data);

                 const data = result.data;
                 resolve(data);
                 return;
             })
             .catch((err) => {
                 console.log(err);

                 reject(err.message);
                 return;
             })
    })
}

export const getReportList = () =>{
   return new Promise((resolve, reject)=>{
        axios.get("/report/list")
        .then( (result) => {
            const data = result.data;
            resolve(data);
        })
        .catch((err)=>{
            console.log(err);
            reject(err.message);
        })
    })
   
}

export const getDealList = () =>{
    return new Promise((resolve, reject)=>{
         axios.get("/deal/list")
         .then( (result) => {
             const data = result.data;
             resolve(data);
         })
         .catch((err)=>{
             console.log(err);
             reject(err.message);
         })
     })
    
 }
