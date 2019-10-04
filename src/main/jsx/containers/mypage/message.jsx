import React from 'react';
import ReactDOM from 'react-dom';
import Message from '../../components/mypage/message.jsx';


// DOM의 내용을 메인 컴포넌트로 변경합니다.
ReactDOM.render(
    <Message />,
    document.getElementById('message')
);