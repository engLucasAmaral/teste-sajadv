import React from 'react'
import styled from 'styled-components'

const Card = styled.div`
    display: block;
    position: relative;
    border-radius: 4px;
    background-color: #FFFFFF;
    flex: 1 0 calc(50% - 80px);
    margin: 40px;
    max-width: calc(100vw - 180px);
    box-shadow: 0px 3px 1px -2px rgba(0, 0, 0, 0.2), 0px 2px 2px 0px rgba(0, 0, 0, 0.14), 0px 1px 5px 0px rgba(0, 0, 0, 0.12);
`;

const CardTitle = styled.div`
    display: block;
    position: relative;
    font-size: 24px;
    text-align: center;
    line-height: 2rem;
    font-family: 'Roboto', sans-serif;
    width: 300px;
    padding: 16px;
    background-color: #009fc2;
    border-color: #009fc2;
    border-radius: 4px;
    color: #fff;
    top: -24px;
    left: 24px;
    box-shadow: 0px 2px 4px -1px rgba(0, 0, 0, 0.2), 0px 4px 5px 0px rgba(0, 0, 0, 0.14), 0px 1px 10px 0px rgba(0, 0, 0, 0.12) !important;
`;

export default class CardContainer extends React.Component {
    render() {
        return (
            <Card>
                <CardTitle>{this.props.title}</CardTitle>
                {this.props.children}
            </Card>
        )
    }
}