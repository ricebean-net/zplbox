/**
 * Util method for bytes length to a humand readable string.
 */
function bytes2readable(length) {
    if(length === 0) {
        return '0 B';
    };
    var i = Math.floor( Math.log(length) / Math.log(1024) );
    return ( length / Math.pow(1024, i) ).toFixed(1) * 1 + ' ' + ['B', 'KB', 'MB', 'GB', 'TB'][i];
};

const exportFunctions = {
    bytes2readable
};

export default exportFunctions;