/*'use strict';
import { 
  NativeModules, 
  requireNativeComponent, 
  View 
} from 'react-native';
import PropTypes from 'prop-types';*/
var React = require('react');
import PropTypes from 'prop-types'
import { Component } from 'react';  
import { NativeModules,findNodeHandle, requireNativeComponent, View, Text, UIManager, DeviceEventEmitter  } from 'react-native';

/*var iface = {  
    name: 'MyEditText',
    propTypes: {
		blured: PropTypes.bool,
		text:PropTypes.string,
        ...View.propTypes, // include the default view properties
    }
}*/


class MyEditTextComponent extends Component {

    constructor(props) {
        super(props);
        this._onChange = this._onChange.bind(this);
    }

    _onChange(event) {
        if(!this.props.onDateChange) {
            return;
        }
        this.props.onDateChange(event.nativeEvent);
    }

    render() {
        return <MyEditText {...this.props} onChange={this._onChange} />;
    }
	
	requestFocus() {
		UIManager.dispatchViewManagerCommand(
			findNodeHandle(this),
			UIManager.MyEditText.Commands.requestFocus,
			[],
		);
	}
	
	requestBlur() {
		UIManager.dispatchViewManagerCommand(
			findNodeHandle(this),
			UIManager.MyEditText.Commands.requestBlur,
			[],
		);
	}
	
	setText(text) {
		if(!text || (text && text.length == 0)){
			UIManager.dispatchViewManagerCommand(
			findNodeHandle(this),
			UIManager.MyEditText.Commands.setText,
			undefined
			);
		}else{
			UIManager.dispatchViewManagerCommand(
			findNodeHandle(this),
			UIManager.MyEditText.Commands.setText,
			[text],
			);
		}
		
	}
}

MyEditTextComponent.propTypes = {  
    blured: PropTypes.bool,
	text:PropTypes.string,
    onDateChange: PropTypes.func,
    ...View.propTypes,
}

const MyEditText = requireNativeComponent(`MyEditText`, MyEditTextComponent, {  
    nativeOnly: { 
        onChange: true,
    },
});


//var MyEditText = requireNativeComponent('MyEditText', iface);
export default MyEditTextComponent;
