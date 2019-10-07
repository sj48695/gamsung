import React, { Component } from 'react';

import * as ProductService from '../../services/ProductService.js';

import dateFormat from 'dateformat';
import NumberFormat from 'react-number-format';

class ProductList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            type: 'all',
            category: 'every',
            keyword: '',
            pageNo: 1
        }

        this.handleChangeSelectType = this.handleChangeSelectType.bind(this);
        this.handleChangeSelectCategory = this.handleChangeSelectCategory.bind(this);
        this.handleChangeInput = this.handleChangeInput.bind(this);
        this.handleSerch = this.handleSerch.bind(this);
    }


    getProductList(type, category, keyword, pageNo) {
        const promise = ProductService.getProductList(type, category, keyword, pageNo);
        promise.then(products => {
            this.setState({
                products: products
            });

        }).catch(err => {
            console.log(err);
            return;
        })
    }

    handleChangeSelectType(e) {
        this.setState({ type: e.target.value });
    }

    handleChangeSelectCategory(e) {
        this.setState({ category: e.target.value });
    }

    handleChangeInput(e) {
        this.setState({ keyword: e.target.value });
        this.handleSerch();
    }

    handleSerch() {
        const { type, category, keyword, pageNo } = this.state;
        this.getProductList(type, category, keyword, pageNo);
    }

    handleSubmit() {
        const { type, category, keyword, pageNo } = this.state;
        alert('value - ' + type+category+keyword+pageNo);
        this.getProductList(type, category, keyword, pageNo);
    }

    componentDidMount() {
        const { type, category, keyword, pageNo } = this.state;
        this.getProductList(type, category, keyword, pageNo);
    }

    render() {
        const { products, type, category, keyword, pageNo } = this.state;
        var productRows;
        if (products.length <= 0) {
            productRows =
                <div className="align-items-center align-items-start col-12">
                    <div className="card-header mypage_info_col col-12" id='heading'>등록된 상품이 없습니다.</div>
                </div>

        } else {
            productRows = products.map(product => {
                var productType;
                if (product.type == '흥정') {
                    productType =
                        <div className="product_extra product_sale">
                            <a href="categories">흥정</a>
                        </div>
                } else if (product.type == '일반') {
                    productType =
                        <div className="product_extra product_hot">
                            <a href="categories">일반</a>
                        </div>
                }

                return (
                    // <!-- Product -->
                    <div className="product" key={product.productNo}>
                        <div className="product_image" >
                            <img src={`/files/product-files/${product.file.saveFileName}`} />
                        </div>
                        {productType}
                        <div className="product_content">
                            <div className="row justify-content-between">
                                <a className="col" href={`/member/store/${product.seller}`} style={{ color: 'gray' }}>{product.sellerNick}</a>
                                <div className="text-right col">{dateFormat(product.regDate, 'yyyy-mm-dd')}</div>
                            </div>
                            <div className="row justify-content-between">
                                <div className="text-right col">{product.category}</div>
                            </div>
                            <div className="row justify-content-between">
                                <div className="product_title col title" >
                                    <a href={`/product/detail/${product.productNo}`}>{product.name}</a>
                                </div>
                                <div className="product_price col"><NumberFormat value={product.price} displayType="text" thousandSeparator={true} />원</div>
                            </div>
                        </div>
                    </div>
                );
            });
        }

        return (
            <div className="container">
                {/* <!-- Product Sorting --> */}
                <form onSubmit={this.handleSubmit}>
                    <div className="sorting_bar d-flex flex-md-row flex-column align-items-md-center justify-content-md-start">
                        <div className="sorting_container">
                            <div className="sorting">
                                <select name="type" id="type_form" className="custom-select" value={type} onChange={this.handleChangeSelectType}>
                                    <option value="all" >전체</option>
                                    <option value="흥정" >흥정</option>
                                    <option value="일반">일반</option>
                                </select>
                                <select name="category" id="category_form" className="custom-select" value={category} onChange={this.handleChangeSelectCategory}>
                                    <option value="every">전체</option>
                                    <option value="전자제품" >전자제품</option>
                                    <option value="의류/잡화" >의류/잡화</option>
                                    <option value="생활용품" >생활용품</option>
                                    <option value="기타" >기타</option>
                                </select>
                            </div>
                        </div>
                        <div className="sorting_container ml-md-auto">
                            <input type="text" className="search_input" name="keyword" value={keyword} placeholder="Search" required="required" onChange={this.handleChangeInput} />
                            <button type="submit" className="newsletter_button" style={{ width: '80px', height: '45px', marginTop: '5px' }}><span>검색</span></button>
                        </div>
                    </div>
                    <div className="product_grid">
                        {productRows}
                    </div>
                    <div className="wrapper">
                        <nav>
                            <ul className="pager">
                                <li className="pager__item pager__item--prev"><a className="pager__link" href="#">
                                    <i className="fa fa-chevron-left"></i></a></li>
                                <li className="pager__item"><a className="pager__link" href="#">...</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">1</a></li>
                                <li className="pager__item active"><a className="pager__link" href="#">2</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">3</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">4</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">5</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">6</a></li>
                                <li className="pager__item"><a className="pager__link" href="#">...</a></li>
                                <li className="pager__item pager__item--next"><a className="pager__link" href="#">
                                    <i className="fa fa-chevron-right"></i></a></li>
                            </ul>
                        </nav>
                    </div>
                </form>
            </div>
        );
    }
}
export default ProductList;