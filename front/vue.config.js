const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  lintOnSave: false,

  configureWebpack: {
    plugins: [
      require('unplugin-element-plus/webpack')({
        // options
      }),
    ],
  },

  // 개발 서버 설정
  devServer: {
    // 프록시 설정
    proxy: {
      // 프록시 요청을 보낼 api의 시작 부분
      '/TEAMMOVIE': {
        // 프록시 요청을 보낼 서버의 주소
        target: 'http://localhost:9090',
        changeOrigin: true,
        logLevel: 'debug',
      }
    },
    port: 8081
  },


  // 리소스의 위치
  publicPath: '/TEAMMOVIE/vue/'
})