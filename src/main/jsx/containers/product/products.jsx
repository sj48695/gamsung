import React from 'react';
import ReactDOM from 'react-dom';
import ProductList from '../../components/product/productList.jsx';


// DOM의 내용을 메인 컴포넌트로 변경합니다.
ReactDOM.render(
    <ProductList />,
    document.getElementById('products')
);