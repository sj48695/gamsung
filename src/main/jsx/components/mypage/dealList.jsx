import React, { Component, Fragment } from 'react';
import NumberFormat from 'react-number-format';
import dateFormat from 'dateformat';

import DealActive from './dealActive.jsx';


class DealList extends Component {



    render() {
        const { type, deals, productNo, onUpdateDealActive, onDeleteDeal } = this.props;
        var dealRows;
        var buyerInfo;
        var buyerItem;
        if (type == 'myProducts') {
            buyerInfo = <div className="mypage_info_col col-1"><b>요청자</b></div>;
        } 


        if (deals.length <= 0) {
            dealRows =
                <div className="card-body row align-items-center px-0 mx-1" style={{ borderBottom: 'solid 1px' }}>
                    <div className="mypage_info_col col">요청받은 거래가 없습니다.</div>
                </div>
        } else {
            dealRows = deals.map(deal => {
                if (type == 'myProducts') {
                    buyerItem = <div className="mypage_info_col col-1" >{deal.buyer}</div>;
                } 
                return (
                    <div className="card-body align-items-center" key={deal.dealNo}>
                        <div className="row mb-3">
                            {buyerItem}
                            <div className="mypage_info_col col-2" >{deal.type}</div>
                            <div className="mypage_info_col col-2" ><NumberFormat value={deal.price} displayType="text" thousandSeparator={true} />원</div>
                            <div className="mypage_info_col col-2" ><NumberFormat value={deal.count} displayType="text" thousandSeparator={true} />개</div>
                            <div className="mypage_info_col col-2" >{dateFormat(deal.regDate, 'yyyy-mm-dd')}</div>
                            <div className="mypage_info_col col-3 row">
                                <DealActive type={type} dealNo={deal.dealNo} active={deal.active} onUpdateDealActive={onUpdateDealActive} onDeleteDeal={onDeleteDeal} />
                            </div>
                        </div>
                        <div className="row px-0" style={{ borderBottom: 'solid 1px' }} >
                            <div className="mypage_info_col text-left" > <b>
                                &emsp;＞ [</b> 수신자 : {deal.receiver}
                                &emsp;전화번호 : {deal.phone}
                                &emsp;주소 : {deal.postcode} {deal.roadAddr} {deal.detailAddr} <b>]</b>
                            </div>
                        </div>
                    </div>
                );
            });
        }
        return (
            <div id={'collapse' + productNo} className="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div className="card-body mypage_info_columns align-items-center">
                    {buyerInfo}
                    <div className="mypage_info_col col-2"><b>택배/직거래</b></div>
                    <div className="mypage_info_col col-2"><b>가격</b></div>
                    <div className="mypage_info_col col-2"><b>수량</b></div>
                    <div className="mypage_info_col col-2"><b>요청일</b></div>
                    <div className="mypage_info_col col-3"><b>거래</b></div>
                </div>
                {dealRows}
            </div>
        );
    }
}

export default DealList;
