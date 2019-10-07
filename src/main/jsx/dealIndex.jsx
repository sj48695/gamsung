import React from 'react';
import ReactDom from 'react-dom';

import DealApp from './components/dealApp.jsx'
import './styles/management.scss';

ReactDom.render(
    <DealApp />,
    document.getElementById("deal")
)