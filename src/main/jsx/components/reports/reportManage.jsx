import React,{Component} from'react';
import dateFormat from 'dateformat';

class ReportManage extends Component {
    constructor(props){
        super(props);
        this.state = {
            value:''
        }  
        this.handleInsert = this.handleInsert.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInsert(e){
        this.setState({value:e.target.value});
    }
    
    handleSubmit(e){

    }
    render(){
        const {value} = this.state;
        const {reports} = this.props;
        const report = reports.map((report) =>{
            return(
                    <tr key={report.reportNo} id="report?{reoprt.reportNo}">
                        <td>{report.reportNo}</td>
                        <td>{report.reporter}</td>
                        <td>{dateFormat(report.regDate, "yyyy-mm-dd")}</td>
                        <td>{report.confirm}</td>
                    </tr>

                )
        })

        return(
            <div>
                <table className="table">
                    <thead className="thead">
                        <tr>
                            <td>신고번호</td>
                            <td>신고자</td>
                            <td>신고날짜</td>
                            <td>처리유무</td>
                        </tr>
                    </thead>
                    <tbody>
                        {report}
                    </tbody>
                </table>
            </div>
        )
    }
}
export default ReportManage;