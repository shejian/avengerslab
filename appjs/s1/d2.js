import React, {Component} from 'react'
import {
    View,
    Text,
    Button,
    Image,
    StyleSheet,
    Platform
} from 'react-native'
import {
    createStackNavigator
} from 'react-navigation'

String.prototype.myReplace = (s, d) => {
    let reg = new RegExp(s, 'g');
    return this.replace(reg, d)
};

class LogoTitle extends Component {
    render() {
        return (
            <Image
                source={{uri: 'aa'}}
                style={{width: 30, height: 30}}/>

        )
    }
}

class HomeScreen extends Component {
    static navigationOptions = ({navigation}) => {
        return {
            headerTitle: <LogoTitle/>,
            headerLeft: (
                <Button
                    onPress={() => navigation.navigate('MyModal')}
                    title='Info'
                    color={Platform.OS === 'ios' ? '#fff' : null}
                />
            ),
            headerRight: (
                <Button
                    onPress={navigation.getParam('increaseCount')}
                    title='点击'
                    color={Platform.OS === 'ios' ? '#fff' : null}
                />
            )
        }
    };

    state = {count: 0};

    componentDidMount() {
        this.props.navigation.setParams({increaseCount: this._increaseCount})
    }

    _increaseCount = () => {
        this.setState({
            count: this.state.count + 1
        })
    };

    render() {
        return (
            <View style={styles.full}>
                <Text>首页</Text>
                <Text>count: {this.state.count}</Text>
                <Button
                    title='下一页'
                    onPress={() => this.props.navigation.navigate('Details', {
                        item: 86,
                        otherParam: 'xxxx'
                    })}
                />
            </View>
        )
    }
}

function getActiveRouteName(navigationState) {
    if (!navigationState) {
        return null
    }
    let route = navigationState.routes[navigationState.index];
    if (route.routes){
        return getActiveRouteName(route)
    }
    return route.routeName
}

class DetailsScreen extends Component {
    static navigationOptions = ({navigation, navigationOptions}) => {
        const {params} = navigation.state;
        return {
            title: params.otherParam ? params.otherParam : '未设置title',
            headerStyle: {
                backgroundColor: navigationOptions.headerTintColor
            },
            headerTintColor: navigationOptions.headerStyle.backgroundColor
        }
    };

    render() {
        let {navigation} = this.props;
        let itemId = navigation.getParam('item', 0);
        let otherParam = navigation.getParam('otherParam', 'ccc');
        let reg = new RegExp('"', 'g');
        return (
            <View style={styles.full}>
                <Text>详情页</Text>
                <Text>itemId: {JSON.stringify(itemId)}</Text>
                <Text>otherParam: {JSON.stringify(otherParam).replace(reg, '')}</Text>
                <Button
                    title='继续打开此页'
                    onPress={() => this.props.navigation.push('Details', {
                        item: Math.floor(Math.random() * 100)
                    })}
                />
                <Button
                    title='打开home'
                    onPress={() => this.props.navigation.navigate('Home')}
                />
                <Button
                    title='返回'
                    onPress={() => this.props.navigation.goBack()}
                />
                <Button
                    title='第一页'
                    onPress={() => this.props.navigation.popToTop()}
                />
                <Button
                    title='更新标题'
                    onPress={() => this.props.navigation.setParams({otherParam: '我被更新了'})}/>
            </View>
        )
    }
}

class ModalScreen extends Component{
    render() {
        return(
            <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
                <Text style={{fontSize: 30}}>this is a modal</Text>
                <Button
                    onPress={() => this.props.navigation.goBack()}
                    title='Dismiss'
                />
            </View>
        )
    }
}

const styles = StyleSheet.create({
    full: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    }
});

const MainStack = createStackNavigator(
    {
        Home: HomeScreen,
        Details: DetailsScreen
    },
    {
        initialRouteName: 'Home',
        navigationOptions: {
            headerStyle: {
                backgroundColor: '#f4511e'
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
                fontWeight: 'bold'
            }
        }
    }
);

const RootStack = createStackNavigator(
    {
        Main: {
            screen: MainStack
        },
        MyModal: {
            screen: ModalScreen
        }
    },
    {
        mode:'modal',
        headerMode: 'none'
    }
);

export default class App extends Component {
    render() {
        return <RootStack onNavigationStateChange={(pre, current) =>{
            let currentScreen = getActiveRouteName(current)
            let preScreen = getActiveRouteName(pre)
            if (preScreen !== currentScreen) {
                console.log('preScreen:' + preScreen + ' currentScreen:' + currentScreen)
            }
        }}/>
    }
}
