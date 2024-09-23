/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import AlarmTask from './AlarmTask';




  AppRegistry.registerHeadlessTask('AlarmTask', () => 
    AlarmTask
);

AppRegistry.registerComponent(appName, () => App);
  