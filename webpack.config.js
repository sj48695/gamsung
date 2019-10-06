var path = require('path');
 
module.exports = {
    context: path.resolve(__dirname, 'src/main/jsx'),
    entry: {
        // main: './App.jsx',
        admin:'./adminIndex.jsx',
        myProducts:'./containers/mypage/myProducts.jsx',
        myRequestProducts:'./containers/mypage/myRequestProducts.jsx',
        myEditUserData:'./editUserIndex.jsx',
        message:'./containers/mypage/message.jsx',
        report:'./reportIndex.jsx'
    },
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/js/react/[name].bundle.js'
        // filename: './src/main/resources/static/js/react/bundle.js'
    },
    mode: 'none',
    module: {
        rules: [ {
            test: /\.(js|jsx)$/,
            exclude: /(node_modules)/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: [ '@babel/preset-env', '@babel/preset-react' ]
                }
            }
        }, {
            test: /\.(css|scss|sass)$/, // == /\.s?ss$/
            use: [ 'style-loader', 'css-loader', 'sass-loader' ],
            exclude: /(node_modules)/
        } ]
    }
};