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

    componentDidMount() {
        this.getProductList();
    }


    render() {
        const { products } = this.state;
        return (
            <ProductList type="myProducts"
                products={products}
                onUpdateDealActive={this.handleOnUpdateDealActive} />
        );
    }
}

export default MyProducts;