import React from 'react';
import {
    View,
    Text, StyleSheet, Platform, I18nManager
} from 'react-native';
import {HeaderBackButton} from 'react-navigation'

class TitleBar extends React.PureComponent {
    render() {
        const {
            onPress,
            title,
            titleStyle,
            tintColor,
        } = this.props;

        return (
            <View style={styles.container}>
                <HeaderBackButton
                    onPress={onPress}
                    tintColor={tintColor}
                    titleStyle={titleStyle}
                />
                <Text
                    style={[{color: tintColor}, titleStyle]}
                    numberOfLines={1}>
                    {title}
                </Text>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        alignItems: 'center',
        flexDirection: 'row',
        backgroundColor: 'transparent',
    },
    title: {
        fontSize: 17,
        paddingRight: 10,
    },
    icon:
        Platform.OS === 'ios'
            ? {
                height: 21,
                width: 13,
                marginLeft: 9,
                marginRight: 22,
                marginVertical: 12,
                resizeMode: 'contain',
                transform: [{scaleX: I18nManager.isRTL ? -1 : 1}],
            }
            : {
                height: 24,
                width: 24,
                margin: 16,
                resizeMode: 'contain',
                transform: [{scaleX: I18nManager.isRTL ? -1 : 1}],
            },
    iconWithTitle:
        Platform.OS === 'ios'
            ? {
                marginRight: 6,
            }
            : {},
});

export {TitleBar}