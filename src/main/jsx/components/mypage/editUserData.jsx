import React, {Component} from 'react';
import axios from 'axios';
import Modal from 'react-modal';

import ManageManager from '../managements/manageManager.jsx'

import '../../styles/management.scss';

class EditUserData extends Component {
    constructor(props){
        super(props);

        this.state = {
            value: '',
            isOpenedUserData: false
        }
        
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    handleChange(e){
        this.setState({value: e.target.value})
    }


    handleSubmit(e){
        e.preventDefault();

        const pwd = this.state.value;
        const mythis = this;
        axios.post("/member/mypage/confirmpwd", {"pwd":pwd})
             .then((result) =>{
                 const data = result.data;
                 mythis.setState({
                    isOpenedUserData: true
                })
             })
             .catch((err)=>{
                 console.log(err.message);
                 return;
             })

        
    }

    render(){
        const { value,isOpenedUserData} = this.state;
        return(
            <div>

                <form onSubmit={this.handleSubmit} method="POST">
                    <input name="password" id="inpwd" type="password" value={value} onChange={this.handleChange} />
                    <button type="submit">button</button>
                </form>
                <Modal isOpen={isOpenedUserData} ariaHideApp={false} className="modal">
                <h2>test text</h2>
                </Modal>


            </div>
        )
    }
}

export default EditUserData;
