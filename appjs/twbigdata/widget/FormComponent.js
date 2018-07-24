import React, {PureComponent} from 'react'
import {
    View,
    Text,
    TextInput,
    TouchableNativeFeedback,
} from 'react-native'
import {styles} from './AllStyles'
import {IconFont} from '../iconfont/Icon'

/**
 * 输入
 */
class ItemHorizontalInput extends PureComponent {
    render() {
        const {
            textTitle,
            required,
            rootStyle,
            borderTop,
            borderBottom,
        } = this.props;

        return (
            <View
                style={[styles.itemView, rootStyle, borderTop && styles.borderTop, borderBottom && styles.borderBottom]}>
                {required && (<Text style={{color: 'red'}}>*</Text>)}
                <Text style={styles.itemTitle}>
                    {textTitle}
                </Text>
                <TextInput
                    style={styles.itemInput}
                    placeholderTextColor='#787878'
                    underlineColorAndroid='transparent'
                    multiline={false}
                    {...this.props}/>
            </View>
        )
    }
}

/**
 * 输入
 */
class ItemVerticalInput extends PureComponent {
    render() {
        const {
            textTitle,
            required,
            rootStyle,
            inputStyle,
            borderTop,
            borderBottom,
        } = this.props;

        return (
            <View
                style={[styles.itemVerticalView, rootStyle, borderTop && styles.borderTop, borderBottom && styles.borderBottom]}>
                <View style={{flexDirection: 'row'}}>
                    {required && (<Text style={{color: 'red'}}>*</Text>)}
                    <Text style={styles.itemTitle}>
                        {textTitle}
                    </Text>
                </View>
                <TextInput
                    style={[styles.itemVerticalInput, inputStyle]}
                    placeholderTextColor='#787878'
                    underlineColorAndroid='transparent'
                    multiline={true}
                    {...this.props}/>
            </View>
        )
    }
}

/**
 * 点击
 */
class ItemClick extends PureComponent {
    render() {
        const {
            textTitle,
            placeholder,
            required,
            onPress,
            content,
            rootStyle,
            borderTop,
            borderBottom,
        } = this.props;

        return (
            <TouchableNativeFeedback style={styles.full} onPress={onPress}>
                <View
                    style={[styles.itemView, rootStyle, borderTop && styles.borderTop, borderBottom && styles.borderBottom]}>
                    {required && (<Text style={{color: 'red'}}>*</Text>)}
                    <Text style={styles.itemTitle}>
                        {textTitle}
                    </Text>
                    <Text style={[styles.itemInput, {color: content ? 0x000000de : '#787878'}]}>
                        {content ? content : placeholder}
                    </Text>
                    <IconFont style={{marginLeft: 10}} name={'next'} size={15} color={'#787878'}/>
                </View>
            </TouchableNativeFeedback>
        )
    }
}

/**
 * 图片上传，拍照或相册选择图片
 */
class UploadImage extends PureComponent {
    render() {
        const {
            textTitle,
            required,
            rootStyle,
            borderTop,
            borderBottom,
            onPress,
        } = this.props;

        return (
            <View
                style={[styles.itemVerticalView, rootStyle, borderTop && styles.borderTop, borderBottom && styles.borderBottom]}>
                <View style={{flexDirection: 'row'}}>
                    {required && (<Text style={{color: 'red'}}>*</Text>)}
                    <Text style={styles.itemTitle}>
                        {textTitle}
                    </Text>
                </View>
                <View
                    style={[styles.itemImageList]}>
                    <TouchableNativeFeedback onPress={onPress}>
                        <View>
                            <IconFont style={styles.itemImagePlus} name={'plus'} size={25} color={'#787878'}/>
                        </View>
                    </TouchableNativeFeedback>
                </View>
            </View>
        )
    }
}

export {
    ItemHorizontalInput,
    ItemVerticalInput,
    ItemClick,
    UploadImage,
}