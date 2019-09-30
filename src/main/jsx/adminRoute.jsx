import React, { Component } from 'react';
import { BrowserRouter } from 'react-router-dom';

import App from './components/App.jsx'

class RouteRoot extends Component {
    render(){
        return(
            <BrowserRouter>
                <App />
            </BrowserRouter>
        )
    };
}

export default RouteRoot;