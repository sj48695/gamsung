import React, {Component} from 'react';

import * as ManageService from '../../services/ManageService.js'

import DealManage from './dealManage.jsx'

class DealDetail extends Component{

    constructor(props) {
        super(props);
        this.state = {
            deals: []
        }

    }

    getUserList() {
        const promise = ManageService.getDealList();
        promise.then(result => {
            this.setState({
                deals: result.data.slice(0)
            });

        }).catch(err => {
            console.log(err);
            return;
        })
    }



    componentDidMount() {
        this.getDealList();
    }


    render(){
        return(
            <div>
            </div>
        )
    }
}
export default DealDetail;