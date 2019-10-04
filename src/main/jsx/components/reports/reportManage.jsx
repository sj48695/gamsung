import React,{Component} from'react';

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
                <div>
                    <tr id="report?{reoprt.reportNo}">
                        <td>{report.reportNo}</td>
                        <td>{report.reporter}</td>
                        <td>{report.contents}</td>
                        <td>{report.regDate}</td>
                        <td>{report.confirm}</td>
                    </tr>
                    <div id="results?{report.reportNo}">
                        <form onSubmit={this.handleSubmit}>
                            <input type="text" value={value} onChange={this.handleInsert}/>
                            <button type="submit">전송</button>
                        </form>
                    </div>
                </div>
                )
        })

        return(
            <div>
                <table className="table">
                    <thead className="thead">
                        <tr>
                            <td>신고번호</td>
                            <td>신고자</td>
                            <td>신고내용</td>
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