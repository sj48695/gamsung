import React,{Component} from 'react';

import Header from './header.js';
import ManageManager from './managements/manageManager.jsx';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render(){
        return(
        <div className="gide-main">
            <Header />
            <div className="gide-body">
                <ManageManager />
            </div>
        </div>
        )
    }
}

export default App;