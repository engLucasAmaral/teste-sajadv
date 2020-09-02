import React from 'react'
import { Link } from 'react-router-dom'
import styled from 'styled-components'

const ButtonLinkContainer = styled(Link)`
    display: inline-flex;
    height: 26px;
    margin: 10px;
    background-color: #091E40;
    color: #FFFFFF;
    cursor: pointer;
    flex: 0 0 auto;
    letter-spacing: 1px;
    position: relative;
    text-decoration: none;
    text-transform: uppercase;
    align-items: center;

    &:hover {
        opacity: 0.6;
    }
`;

const ButtonLinkIcon = styled.span`
    margin-right: 5px;
`;

const ButtonLinkTitle = styled.span`
    display: flex;
    font-size: 18px;
`;

export default class ButtonLink extends React.Component {
    render() {
        return (
            <ButtonLinkContainer to={this.props.to}>
                <ButtonLinkIcon>
                    {this.props.icon}
                </ButtonLinkIcon>
                <ButtonLinkTitle>
                    {this.props.title}
                </ButtonLinkTitle>
            </ButtonLinkContainer>
        )
    }
}