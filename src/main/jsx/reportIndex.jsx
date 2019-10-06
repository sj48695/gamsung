import React, { Component } from 'react';
import ReactDom from 'react-dom';

import ReportApp from './components/reportApp.jsx';


import './styles/management.scss';

ReactDom.render(
    <ReportApp />,
    document.getElementById("report")
)