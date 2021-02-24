
module.exports = {
    devServer: {
        disableHostCheck: true,
        open: true,
        port: 82,
        proxy: {
            '/cw-api': {
                target: 'http://localhost:5000',
                changeOrigin: true,
                pathRewrite: {
                    '^/cw-api': ''
                }
            }
        }
    },
    publicPath: './',
};