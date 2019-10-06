import React from 'react';
import ReactDOM from 'react-dom';
import CommentList from '../../components/product/commentList.jsx';


// DOM의 내용을 메인 컴포넌트로 변경합니다.
ReactDOM.render(
    <CommentList />,
    document.getElementById('comment')
);