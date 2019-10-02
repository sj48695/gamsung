import React,{Component} from 'react';
import { Route } from 'react-router-dom';

import Header from './header.js';
import ManageManager from './managements/manageManager.jsx';
import ReportManager from './reports/reportManager.jsx';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render(){
        return(
        <div className="container">
            <Header />
            <div className="gide-main">
                <Route exact path="/member/admin" title="managements" component={ManageManager}  />
                <Route  path="/member/report" title="reports" component={ReportManager} />
            </div>
        </div>
        )
    }
}

export default App;