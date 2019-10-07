import React, { Component, Fragment } from 'react';
import * as MessageService from '../../services/MessageService.js';

import dateFormat from 'dateformat';

class Message extends Component {

    constructor(props) {
        super(props);
        this.state = {
            messages: []
        }
        this.chatpop=this.chatpop.bind(this);
    }

    chatpop(e){
        console.log(e.target);
        //팝업창출력
        //width : 300px크기
        //height : 300px크기
        //top : 100px 위의 화면과 100px 차이해서 위치
        //left : 100px 왼쪽화면과 100px 차이해서 위치
        //툴바 X, 메뉴바 X, 스크롤바 X , 크기조절 X
        window.open('/member/chatting/'+e.target.title,'popName',
                    'width=700,height=900,top=100,left=100,toolbar=no,menubar=no,scrollbars=no,resizable=no,status=no');
    }

    getChatList() {
        const promise = MessageService.getChatList();
        promise.then((messages) => {
            this.setState({ messages: messages });
        }).catch((err) => {
            console.log(err);
            return;
        });

    }
    componentDidMount() {
        this.getChatList();
    }

    render() {
        const { messages } = this.state;

        var chatList;
        if (messages.length > 0) {
            chatList = messages.map((message) => {

                return (
                    <Fragment key={message.messageNo}>
                        <div className="row align-items-center">
                            <i className="icon pe-7s-paper-plane col-1" title={message.relativeId} onClick={this.chatpop}></i>
                            <div className="col-2">
                                <img className="profile m-0" style={{position:"initial"}} src={`/files/profile-files/${message.profile}`} />
                                <div>{message.relativeNick}</div>
                            </div>
                            <div className="col-5">
                                <div className="text-left">{message.contents}</div>
                            </div>
                            <div className="col-4">
                                <div>{dateFormat(message.sendDate,'yyyy-mm-dd hh:MM')}</div>
                            </div>
                        </div><hr/>
                    </Fragment>
                );

            });
        } else {
            chatList = <div>채팅창이 없습니다.</div>

        }
        return (

            <div>
                {/* <!-- Column Titles --> */}
                <div className="mypage_info_columns clearfix mb-3">
                    <div className="mypage_info_col col-3"><b>쪽지상대</b></div>
                    <div className="mypage_info_col col-5"><b>내용</b></div>
                    <div className="mypage_info_col col-4"><b>날짜</b></div>
                </div>
                {chatList}
            </div>
        );
    }
}

export default Message;