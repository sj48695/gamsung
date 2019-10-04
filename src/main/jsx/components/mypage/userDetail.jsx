import React, {Component} from 'react';

import UserDetailData from './userDetailData.jsx';

import * as UserDataService from '../../services/UserDataService.js'

class UserDetail extends Component{
    constructor(props){
        super(props);
        this.state={
            member: {}
        }
    }
    getUserDetail(){
        const promise = UserDataService.getUserDetail();
        promise.then(member => {
            this.setState({
                member:member
            });

        }).catch(err => {
            console.log(err);
            return;
        });
    }

    componentDidMount(){
        this.getUserDetail();
    }


    render(){
        const {member} = this.state;
        return(
            <div>
                <UserDetailData member={member} />
            </div>
        )
    }

}
export default UserDetail;