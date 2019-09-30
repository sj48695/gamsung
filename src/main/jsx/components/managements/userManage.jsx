import React, {Component} from 'react';

import axios from 'axios';

import * as ManageService from '../../services/ManageService.js';

class UserManage extends Component {
    

    constructor(props) {
        super(props);

        this.state = {
           defaultChecked: ''
        }
 
        
        this.handleActive = this.handleActive.bind(this);
        this.handleBlackList = this.handleBlackList.bind(this);
    }
    handleActive(e) {
        const {members} = this.props;
        //const { key } = this;

        const id = e.target.value;
        const member = members.find(m => m.id === id);
        ActiveManage(member)
                     .then(result =>{
                         result.data;
                         ManageService.getUserList();
                     })
                     .catch(err =>{
                         console.log(err);
                     })
    }

    handleBlackList(e) {
        const {members} = this.props;

        const id = e.target.value;
        const member = members.find(m => m.id === id);
        BlackListManage(member)
                     .then(result =>{
                         result.data;
                         ManageService.getUserList();
                     })
                     .catch(err =>{
                         console.log(err);
                     })
    }


    render(){

        const { members } = this.props;

        const UserRaws = members.map( member => {
            return(
                <tr key={ member.id }>
                    <td>{ member.id }</td>
                    <td>{ member.nickname }</td>
                    <td>{ member.regDate }</td>
                    <td>
                        <label className="switch">
                            <input type="checkbox" defaultChecked={member.active} value={member.id} onChange={this.handleActive}></input>
                            <span className="slider round"></span>
                        </label>
                    </td>
                    <td>
                        <label className="switch">
                            <input type="checkbox" defaultChecked={member.blackList} value={member.id} onChange={this.handleBlackList}></input>
                            <span className="slider round"></span>
                        </label>
                    </td>
                </tr>
            )
        })

        return(
            <div>
                <table>
                    <thead>
                        <tr>
                            <td>아이디</td>
                            <td>별명</td>
                            <td>가입날짜</td>
                            <td>상태</td>
                            <td>블랙리스트</td>
                        </tr>
                    </thead>
                    <tbody>
                        {UserRaws}
                    </tbody>
                </table>
            </div>
        )
    }
}
export default UserManage;

export const ActiveManage = (member) =>{
    return new Promise( (resolve, reject) => {
        axios.post("delete",  member)
             .then((result) =>{
                 const data = result.data;
                 resolve(data);
                 return;
             })
             .catch((err)=>{
                 reject(err.message);
                 return;
             })
    })
}

export const BlackListManage = (member) =>{
    return new Promise( (resolve, reject) => {
        axios.post("blacklist",  member)
             .then((result) =>{
                 const data = result.data;
                 resolve(data);
                 return;
             })
             .catch((err)=>{
                 reject(err.message);
                 return;
             })
    })
}