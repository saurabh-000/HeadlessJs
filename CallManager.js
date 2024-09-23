// CallManager.js
import CallKeep from 'react-native-callkeep';
import uuid from 'react-native-uuid';
import { PermissionsAndroid, Platform } from 'react-native';
import Sound from 'react-native-sound';
let callAudio;
const options = {
  ios: {
    appName: 'Headless',  // Name of the app that will show on call UI
    supportsVideo: false,
    maximumCallGroups: '1',
    maximumCallsPerCallGroup: '1',
  },
  android: {
    alertTitle: 'Permissions required',
    alertDescription: 'This app needs to access your phone accounts',
    cancelButton: 'Cancel',
    okButton: 'OK',
    additionalPermissions: [PermissionsAndroid.PERMISSIONS.CALL_PHONE],
  },
  foregroundService: {
    channelId: 'com.headless',
    channelName: 'Foreground service for my app',
    notificationTitle: 'My app is running on background',
    notificationIcon: 'Path to the resource icon of the notification',
  }, 
};

// Setup CallKeep
export const setupCallKeep = () => {
  CallKeep.setup(options);
  CallKeep.setAvailable(true); // Set your app to be available to handle calls
};

// Display an incoming call
export const displayIncomingCall = (callerName) => {
  const callUUID = uuid.v4(); // Generate a unique ID for the call
  CallKeep.displayIncomingCall(callUUID, callerName, 'Walsons Labs');

};

// Handle outgoing call
export const startCall = (callerName) => {
  const callUUID = uuid.v4(); // Generate a unique ID for the call
  CallKeep.startCall(callUUID, callerName, callerName);
  return callUUID;
};

// End a call
export const endCall = (callUUID) => {
  CallKeep.endCall(callUUID);
};

export const playAudioOnAnswer = () => {
  // Load the audio file
  callAudio = new Sound('audio.mp3', Sound.MAIN_BUNDLE, (error) => {
    if (error) {
      console.log('Failed to load the sound', error);
      return;
    }
    // Play the sound once the call is answered
    callAudio.play((success) => {
      if (success) {
        console.log('Audio played successfully');
      } else {
        console.log('Audio playback failed');
      }
    });
  });
};

// Stop audio playback when the call ends
export const stopAudio = () => {
  if (callAudio) {
    callAudio.stop(() => {
      console.log('Audio stopped');
    });
  }
};
