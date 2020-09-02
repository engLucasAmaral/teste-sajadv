import React from 'react'
import styled from 'styled-components'

const Button = styled.button`
    display: inline-flex;
    height: 36px;
    padding: 24px;
    margin: 10px;
    background-color: ${props => props.outlined ? '#FFFFFF' : '#091E40'};
    color:  ${props => props.outlined ? props.outlined : '#FFFFFF'};
    caret-color: ${props => props.outlined ? props.outlined : '#FFFFFF'};
    cursor: pointer;
    box-shadow: ${props => props.outlined ? 'none' : '0px 3px 1px -2px rgba(0, 0, 0, 0.2), 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 1px 5px 0px rgba(0, 0, 0, 0.12)'};
    align-items: center;
    border-radius: 4px;
    flex: 0 0 auto;
    letter-spacing: 1px;
    justify-content: center;
    position: relative;
    text-decoration: none;
    outline: none;
    border: ${props => props.outlined ? `1px solid ${props.outlined}` : 'none'};
    transition-duration: 0.15s;
    transition-property: box-shadow, transform, opacity, -webkit-box-shadow, -webkit-transform;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    text-transform: uppercase;

    &:before {
        border-radius: inherit;
        bottom: 0;
        color: inherit;
        content: '';
        left: 0;
        opacity: 0;
        pointer-events: none;
        position: absolute;
        right: 0;
        top: 0;
        -webkit-transition: opacity 0.2s cubic-bezier(0.4, 0, 0.6, 1);
        transition: opacity 0.2s cubic-bezier(0.4, 0, 0.6, 1);
    }

    &:active {
        box-shadow: none;
    }

    &:hover {
        opacity: ${props => props.outlined ? '1' : '0.9'};
        background-color: ${props => props.outlined ? '#f2f2f2' : '#091E40'};
    }
`;

const ButtonTitle = styled.span`
    display: flex;
    align-items: center;
    font-size: 16px;
`;

export default class ButtonRounded extends React.Component {
    render() {
        return (
            <Button type={this.props.type} onClick={this.props.click} outlined={this.props.outlined}>
                <ButtonTitle>
                    {this.props.title}
                </ButtonTitle>
            </Button>
        )
    }
}