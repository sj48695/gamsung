import React, { Component } from 'react';
import ReactDom from 'react-dom';

import App from './components/App.jsx'

import './styles/menu.scss';

ReactDom.render(
    <App />,
    document.getElementById("react")
)