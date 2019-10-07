import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import * as UserDataService from '../../services/UserDataService.js'

class UserDetailData extends Component{
    constructor(props){
        super(props);
        this.state={
            id:'',
            nickname: '',
            phone:'',
            postcode:'',
            roadAddr:'',
            jibunAddr:'',
            detailAddr:'',
            extraAddr:''
        }

        this.successedit = React.createRef();
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange1 = this.handleChange1.bind(this);
        this.handleChange2 = this.handleChange2.bind(this);
        this.handleChange3 = this.handleChange3.bind(this);
        this.handleChange4 = this.handleChange4.bind(this);
        this.handleChange5 = this.handleChange5.bind(this);
        this.handleChange6 = this.handleChange6.bind(this);
        this.handleChange7 = this.handleChange7.bind(this);
    }

    
    handleOpened(){
        this.successedit.current.click();
     }

    handleChange1(e){
        this.setState({nickname: e.target.value});
    }
    handleChange2(e){
        this.setState({phone:e.target.value});
    }
    handleChange3(e){
        this.setState({postcode:e.target.value});
    }
    handleChange4(e){
        this.setState({roadAddr:e.target.value});
    }
    handleChange5(e){
        this.setState({jibunAddr:e.target.value});
    }
    handleChange6(e){
        this.setState({detailAddr:e.target.value});
    }
    handleChange7(e){
        this.setState({extraAddr:e.target.value});
    }

    handleSubmit(e){
        const mythis = this;
        e.preventDefault();
        const {id,nickname, phone, postcode, roadAddr, jibunAddr, detailAddr, extraAddr } = this.state;
        var member={
            id: id,
            nickname: nickname,
            phone:phone,
            postcode:postcode,
            roadAddr:roadAddr,
            jibunAddr:jibunAddr,
            detailAddr:detailAddr,
            extraAddr:extraAddr
        }
        const promise = UserDataService.UpdateUser(member);
        promise.then(result =>{
                    if(result === "success"){
                        //mythis.handleOpened();
                        location.href="/member/mypage"
                        alert("수정되었습니다.")
                    }
                })
                .catch(err =>{
                    console.log(err);
                    return;
                })
    }

    componentDidMount(){
        const promise = UserDataService.getUserDetail();
        promise.then(member => {
            this.setState({
                id:member.id,
                nickname: member.nickname,
                phone:member.phone,
                postcode:member.postcode,
                roadAddr:member.roadAddr,
                jibunAddr:member.jibunAddr,
                detailAddr:member.detailAddr,
                extraAddr:member.extraAddr
            });

        }).catch(err => {
            console.log(err);
            return;
        });
    }

    render(){
        const {member} = this.props;
        const {id,nickname, phone, postcode, roadAddr, jibunAddr, detailAddr, extraAddr} = this.state;
            return(
                <div key={member.id} className="row justify-content-center py-5 my-5">
                    <div className="checkout_form_container py-5 my-5 w-50">
                        <form onSubmit={this.handleSubmit} method="POST">
                            <div className="row justify-content-between">
                                <div className="col-6">
                                    <div className="form-group">
                                        <label htmlFor="id">id</label>
                                        <input className="checkout_input" type="hidden" name="id" value={id}/><br />{member.id}
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="nickname">nickname</label>
                                        <input className="checkout_input" type="text" name="nickname"  value={nickname}  onChange={this.handleChange1} />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="phone">phone</label>
                                        <input className="checkout_input" type="text" name="phone" value={phone} onChange={this.handleChange2}/>
                                    </div>
                                </div>
                                <div className="col-6">
                                    <label htmlFor="postcode" >Zipcode*</label> 
                                    <div className="row">
                                            <div className="form-group col-6">
                                                <input type="text" className="checkout_input" value={postcode} onChange={this.handleChange3}
                                                    id="postcode"  name="postcode" placeholder="우편번호" required="required" />
                                            </div>
                                            
                                    </div>
                                    <div className="form-group">
                                            <label htmlFor="roadAddress">RoadAddress*</label> 
                                            <input type="text" className="checkout_input" value={roadAddr} onChange={this.handleChange4}
                                                id="roadAddress" name="roadAddr" placeholder="도로명주소" />
                                        </div>
                                        <div className="form-group">
                                            <label htmlFor="jibunAddr">Jibun*</label> 
                                            <input type="text" className="checkout_input" value={jibunAddr} onChange={this.handleChange5}
                                                id="jibunAddress" name="jibunAddr" placeholder="지번주소" />
                                        </div>
                                        <div className="form-group row">
                                            <div className="form-group col-6">
                                                <label htmlFor="detailAddr">Detail*</label> 
                                                <input type="text" className="checkout_input" value={detailAddr} onChange={this.handleChange6}
                                                    id="detailAddress" name="detailAddr" placeholder="상세주소" />
                                            </div>
                                            <div className="form-group col-6">
                                                <label htmlFor="address">Extra*</label> 
                                                <input type="text" className="checkout_input" value={extraAddr} onChange={this.handleChange7}
                                                    id="extraAddress" name="extraAddr" placeholder="참고항목" />
                                            </div>
                                    </div>
                                </div>
                            </div>
                            <div className="row justify-content-center">
					            <button className="newsletter_button col-5 mt-0" id="registerBtn" type="submit"><span>수정완료</span></button>
				            </div>
                        </form>
                    </div>
                </div>
            )
    }

}
export default UserDetailData;