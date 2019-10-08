import React, { Component } from 'react';
import ProductList from './productList.jsx';

import * as ProductService from '../../services/ProductService.js';
import * as DealService from '../../services/DealService.js';

class MyProducts extends Component {

    constructor(props) {
        super(props);
        this.state = {
            products: []
        }
        this.handleOnUpdateDealActive = this.handleOnUpdateDealActive.bind(this);
        this.handleOnDeleteProduct = this.handleOnDeleteProduct.bind(this);
    }

    getProductList() {
        const promise = ProductService.getMyProductList();
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

    handleOnDeleteProduct(productNo) {
        var ok = confirm('상품을 삭제하시겠습니까?');
        if (ok) {
            const promise = ProductService.deleteProduct(productNo);
            promise.then(() => {
                alert('상품을 삭제하였습니다.');
                this.componentDidUpdate
            }).catch(err => {
                console.log(err);
                return;
            })
        }
    }

    componentDidMount() {
        this.getProductList();
    }

    componentDidUpdate() {
        this.getProductList();
    }


    render() {
        const { products } = this.state;
        return (
            <ProductList type="myProducts"
                products={products}
                onUpdateDealActive={this.handleOnUpdateDealActive}
                onDeleteProduct={this.handleOnDeleteProduct} />
        );
    }
}

export default MyProducts;