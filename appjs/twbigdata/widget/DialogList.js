import React, {PureComponent} from 'react'
import {
    View,
    Text,
    ScrollView,
    TouchableNativeFeedback,
    Modal,
    StyleSheet
} from 'react-native'
import {IconFont} from '../iconfont/Icon'

let Dimensions = require('Dimensions');
let screenWidth = Dimensions.get('window').width;
let dialogWidth = screenWidth - 80;

class DialogList extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {modalVisible: true}
    }

    setModalVisible(visible, chooseItem) {
        this.setState({modalVisible: visible});
        if (!visible && this.props.onDismiss) {
            this.props.onDismiss(chooseItem)
        }
    }

    onClose() {
        this.setModalVisible(false);
    }

    render() {
        const {
            data,
            onDismiss,
            title,
            defaultItem,
        } = this.props;
        return (
            <Modal
                animationType={'fade'}
                transparent={true}
                visible={this.state.modalVisible}
                onRequestClose={() => {
                    this.setModalVisible(false);
                }}>
                <View style={styles.container}>
                    <View style={styles.innerContainer}>
                        {title && (<Text style={styles.title}>{title}</Text>)}
                        <ScrollView>
                            {data && (data.map((item) => {
                                return (
                                    <TouchableNativeFeedback
                                        key={item.key}
                                        onPress={() => {
                                            this.setModalVisible(false, item.name)
                                        }}>
                                        <View style={styles.itemView}>
                                            <IconFont style={{marginLeft: 10, marginRight: 10}} name={'tick'} size={15}
                                                      color={defaultItem && (defaultItem === item.name) ? 'red' : '#fff'}/>
                                            <Text style={styles.itemText}>{item.name}</Text>
                                        </View>
                                    </TouchableNativeFeedback>
                                )
                            }))}
                        </ScrollView>
                    </View>
                </View>
            </Modal>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 40,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
    innerContainer: {
        borderRadius: 10,
        alignItems: 'center',
        backgroundColor: '#fff',
    },
    title: {
        width: dialogWidth,
        color: 0x000000de,
        fontSize: 15,
        textAlign: 'center',
        textAlignVertical: 'center',
        padding: 12,
        borderBottomWidth: 1,
        borderBottomColor: '#ddd',
        fontWeight: 'bold',
    },
    itemView: {
        width: dialogWidth,
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        padding: 12,
    },
    itemText: {
        color: 0x000000de,
        fontSize: 15,
    },
    btnContainer: {
        alignItems: 'center',
    },
    btnText: {
        width: dialogWidth,
        color: 0x000000de,
        fontSize: 15,
        textAlign: 'center',
        textAlignVertical: 'center',
        borderTopWidth: 1,
        borderTopColor: '#ddd',
        padding: 12,
    },
});

export {DialogList}

/**
 <TouchableOpacity style={{flex: 1}} onPress={this.onClose.bind(this)}>
 <View style={styles.container}>
 <View style={styles.innerContainer}>
 {title && (<Text style={styles.title}>{title}</Text>)}
 {data && (data.map((item) => {
     return <Text style={styles.itemText} key={item.key}>{item.name}</Text>
 }))}
 <View >
 <TouchableNativeFeedback onPress={() => {
                                    this.setModalVisible(!this.state.modalVisible)
                                }}>
 <Text style={styles.btnText}>关闭</Text>
 </TouchableNativeFeedback>
 </View>
 </View>
 </View>
 </TouchableOpacity>
 */