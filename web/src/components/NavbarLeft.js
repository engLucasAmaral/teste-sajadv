import React from 'react'
import styled, { css } from 'styled-components'
import Button from './ButtonLink'
import { User } from 'styled-icons/boxicons-regular/User'
import { Settings } from 'styled-icons/feather/Settings'
import { Star } from '@styled-icons/boxicons-regular/Star'
import { ListUl } from '@styled-icons/boxicons-regular/ListUl'
import { PhoneFind } from 'styled-icons/remix-line/PhoneFind';
import { Link } from "react-router-dom";
const Navbar = styled.div`
    height: 100vh;
    background-color: #091E40;
    flex: 1 1 auto;
    width: 240px;
    display: block;
    box-shadow: 0px 2px 4px -1px rgba(0, 0, 0, 0.2), 0px 4px 5px 0px rgba(0, 0, 0, 0.14), 0px 1px 10px 0px rgba(0, 0, 0, 0.12);
    padding-left: 40px;
    padding-top: 130px;
    margin: 0;
    display: flex;
    flex-direction: column;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 0;
    align-items: flex-start;
`;
// .menu li  ul{
//     position:absolute;
//     top:25px;
//     left:0;
//     background-color:#fff;
//     display:none;
//     }
const navbarIconStyle = css`
    color: #fff;
    height: 24px;
    width: 24px;
`;

const SettingsIcon = styled(Settings)`
    ${navbarIconStyle}
`;

const ListUlIcon = styled(ListUl)`
    ${navbarIconStyle}
`;

const PhoneFindIcon = styled(PhoneFind)`
    ${navbarIconStyle}
`;

const StarIcon = styled(Star)`
    ${navbarIconStyle}
`;

const UserIcon = styled(User)`
    ${navbarIconStyle}
`;

export default class NavbarLeft extends React.Component {
    render() {
        return (
            <Navbar>
                <Button to='/gestao' title='Gestão de Pessoas' icon={<ListUlIcon />} />
            </Navbar>
        )
    }
}