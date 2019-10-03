import React, {Component} from 'react';
import axios from 'axios';
import {Link} from 'react-router-dom';

class Confirmpwd extends Component {
    constructor(props){
        super(props);

        this.state = {
            value: '',

        }
        this.showuserdata = React.createRef();
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleOpened = this.handleOpened.bind(this);

    }

    handleChange(e){
        this.setState({value: e.target.value})
    }

    handleOpened(){
       this.showuserdata.current.click();
    }

    handleSubmit(e){
        e.preventDefault();

        const pwd = this.state.value;
        const myThis = this;
        axios.post("/member/mypage/confirmpwd", {"pwd":pwd})
             .then((result) =>{
                 if(result.data === "success"){
                     myThis.handleOpened();                    
                    console.log("success!!")
                } else {
                    alert("비밀번호가 맞지 않습니다.");
                }
             })
             .catch((err)=>{
                 console.log(err.message);
                 return;
             })    
    }


    render(){
        const { value } = this.state;

                return(                    
                    <div>
                        <form onSubmit={this.handleSubmit} method="POST">
                            <input name="password" id="inpwd" type="password" value={value} onChange={this.handleChange} />
                            <button type="submit">button</button>
                        </form>
                        <div><Link to="/member/mypage/editUser" ref={this.showuserdata}></Link></div>
                    </div>
                )
        
    }
}

export default Confirmpwd;
