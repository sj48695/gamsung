import React,{Component} from 'react';

import Header from './header.js';

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
            <div className="gide-body"></div>
        </div>
        )
    }
}

export default App;