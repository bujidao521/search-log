
module.exports = {
    pages: {
        index:{
            // page 的入口
            entry: "./src/main.js",
            // 模板来源
            template: "./public/index.html",
            // 在 dist/index.html 的输出
            filename: "index.html",
        }
    },
    devServer:{
        port:8004,
        open:true,
        proxy:{
            "/":{
                target:"http://127.0.0.1:9900",
                pathRewrite:{
                    "/":""
                }
            },


        }

    }
}