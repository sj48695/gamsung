import React, { Component } from 'react';
import ReactDom from 'react-dom';

import RouteRoot from './adminRoute.jsx'

import './styles/management.scss';

ReactDom.render(
    <RouteRoot />,
    document.getElementById("react")
)