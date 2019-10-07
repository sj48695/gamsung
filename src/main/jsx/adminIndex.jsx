import React, { Component } from 'react';
import ReactDom from 'react-dom';

import App from './components/App.jsx'

import './styles/management.scss';

ReactDom.render(
    <App />,
    document.getElementById("manage")
)