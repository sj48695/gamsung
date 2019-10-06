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
                    <div className="checkout_form_container py-5 my-5 row justify-content-center py-5 my-5" >
                        <form onSubmit={this.handleSubmit} method="POST">
                            <input className="checkout_input" name="password" id="inpwd" type="password" value={value} onChange={this.handleChange} placeholder="enter password..."/>
                            <button type="submit" className="newsletter_button col-5 mt-0 ml-5">인증</button>
                        </form>
                        <div><Link to="/member/editUser" ref={this.showuserdata}></Link></div>
                    </div>
                )
        
    }
}

export default Confirmpwd;
