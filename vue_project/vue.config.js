const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false// 类似于关闭warning----设置是否在开发环境下每次保存代码时都启用 eslint验证
})
