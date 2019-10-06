import * as axios from 'axios';
export const getProductList = () => {
    return new Promise((resolve, reject) => {
        axios.get("/member/mypage/products")
            .then((result) => {
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

export const getRequestProductList = () => {
    return new Promise((resolve, reject) => {
        axios.get("/member/mypage/requestProducts")
            .then((result) => {
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
