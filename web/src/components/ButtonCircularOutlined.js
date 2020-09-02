import React from 'react'
import styled from 'styled-components'

const Tooltip = styled.span`
  visibility: hidden;
  width: auto;
  background-color: #616161;
  color: #fff;
  font-size: 14px;
  line-height: 22px;
  text-align: center;
  border-radius: 5px;
  padding: 5px 16px;
  position: absolute;
  z-index: 1;
  top: 125%;
  margin: 0 auto;
  transition: all .2s linear;
  pointer-events: none;
`;

const Button = styled.button`
  display: flex;
  flex: 0 0 auto;
  position: relative;
  justify-content: center;
  cursor: pointer;
  border-radius: 50%;
  color: #009fc2;
  background-color: #fff;
  border: 1px solid #009fc2;
  width: 48px;
  height: 48px;
  margin: 0 10px;

  &:hover {
    background-color: #E9FFFF;
  }

  &:focus {
    outline: none;
  }

  &:hover ${Tooltip} {
    visibility: visible;
    opacity: 0.8;
    transform: translate(0, -4px);
  }
`;


export default class ButtonCircularOutlined extends React.Component {
  render() {
    return (
      <Button onClick={this.props.click}>
        {this.props.children}
        {this.props.tooltip && <Tooltip>{this.props.tooltip}</Tooltip>}
      </Button>
    )
  }
}