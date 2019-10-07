import React,{Component} from 'react';
import { Route } from 'react-router-dom';

import ManageManager from './managements/manageManager.jsx';
import ReportManage from './reports/reportManage.jsx';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render(){
        return(
        <div className="container">
            <div className="gide-main">
                <ManageManager />
            </div>
        </div>
        )
    }
}

export default App;