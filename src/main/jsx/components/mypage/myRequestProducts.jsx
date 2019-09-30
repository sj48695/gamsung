import React, { Component } from 'react';
import ProductList from './productList.jsx';

import * as ProductService from '../../services/ProductService.js';
import * as DealService from '../../services/DealService.js';

class MyRequestProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            products: []
        }
        this.handleOnUpdateDealActive = this.handleOnUpdateDealActive.bind(this);
        this.handleOnDeleteDeal = this.handleOnDeleteDeal.bind(this);
    }

    getRequestProductList() {
        const promise = ProductService.getRequestProductList();
        promise.then(products => {
            this.setState({
                products: products
            });

        }).catch(err => {
            console.log(err);
            return;
        })
    }
    handleOnUpdateDealActive(dealNo, active) {
        const promise = DealService.updateDealActive(dealNo, active);
        promise.then(() => {
            console.log(active);
        }).catch(err => {
            console.log(err);
            return;
        })
    }

    handleOnDeleteDeal(dealNo) {
        const promise = DealService.deleteDeal(dealNo);
        promise.then(() => {
            alert("요청취소가 완료되었습니다.");
            this.getRequestProductList();
        }).catch(err => {
            console.log(err);
            return;
        })
    }


    componentDidMount() {
        this.getRequestProductList();
    }


    render() {
        const { products } = this.state;
        return (
            <ProductList type="myRequestProducts"
                products={products}
                onDeleteDeal={this.handleOnDeleteDeal}
                onUpdateDealActive={this.handleOnUpdateDealActive} />
        );
    }
}

export default MyRequestProducts;