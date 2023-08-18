import React, {useEffect, useState} from 'react';
import {Button, View, Alert, Platform} from 'react-native';
import OverlayPopup from './component/OverlayPopup';
import PopupModule from './PopupModule';

const App = () => {
  const [popupVisible, setPopupVisible] = useState(false);

  useEffect(() => {
    if (Platform.OS === 'android') {
      PopupModule.isRequestOverlayPermissionGranted((status: any) => {
        if (status) {
          Alert.alert(
            'Permissions',
            'Overlay Permission',
            [
              {
                text: 'Cancel',
                onPress: () => console.log('Cancel Pressed'),
                style: 'cancel',
              },
              {
                text: 'OK',
                onPress: () => PopupModule.requestOverlayPermission(),
              },
            ],
            {cancelable: false},
          );
        }
      });
    }
  }, []);

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Button title="Show Popup" onPress={() => {}} />
      <OverlayPopup
        visible={popupVisible}
        onRequestClose={() => setPopupVisible(false)}
      />
    </View>
  );
};

export default App;
