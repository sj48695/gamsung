import * as axios from 'axios';

export const getUserDetail = () =>{
    return new Promise( (resolve, reject) =>{
        axios.get("/member/mypage/userData")
             .then((result) =>{
                 const data = result.data;
                 resolve(data);
                 return;
             })
             .catch((err)=>{
                 console.log(err);
                 reject(err.message);
                 return;
             })
    })
}

export const UpdateUser = (member) =>{
    return new Promise((resolve, reject) =>{
        axios.post("/member/mypage/userUpdate", member)
             .then((result) =>{
                 const data = result.data;
                 resolve(data);
                 return;
             })
             .catch((err) =>{
                 reject(err.message);
                 return;
             })
    })
}