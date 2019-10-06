import React,{Component} from'react';
import dateFormat from 'dateformat';
import Modal from 'react-modal';

import * as axios from 'axios';

import * as ManageService from '../../services/ManageService.js';

class ReportManage extends Component {
    constructor(props){
        super(props);
        this.state = {
            reports: [],
            value:'',
            isModalOpened: false,
            selectReport: null,
            reportNo: '',
            reporter: '',
            user: '',
            contents: '',
            answer:''
        }  
        this.handleInsert = this.handleInsert.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInsert(e){
        this.setState({answer:e.target.value});
    }

    openModal(i){
        this.setState({isModalOpened: true, selectReport: i})
    }

    closeModal(){
        this.setState({isModalOpened: false})
    }
    
    handleSubmit(e){
        e.preventDefault();

        const i = this.state.selectReport;
        const reportList = this.state.reports[i]
        const answer = this.state.answer;
        const report = {
                reportNo: reportList.reportNo,
                reporter: reportList.reporter,
                user: reportList.user,
                contents: reportList.contents,
                answer: answer
        }
        const myThis = this;
        axios.post("/report/answer", report)
             .then((result) =>{
                 if(result.data === "success"){
                     myThis.closeModal();                    
                    console.log("답변완료하였습니다.")
                    this.componentDidMount();
                } else {
                    alert("답변입력이 실패했습니다.");
                }
             })
             .catch((err)=>{
                 console.log(err.message);
                 return;
             })
    }

    componentDidMount(){
        axios.get("/report/list")
             .then(result =>{
                 this.setState({
                     reports : result.data.slice(0)
                 })
                 console.log(result.data.slice(0))
             })
    }

    renderReports(){
        return this.state.reports.map((report, i) => {
            return(
                <tr key={report.reportNo} onClick={() => this.openModal(i)} id="report?{reoprt.reportNo}">
                <td>{report.reportNo}</td>
                <td>{report.reporter}</td>
                <td>{report.title}</td>
                <td>{dateFormat(report.regDate, "yyyy-mm-dd")}</td>
                <td>{report.confirm}</td>
            </tr>
            )
        });
    };

    renderModal(){
        const {answer} = this.state;
        if(this.state.selectReport !== null){
            const report = this.state.reports[this.state.selectReport];
            return(
                <div>
                    <div>
                        <form onSubmit={this.handleSubmit} method="POST">
                            <div className="report-tx">신고번호:<input type="hidden" value={report.reportNo} />{report.reportNo}</div>
                            <div className="report-tx">신고자:<input type="hidden" value={report.reporter} />{report.reporter}</div>
                            <div className="report-tx">신고대상:<input type="hidden" value={report.user} />{report.user}</div>
                            <div className="report-tx"><div>신고내용:</div><input type="hidden" value={report.contents} />{report.contents}</div>
                            <div className="report-tx">관리자답변:{report.answer}</div>
                            <div className="report-tx">답변입력:
                                <div><input type="text" name="answer" value={answer} onChange={this.handleInsert} /></div>
                                <div>
                                    <button className="newsletter_button col-5 mt-0 btn-sz" type="submit">답변</button>
                                    <button className="newsletter_button col-5 mt-0 btn-sz" onClick={this.closeModal}>취소</button>
                                </div>
                            
                            </div>
                        </form>
                    </div>

                </div>
            )
        }
    }
    render(){
        const {isModalOpened} = this.state;

        return(
            <div>
                <table className="table">
                    <thead className="thead">
                        <tr>
                            <td>신고번호</td>
                            <td>신고자</td>
                            <td>신고제목</td>
                            <td>신고날짜</td>
                            <td>처리유무</td>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderReports()}
                    </tbody>
                </table>
                <Modal isOpen={isModalOpened} ariaHideApp={false}>
                    <div>{this.renderModal()}</div>
                </Modal>
            </div>
        )
    }
}
export default ReportManage;