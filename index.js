'use strict';
import { 
  NativeModules, 
  requireNativeComponent, 
  View 
} from 'react-native';
import { PropTypes } from 'react';

var iface = {  
    name: 'MyEditText',
    PropTypes: {
        customval: PropTypes.string,
		blured: PropTypes.boolean,
		text:PropTypes.string,
        ...View.propTypes // include the default view properties
    }
}

var MyEditText = requireNativeComponent('MyEditText', iface);
export default MyEditText;
