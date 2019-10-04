import React, {Component} from 'react';
import {BrowserRouter, Link} from 'react-router-dom';

import EditUserApp from '../../components/EditUserApp.jsx';

class EditUserRoute extends Component{
    render(){
        return(
            <div>
                <div>test</div>
                <div>
                    <BrowserRouter>
                        <EditUserApp />
                    </BrowserRouter>
                </div>
            </div>

        )
    }
    
}
export default EditUserRoute;