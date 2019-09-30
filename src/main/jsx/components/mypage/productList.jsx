import React, { Component } from 'react';
import DealList from './dealList.jsx';

import dateFormat from 'dateformat';
import NumberFormat from 'react-number-format';

class MyProducts extends Component {

    render() {
        const { type, products, onUpdateDealActive, onDeleteDeal } = this.props;
        var productRows;
        if (products.length <= 0) {
            productRows = 
                <div className="align-items-center align-items-start col-12">
                    <div className="card-header mypage_info_col col-12" id='heading'>등록된 상품이 없습니다.</div>
                </div>
            
        }else {
            productRows = products.map(product => {
                return (
                    <div className="align-items-center align-items-start col-12" key={product.productNo}>

                        <div className="card-header col-12" id={'heading' + product.productNo}>
                            <div className="btn row align-items-center" data-toggle="collapse" data-target={'#collapse' + product.productNo}
                                aria-expanded="true" aria-controls={`collapse${product.productNo}`} style={{ display: 'flex' }}>
                                {/* <!-- Name --> */}
                                <div className="mypage_item_product d-flex flex-row align-items-center">
                                    <div className="mypage_item_image">
                                        <div>
                                            <img src={'/files/product-files/' + product.file.saveFileName} />
                                        </div>
                                    </div>
                                    <div className="mypage_item_name_container">
                                        <div className="mypage_item_name">
                                            <a href={`/product/detail/${product.productNo}`} >{product.name}</a>
                                        </div>
                                    </div>
                                </div>
                                {/* <!-- price --> */}
                                <div className="mypage_item_price" ><NumberFormat value={product.price} displayType="text" thousandSeparator={true} />원</div>
                                {/* <!-- Quantity --> */}
                                <div className="mypage_item_quantity"><NumberFormat value={product.count} displayType="text" thousandSeparator={true} />개</div>
                                {/* <!-- regDate --> */}
                                <div className="mypage_item_date" >{dateFormat(product.regDate, 'yyyy-mm-dd')}</div>
                                {/* <!-- 상태 --> */}
                                <div className="mypage_item_state">
                                    {/* <select className="form-control" id="exampleFormControlSelect1">
                                        <option>판매중</option>
                                        <option>판매완료</option>
                                        <option>거래중</option>
                                        <option>거래완료</option>
                                        <option>거래중지</option>
                                    </select> */}
                                    {product.active}
                                </div>
                            </div>
                        </div>
                        <DealList
                            type={type}
                            deals={product.deals}
                            productNo={product.productNo}
                            onUpdateDealActive={onUpdateDealActive}
                            onDeleteDeal={onDeleteDeal} />
                    </div>
                );
            });
        } 
        return (
            <div>
                <div className="row">
                    <div className="col">
                        {/* <!-- Column Titles --> */}
                        <div className="mypage_info_columns clearfix">
                            <div className="mypage_info_col mypage_info_col_product"><b>상품명</b></div>
                            <div className="mypage_info_col mypage_info_col_price"><b>가격</b></div>
                            <div className="mypage_info_col mypage_info_col_quantity"><b>갯수</b></div>
                            <div className="mypage_info_col mypage_info_col_date"><b>날짜</b></div>
                            <div className="mypage_info_col mypage_info_col_state"><b>상태</b></div>
                        </div>
                    </div>
                </div>
                <div className="row cart_items_row accordion" id="accordionExample">
                    {productRows}
                </div>
            </div>
        );
    }
}

export default MyProducts;