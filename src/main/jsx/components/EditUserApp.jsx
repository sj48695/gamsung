import React, {Component} from 'react';
import {Route, Link} from 'react-router-dom';

import Confirmpwd from './mypage/confirmpwd.jsx'
import UserDetail from './mypage/userDetail.jsx';

class EditUserApp extends Component {
    render(){
        return(
            <div>
                
                <Route exact path="/member/mypage" title="confirm" component={Confirmpwd}/>
                <Route path="/member/editUser" title="editUser" component={UserDetail}/>
            </div>
        )
    }
}
export default EditUserApp;