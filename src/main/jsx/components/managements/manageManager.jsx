import React, { Component } from 'react';

import Modal from 'react-modal';
import UserManage from './userManage.jsx';

import * as ManageService from '../../services/ManageService.js';
import { domainToASCII } from 'url';

class ManageManager extends Component {

    constructor(props) {
        super(props);
        this.state = {
            members: []
        }
    }

    getUserList() {
        const promise = ManageService.getUserList();
        promise.then(members => {
            this.setState({
                members:members
            });

        }).catch(err => {
            console.log(err);
            return;
        })
    }

    componentDidMount() {
        this.getUserList();
    }


    render(){
        const { members } = this.state;
        return(
            <div>
                <UserManage members = { members }  />
            </div>
        )
    }
}
export default ManageManager;