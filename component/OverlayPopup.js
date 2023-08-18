import React from 'react';
import { Modal, View, Text, Button } from 'react-native';

const OverlayPopup = ({ visible, onRequestClose }) => {
  return (
    <Modal
      visible={visible}
      animationType="slide"
      transparent={true}
      onRequestClose={onRequestClose}
    >
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <View style={{ backgroundColor: 'white', padding: 20 }}>
          <Text>This is the overlay popup content</Text>
          <Button title="Dismiss" onPress={onRequestClose} />
        </View>
      </View>
    </Modal>
  );
};

export default OverlayPopup;
