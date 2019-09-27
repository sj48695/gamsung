import React, {Component} from 'react';

class UserManage extends Component {

    componentDidMount() {
        console.log('test');
    }

    render(){

        const { members } = this.props;

        const UserRaws = members.map( member => {
            return(
                <tr key={ member.id }>
                    <td>{ member.id }</td>
                    <td>{ member.nickname }</td>
                    <td>{ member.regDate }</td>
                    <td>{ member.active }</td>
                </tr>
            )
        })

        return(
            <div>
                <table>
                    <thead>
                        <tr>
                            <td>아이디</td>
                            <td>별명</td>
                            <td>가입날짜</td>
                            <td>상태</td>
                        </tr>
                    </thead>
                    <tbody>
                        {UserRaws}
                    </tbody>
                </table>
            </div>
        )
    }
}
export default UserManage;