import React,{Component} from'react';

import ReportManage from './reports/reportManage.jsx';

class ReportApp extends Component{
    constructor(props) {
        super(props);
        this.state = {
        };
    }
    render(){
        return(
            <div className="container">
                <div className="gide-main">
                    <ReportManage />
                </div>
                
            </div>
        )
    }
}

export default ReportApp;