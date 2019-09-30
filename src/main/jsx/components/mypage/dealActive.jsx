import React, { Component, Fragment } from 'react';

class DealActive extends Component {
    constructor(props) {
        super(props);
        const { active } = this.props;
        this.state = {
            active: active
        }

        this.deal = this.deal.bind(this);
        this.delete = this.delete.bind(this);
    }
    deal(event) {//수락 -> 수락취소
        const active = event.target.id;
        const { dealNo, onUpdateDealActive } = this.props;
        onUpdateDealActive(dealNo, active);
        this.setState({
            active: active
        });
    }
    delete() {
        const { dealNo, onDeleteDeal } = this.props;
        onDeleteDeal(dealNo);
    }
    render() {

        const { active } = this.state;
        const { type, dealNo } = this.props;
        if (type == "myProducts") {
            if (active == '거래요청') {
                return (
                    <Fragment>
                        <div className="deal_button col" >
                            <span id="거래수락" onClick={this.deal} >수락</span>
                        </div>
                        <div className="deal_button ml-1 col" >
                            <span id="거래거절" onClick={this.deal} >거절</span>
                        </div>
                    </Fragment>
                );
            } else if (active == '거래수락') {//거래 수락
                return (
                    <div className="deal_button col" >
                        <span id="거래요청" onClick={this.deal}>수락취소</span>
                    </div>
                );
            } else if (active == '거래거절') {//거래 거절
                return (
                    <div className="deal_button col" >
                        <span id="거래요청" onClick={this.deal}>거절취소</span>
                    </div>
                );
            } else if (active == '결제완료') {
                return (
                    <div className="col" >
                        <span>구매확정대기중</span>
                    </div>
                );
            } else if (active == '구매확정') {
                return (
                    <div className="deal_button col" >
                        <span id="판매완료" onClick={this.deal}>판매확정</span>
                    </div>
                );
            } else if (active == '판매완료') {
                return (
                    <div className="col" >
                        <span>판매완료</span>
                    </div>
                );
            } else {
                return (<div>null</div>);
            }
        }
        else if (type == "myRequestProducts") {
            if (active == '거래요청') {
                return (
                    <Fragment>
                        <div className="col" >
                            <span>수락대기중</span>
                        </div>
                        <div className="deal_button col" >
                            <span id="삭제" onClick={this.delete}>삭제</span>
                        </div>
                    </Fragment>
                );
            } else if (active == '거래거절') {//거래 거절
                return (
                    <Fragment>
                        <div className="col" >
                            <span>거래가 거절되었습니다.</span>
                        </div>
                        <div className="deal_button col" >
                            <span id="삭제" onClick={this.delete}>삭제</span>
                        </div>
                    </Fragment>
                );
            } else if (active == '거래수락') {
                return (
                    <div className="deal_button col" >
                        <a id="결제" href={`/deal/order/${dealNo}`}>결제</a>
                    </div>
                );
            } else if (active == '결제완료') {
                return (
                    <div className="deal_button col" >
                        <span id="구매확정" onClick={this.deal}>구매확정</span>
                    </div>
                );
            } else if (active == '구매확정') {
                return (
                    <div className="col" >
                        <span>판매확정대기중</span>
                    </div>
                );
            } else if (active == '판매완료') {
                return (
                    <Fragment>
                        <div className="col" >
                            <span>구매완료</span>
                        </div>
                        <div className="deal_button col" >
                            <a id="결제" href="#">리뷰등록</a>
                        </div>
                    </Fragment>
                );
            } else {
                return (<div>null</div>);
            }
        } else {
            return (<div>null</div>);

        }


    }
}

export default DealActive;
