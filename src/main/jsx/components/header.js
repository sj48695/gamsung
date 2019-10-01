import React from 'react';

import { Link } from 'react-router-dom';

const Header = () => (
    <nav>
        <nav className="gide">
            <ul className="gide-nav-ul">
                <li className="gide-nav-ul-li"><Link to="/member/admin" className="gide-nav-ul-li-a">회원관리</Link></li>
                <li className="gide-nav-ul-li"><Link to="/member/report" className="gide-nav-ul-li-a">신고접수</Link></li>
                <li className="gide-nav-ul-li"><a href="#" className="gide-nav-ul-li-a">회원문의</a></li>
            </ul>
        </nav>
    </nav>
);

export default Header;