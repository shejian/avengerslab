import React, { Component } from 'react'
import { Text, Image, View, StyleSheet, TextInput,
        ScrollView, FlatList, SectionList } from 'react-native'

class HelloWorldApp extends Component {
    render() {
        return (
            <Text> Hello world!</Text>
        );
    }
}

class Bananas extends Component {
    render() {
        let pic = {
            uri: 'http://www.baidu.com/img/bd_logo1.png'
        };
        return (
            <Image source={pic} style={{width: 193, height: 110}} />
        );
    }
}

class Greeting extends Component {
    render() {
        return(
           <Text>Hello {this.props.name}!</Text>
        );
    }
}

class LostsOfGreetings extends Component {
    render() {
        return(
            <View style={{alignItems: 'center'}}>
                <Greeting name = 'A1'/>
                <Greeting name = 'A2'/>
                <Greeting name = 'A3'/>
            </View>
        );
    }
}

class Blink extends Component {
    constructor(props) {
        super(props);
        this.state = { showText: true };

        setInterval(() => {
            this.setState(preState => {
                return { showText: !preState.showText };
            });
        }, 1000);
    }
    render() {
        let display = this.state.showText ? this.props.text : ' ';
        return(
            <Text>{display}</Text>
        );
    }
}

class BlinkApp extends Component {
    render() {
        return(
            <View>
                <Blink text = '1'/>
                <Blink text = '2'/>
                <Blink text = '3'/>
            </View>
        );
    }
}

class LotsOfStyles extends Component {
    render() {
        return(
            <View>
                <Text style={styles.red}>just red</Text>
                <Text style={styles.bigblue}>just bigblue</Text>
                <Text style={[styles.bigblue, styles.red]}>bigblue, then red</Text>
                <Text style={[styles.red, styles.bigblue]}>red, then bigblue</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    bigblue: {
        color: 'blue',
        fontWeight: 'bold',
        fontSize: 30,
    },
    red: {
        color: 'red',
    },
    container: {
        flex: 1,
    },
    item: {
        padding: 10,
        fontSize: 18,
        height: 44,
    },
    sectionHeader: {
        paddingTop: 2,
        paddingLeft: 10,
        paddingRight: 10,
        paddingBottom: 2,
        fontSize: 14,
        fontWeight: 'bold',
        backgroundColor: 'rgba(207,247,247,1.0)',
    },
});

class FixedDimensionsBasics extends Component {
    render() {
        return(
            <View style={{
                flex: 1,
                flexDirection: 'column',
                justifyContent: 'flex-end',
                alignItems: 'flex-end',
            }}>
                <View style={{width:50, height:50, backgroundColor: 'powderblue'}} />
                <View style={{width:50, height:50, backgroundColor: 'skyblue'}} />
                <View style={{width:50, height:50, backgroundColor: 'steelblue'}} />
            </View>
        );
    }
}

class PizzaTranslator extends Component {
    constructor(props) {
        super(props);
        this.state = {text: ''};
    }

    render() {
        return(
            <View style={{padding: 10}}>
                <TextInput
                    style={{height: 40}}
                    placeholder='输入后翻译'
                    onChangeText={(text) => this.setState({text})}
                />
                <Text style={{padding: 10, fontSize: 42}}>
                    {this.state.text.split(' ').map((word) => word && '∆').join(' ')}
                </Text>
            </View>
        );
    }
}

class IScrolledDownAndWhatHappenedNextShockedMe extends Component {
    render() {
        return(
            <ScrollView>
                <Text style={{fontSize: 96}}>A</Text>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={require('../img/rn_img.png')} style={{width: 140, height: 50, marginTop: 10}}/>
                <Text style={{fontSize: 96}}>BBBBB</Text>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Text style={{fontSize: 96}}>CCCCC</Text>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
                <Image source={{uri: 'aa'}} style={{width: 40, height: 40}}/>
            </ScrollView>
        );
    }
}

class FlatListBasics extends Component {
    render() {
        return(
            <View style={styles.container}>
                <FlatList
                    data={[
                        {key: 'aaa'},
                        {key: 'bbbb'},
                        {key: 'ccccc'},
                        {key: 'dddd'},
                        {key: 'eee'},
                        {key: 'fffff'},
                        {key: 'gggggg'},
                        {key: 'hhhhhh'},
                        {key: 'iiiiii'},
                        {key: 'jjjjj'},
                        {key: 'kkkkkk'},
                        {key: 'lllllll'},
                        {key: 'lllllll'},
                        {key: 'lllllll'},
                        {key: 'xxxxxxxx'},
                        {key: 'lllllll'},
                        {key: 'dgsdgsdfsd'},
                        {key: 'lllllll'},
                        {key: '323232'},
                    ]}
                    renderItem={({item}) => <Text style={styles.item}>{item.key}</Text>}
                />
            </View>
        );
    }
}

class SectionListBasics extends Component {
    render() {
        return(
            <View style={styles.container}>
                <SectionList
                    sections={[
                        {title: 'AAA', data: [{key: 'a1'}, {key: 'a2'}]},
                        {title: 'BBB', data: [{key: 'b1'}, {key: 'b2'}, {key: 'b3'}]},
                        {title: 'CCC', data: []},
                    ]}
                    renderItem={({item}) => <Text style={styles.item}>{item.key}</Text>}
                    renderSectionHeader={({section}) => <Text style={styles.sectionHeader}>{section.title}</Text>}
                />
            </View>
        );
    }
}

function getMoviesFromApiAsync(obj) {
    return fetch('http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&APPID=15646a06818f61f7b8d7823ca833e1ce&q=94043')
        .then((response) => response.json())
        .then((responseJson) => {
            console.log(responseJson.city);
            update(obj, responseJson.city);
            return responseJson.city;
        })
        .catch((error) => {
            console.error(error);
        });
}

async function getCitysFromApiAsync(obj) {
    try {
        let response = await fetch('http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7&APPID=15646a06818f61f7b8d7823ca833e1ce&q=94043');
        let responseJson = await response.json();
        console.log(responseJson.city);
        update(obj, responseJson.city);
    } catch (e) {
        console.error(e)
    }
}

function update(obj, data) {
    if (!obj.state.isMounted) {
        obj.setState({city: data});
    }
}

export default class FetchDemo extends Component {
    constructor(props) {
        super(props);
        this.state = {city: {}, isMounted: false};
    }

    componentDidMount() {
        getCitysFromApiAsync(this);
        console.log('componentDidMount');
    }

    componentWillUnmount() {
        console.log('componentWillUnmount');
        this.setState({isMounted: true})
    }

    render() {
        return(
            <Text>{this.state.city.id ? this.state.city.id : 'sadasd'}</Text>
        );
    }
}

// export { Bananas, LostsOfGreetings }