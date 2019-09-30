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
        <div className="container">
            <Header />
            <div className="gide-main">
                <ManageManager />
            </div>
        </div>
        )
    }
}

export default App;