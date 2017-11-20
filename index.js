

'use strict';
import { 
  NativeModules, 
  requireNativeComponent, 
  View 
} from 'react-native';

var iface = {
  name: 'KeyboardDismissEditText',
  propTypes: {
    ...View.propTypes // include the default view properties
  },
};

var KeyboardDismissEditText = requireNativeComponent('KeyboardDismissEditText', iface);

export default KeyboardDismissEditText;
