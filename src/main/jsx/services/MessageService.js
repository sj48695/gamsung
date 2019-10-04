import * as axios from 'axios';

export const getChatList= () => {
    return new Promise((resolve, reject) => {
        axios.get(`/member/chattingList`)
            .then((result) => {
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