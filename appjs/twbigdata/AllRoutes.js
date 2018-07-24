import React, {Component} from 'react';
import {BackHandler} from 'react-native';
import {createStackNavigator} from 'react-navigation';
import {AddStoreScreen} from './AddStoreScreen';
import {TitleBar} from "./TitleBar";

const RootStack = createStackNavigator(
    {
        AddStore: {
            screen: AddStoreScreen,
            navigationOptions: ({navigation, navigationOptions}) => {
                return {
                    headerTitle: (
                        <TitleBar onPress={() => BackHandler.exitApp()}
                                  title='上报新增门店'
                                  tintColor={navigationOptions.headerTintColor}
                                  titleStyle={navigationOptions.headerTitleStyle}
                        />
                    )
                }
            }
        }
    },
    {
        initialRouteName: 'AddStore',
        mode: 'card',
        headerMode: 'screen',
        navigationOptions: {
            headerStyle: {
                backgroundColor: '#fff'
            },
            headerTintColor: '#000',
            headerTitleStyle: {
                fontSize: 17
            }
        }
    }
);

export default class AddStore extends Component {
    render() {
        return (
            <RootStack/>
        )
    }
}