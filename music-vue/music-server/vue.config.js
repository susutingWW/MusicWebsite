const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8081,     // 端口
  },
  lintOnSave: false   // 取消 eslint 验证
})

