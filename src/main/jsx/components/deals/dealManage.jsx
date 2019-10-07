import React,{Component} from 'react';
import dateFormat from 'dateformat';
import Modal from 'react-modal';

import axios from 'axios';

import DealDetail from './dealDetail.jsx';

class DealManage extends Component {

    

    constructor(props){



        super(props);
        this.state={
            deals:[],
            isModalOpened: false,
            selectDeal: null,
            products:[],
            productno: '',
            product:[]
        }

        this.closeModal = this.closeModal.bind(this);
        this.openModal = this.openModal.bind(this);
        this.handleDeleted = this.handleDeleted.bind(this);
        this.handleComlete = this.handleComlete.bind(this);
        this.findsaleCom = this.findsaleCom.bind(this);
    }

    closeModal(){
        this.setState({isModalOpened:false, selectDeal: null})
    }

    openModal(i){
        this.setState({isModalOpened:true, selectDeal: i})
    }

    handleDeleted(e){
        const no = e.target.value;
        const myThis = this;
        axios.post("/deal/deleted", no)
             .then((result) =>{
                 if(result.data === "success"){
                     alert("삭제되었습니다.")
                     myThis.closeModal();
                 }
             })
             .catch((err)=>{
                 console.log(err.message);
             })
    }

    handleComlete(e){
        const no = this.state.selectDeal;
        const deal = this.state.deals[no];
        const myThis = this;
        axios.post("/deal/complete", deal)
             .then((result) =>{
                 if(result.data === "success"){
                     alert("거래가 최종 완료되었습니다.")
                     myThis.closeModal();
                 }
             })
             .catch((err)=>{
                 console.log(err.message);
             })
    }

    findsaleCom(){
        axios.get("/deal/findsaleCom")
             .then((result)=>{
                 this.setState({
                     deals : result.data.slice(0)
                 })
             })
             .catch((err)=>{
                 console.log(err.message);
             })
    }

    findComlete(){
        axios.get("/deal/findcomlete")
             .then((result)=>{
                 this.setState({
                     deals : result.data.slice(0)
                 })
             })
             .catch((err)=>{
                 console.log(err.message);
             })
    }

    shouldComponentUpdate(nextProps, nextState) {
        const { selectDeal } = this.state;
        
        if (selectDeal !=null && selectDeal === nextState.selectDeal) {
            return false;
        }
        else {        
            return true;
        } 
        
    }

    componentDidMount(){
        axios.get("/deal/list")
             .then(result =>{
                this.setState({
                    deals : result.data.slice(0)
                    
                })
                console.log(result.data.slice(0))
        })

    }

    renderDeals(){
        return this.state.deals.map((deal, i)=>{
            return(
                <tr key={deal.dealNo} onClick={() => this.openModal(i)}>
                    <td>{deal.dealNo}</td>
                    <td>{deal.buyer}</td>
                    <td>{deal.complete}</td>
                    <td>{dateFormat(report.regDate, "yyyy-mm-dd")}</td>
                    <td>{deal.active}</td>
                </tr>
            )
        })
    }

    renderModal(){
        if(this.state.selectDeal !== null){
            const  deal = this.state.deals[this.state.selectDeal];
            //const product = this.state;

            const productno = deal.productNo;
            const product = deal.products.find(p => p.productNo === productno);
            return(
                <div>
                    <div>
                        
                            <div className="report-tx">구매번호:<input type="hidden" value={deal.dealNo} />{deal.dealNo}</div>
                            <div className="report-tx">구매자:<input type="hidden" value={deal.buyer} />{deal.buyer}</div>
                            <div className="report-tx">판매자:<input type="hidden" value={product.seller} />{product.seller}</div>
                            <div className="report-tx">판매물품:<input type="hidden" value={product.contents} />{product.contents}</div>
                            <div className="report-tx"><div>판매금액:</div><input type="hidden" value={deal.price} />{deal.price}</div>
                            <div className="report-tx">거래상태:{deal.active}</div>
                            <div className="report-tx">삭제
                                <label className="switch">
                                    <input type="checkbox" defaultChecked={deal.deleted} value={deal.dealNo} onChange={this.handleDeleted}></input>
                                    <span className="slider round"></span>
                                </label>
                            </div>
                            <div className="report-tx">결제처리{deal.complete}<br />
                                <label className="switch">
                                        <input type="checkbox" defaultChecked={deal.completeActive} value={deal.dealNo} onChange={this.handleComlete}></input>
                                        <span className="slider round"></span>
                                </label>
                            </div>
                            <div><button className="newsletter_button col-5 mt-0 btn-sz" onClick={this.closeModal}>창 닫기</button></div>
                    </div>
                            
                </div>
            )
        }
    }


    render(){
        const { isModalOpened } = this.state;
        return(
            <div>
                <table className="table">
                    <thead className="thead">
                        <tr>
                            <td>거래번호</td>
                            <td>구매자</td>
                            <td>구매물품</td>
                            <td>구매날짜</td>
                            <td>거래상태</td>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderDeals()}
                    </tbody>
                </table>
                <Modal isOpen={isModalOpened} ariaHideApp={false}>
                    <div>{this.renderModal()}</div>
                </Modal>
            </div>
        )
    }
}
export default DealManage;