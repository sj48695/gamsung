import * as axios from 'axios';

export const updateDealActive = (dealNo, active) => {
    return new Promise((resolve, reject) => {
        axios.put(`/deal/accept/${dealNo}/${active}`)
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

export const deleteDeal = (dealNo) => {
    return new Promise((resolve, reject) => {
        axios.delete(`/deal/delete/${dealNo}`)
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
