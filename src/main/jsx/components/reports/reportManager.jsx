import React, {Component} from 'react';

class ReportMagnager extends Component {
    render(){
        return(
            <div>
                <table className="table">
                    <thead className="thead">
                        <tr>
                            <td>신고번호</td>
                            <td>신고자</td>
                            <td>신고날짜</td>
                        </tr>
                    </thead>
                </table>
            </div>
        )
    }
}
export default ReportMagnager;