import { useNavigation } from "@react-navigation/native";
import React, { useEffect } from "react";
import { DeviceEventEmitter, Linking, NativeModules, Text, TouchableOpacity, View } from "react-native";
const HomeScreen=()=>{
    const navigation=useNavigation()
    const {RNModule} = NativeModules;
  const start=()=>{
    RNModule.startService()
  }

  useEffect(() => {
    const handleInitialURL = async () => {
      const url = await Linking.getInitialURL();
      if (url) {
        const route = url.replace(/.*?:\/\//g, '');  // Extract the path
        console.log("route",route)
        navigation.navigate("Test");
      }
    };

    handleInitialURL();
  }, []);


  
    return(
        <View style={{flex:1,justifyContent:'center',alignItems:'center'}}>
        <TouchableOpacity onPress={()=>RNModule.startService()} style={{backgroundColor:'green',padding:10,width:150,margin:10,justifyContent:'center',alignItems:'center',borderRadius:10}}>
          <Text style={{color:'white',fontWeight:'bold'}}>Start Event</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={()=>RNModule.stopService()} style={{backgroundColor:'red',padding:10,width:150,margin:10,justifyContent:'center',alignItems:'center',borderRadius:10}} >
          <Text style={{color:'white',fontWeight:'bold'}}>Stop Event</Text>
        </TouchableOpacity>
    </View>
    )
}
export default HomeScreen