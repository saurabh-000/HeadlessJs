import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import React, { useEffect, useRef, useState } from "react";
import { DeviceEventEmitter, Linking, NativeModules, Text, TouchableOpacity, View } from "react-native";
import HomeScreen from "./src/Screens/HomeScreen";
import TestScreen from "./src/Screens/TestScreen";
import NativeDevSettings from 'react-native/Libraries/NativeModules/specs/NativeDevSettings'
const App=()=>{
  const Stack = createNativeStackNavigator();
  const [isStarted,setIsStarted]=useState(false)
  const linking = {
    prefixes: ['example://'],
    config: {
      screens: {
        HomeScreen: 'Home',
        Test: 'Test',  // 'call/:id' allows dynamic routing with an id parameter
      },
    },
  };


  useEffect(() => {
    // if(!isStarted){
    //   connectToRemoteDebugger()
    //   setIsStarted(true)
    // }
    const handleDeepLink = (event) => {
      console.log("event",event)
      const route = event.url ? event.url.replace(/.*?:\/\//g, '') : null;
      if (route) {
        console.log("route",route)
        //navigationRef.current?.navigate(route); // Navigate to specific route
      }
    };

    Linking.addEventListener('url', handleDeepLink);

    return () => {
      Linking.removeEventListener('url', handleDeepLink);
    };
  }, []);

   const connectToRemoteDebugger=()=>{
    NativeDevSettings.setIsDebuggingRemotely(true)
   }

  return(
    <NavigationContainer linking={linking}>
      <Stack.Navigator >
        <Stack.Screen name="Home" component={HomeScreen}  />
        <Stack.Screen name="Test" component={TestScreen} />
      </Stack.Navigator>
    </NavigationContainer>
    
  )
}
export default App;