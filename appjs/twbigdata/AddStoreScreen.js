import React, {PureComponent} from 'react'
import {
    View,
    ScrollView,
    Text,
    TouchableNativeFeedback,
    Alert,
} from 'react-native'
import {DialogList} from './widget/DialogList'
import {
    ItemHorizontalInput,
    ItemVerticalInput,
    ItemClick,
    UploadImage,
} from './widget/FormComponent'
import {styles} from './widget/AllStyles'
import CodePush from 'react-native-code-push'

class AddStoreScreen extends PureComponent {
    static navigationOptions = ({navigation}) => {
        return {}
    };

    constructor(props) {
        super(props);
        this.state = {showCompany: false, chooseCompany: null}
    }

    _showCompanyDialog(show) {
        this.setState({showCompany: show})
    }

    _setCompany(company) {
        company && this.setState({chooseCompany: company})
    }

    render() {
        return (
            <ScrollView style={styles.full}>
                <View style={[styles.container, {paddingLeft: 16, paddingRight: 16}, styles.borderBottom]}>
                    {this.state.showCompany && (
                        <DialogList
                            title={'选择中介'}
                            data={[{key: 1, name: '未知'}, {key: 2, name: '太屋'},
                                {key: 3, name: '菁英地产'}, {key: 4, name: '链家'},
                                {key: 5, name: '中原'}, {key: 6, name: '我爱我家'},
                                {key: 7, name: '易居房地产'}, {key: 8, name: '悟空找房'}]}
                            defaultItem={this.state.chooseCompany}
                            onDismiss={(company) => {
                                this._showCompanyDialog(false)
                                this._setCompany(company)
                            }}/>
                    )}
                    <ItemHorizontalInput
                        required={true}
                        textTitle='门店名称'
                        placeholder='请输入门店名字'
                        borderBottom={true}/>
                    <ItemClick
                        required={true}
                        textTitle='中介'
                        placeholder='请选择'
                        content={this.state.chooseCompany}
                        borderBottom={true}
                        onPress={() => {
                            this._showCompanyDialog(true)
                        }}/>
                    <ItemClick
                        required={true}
                        textTitle='地图标注'
                        placeholder='点击选择位置'
                        borderBottom={true}/>
                    <ItemHorizontalInput
                        required={true}
                        textTitle='门店地址'
                        placeholder='请输入门店地址'
                        borderBottom={true}/>
                    <ItemHorizontalInput
                        textTitle='人员数量'
                        placeholder='大致人员数量'
                        borderBottom={true}/>
                    <ItemClick
                        textTitle='主营小区'
                        placeholder='请选择'/>
                </View>

                <View style={[styles.container, {paddingLeft: 16, paddingRight: 16, marginTop: 16}, styles.borderTop, styles.borderBottom]}>
                    <ItemClick
                        textTitle='门店状态'
                        placeholder='请选择'
                        borderBottom={true}/>
                    <ItemClick
                        textTitle='开店时间'
                        placeholder='请选择'
                        borderBottom={true}/>
                    <ItemVerticalInput
                        textTitle='备注'
                        placeholder='请输入备注'
                        maxLength={500}
                        inputStyle={{height: 150}}/>
                </View>

                <View style={[styles.container, {paddingLeft: 16, paddingRight: 16, marginTop: 16}, styles.borderTop, styles.borderBottom]}>
                    <UploadImage
                        textTitle='照片  (最多10张)'/>
                </View>

                <TouchableNativeFeedback
                    onPress={() => {
                        Alert.alert('提交')
                    }}>
                    <Text style={styles.bigSubmitText}>提交</Text>
                </TouchableNativeFeedback>
            </ScrollView>
        )
    }
}

export {AddStoreScreen}