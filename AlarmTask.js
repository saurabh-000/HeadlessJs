import CallKeep from 'react-native-callkeep';
import { displayIncomingCall, setupCallKeep } from './CallManager';
module.exports=async(data)=>{
    console.log("fetching in componenet 2",data)
    setupCallKeep()
    displayIncomingCall('John Doe');
       
}