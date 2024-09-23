import React from "react";
import { NativeModules, Text, TouchableOpacity, View } from "react-native";
const App=()=>{
  const {RNModule} = NativeModules;
  const start=()=>{
    RNModule.startService()
  }

  return(
    <View style={{flex:1,justifyContent:'center',alignItems:'center'}}>
        <TouchableOpacity style={{backgroundColor:'red',padding:10}} onPress={()=>start()}>
          <Text>start</Text>
        </TouchableOpacity>
    </View>
    
  )
}
export default App;