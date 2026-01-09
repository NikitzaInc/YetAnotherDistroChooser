const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api-repology': {
        target: 'https://repology.org',
        changeOrigin: true,
        pathRewrite: { '^/api-repology': '' },
        logLevel: 'debug'
      }
    }
  }
})