import {
    StyleSheet,
} from 'react-native'

const styles = StyleSheet.create({
    full: {
        flex: 1,
        backgroundColor: '#fafafa',
    },
    container: {
        backgroundColor: '#fff',
    },
    itemView: {
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        paddingTop: 16,
        paddingBottom: 16,
    },
    itemVerticalView: {
        paddingTop: 16,
        paddingBottom: 16,
    },
    itemTitle: {
        color: 0x000000de,
        fontSize: 15,
    },
    itemInput: {
        flex: 1,
        padding: 0,
        textAlign: 'right',
        color: 0x000000de,
        fontSize: 15,
    },
    itemVerticalInput: {
        marginTop: 12,
        paddingTop: 2,
        paddingBottom: 2,
        color: 0x000000de,
        fontSize: 15,
        textAlignVertical: 'top',
        borderRadius: 1.1,
        borderWidth: 0.6,
        borderColor: '#b4b4b4',
    },
    borderTop: {
        borderColor: '#eee',
        borderTopWidth: 1,
    },
    borderBottom: {
        borderColor: '#eee',
        borderBottomWidth: 1,
    },
    bigSubmitText: {
        color: '#fff',
        fontSize: 15,
        textAlign: 'center',
        textAlignVertical: 'center',
        height: 48,
        backgroundColor: '#dd2c00',
        marginLeft: 14,
        marginRight: 14,
        marginTop: 16,
        marginBottom: 24,
        borderRadius: 2,
    },
    itemImageList: {
        justifyContent: 'space-between',
        alignContent: 'flex-start',
        flexDirection: 'row',
        flexWrap: 'wrap',
        marginTop: 12,
    },
    itemImagePlusBorder: {
    },
    itemImagePlus: {
        padding: 20,
        borderRadius: 1,
        borderWidth: 1,
        borderColor: '#787878',
    },
});

export {styles}