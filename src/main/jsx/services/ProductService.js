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

export const getCommentList = (productNo) => {
    return new Promise((resolve, reject) => {
        axios.get("/product/comment-list/"+productNo)
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
export const writeComment = (comment) => {
    return new Promise((resolve, reject) => {
        axios.post("/product/write-comment", comment)
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

export const writeReComment = (comment) => {
    return new Promise((resolve, reject) => {
        axios.post("/product/write-recomment",comment)
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
export const updateComment = (comment) => {
    return new Promise((resolve, reject) => {
        axios.put("/product/update-comment",comment)
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
export const deleteComment = (commentNo) => {
    return new Promise((resolve, reject) => {
        axios.delete("/product/delete-comment",commentNo)
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
