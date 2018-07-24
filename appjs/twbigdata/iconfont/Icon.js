import {createIconSet} from 'react-native-vector-icons'

const glyphMap = {
    plus: parseInt('e6d8', 16),
    next: parseInt('e611', 16),
    tick: parseInt('e601', 16),
};

const IconFont = createIconSet(glyphMap, 'iconfont','iconfont.ttf');

export {IconFont}