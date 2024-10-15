import CallKeep from 'react-native-callkeep';
import { displayIncomingCall, playAudioOnAnswer, setupCallKeep, stopAudio } from './CallManager';
import { DeviceEventEmitter, Linking } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { useEffect } from 'react';
module.exports=async(data)=>{
    console.log("fetching in componenet 2",data)
    setupCallKeep()
    displayIncomingCall('John Doe');

    CallKeep.addEventListener('answerCall', ({ callUUID }) => {
        console.log(`Call answered: ${callUUID}`);

        Linking.openURL('example://Test');
        CallKeep.endCall(callUUID)
        CallKeep.backToForeground()
        
        //playAudioOnAnswer();
        // Here you can trigger your VoIP call connection logic
      });
  
      CallKeep.addEventListener('endCall', ({ callUUID }) => {
        console.log(`Call ended: ${callUUID}`);
        stopAudio();
        // Disconnect your VoIP call logic here
      });
       
}


