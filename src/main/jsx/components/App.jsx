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
        <div className="container">
            <Header />
            <div></div>
        </div>
        )
    }
}

export default App;