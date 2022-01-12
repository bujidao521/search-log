import Vue from "vue";
import VueRouter from "vue-router";


Vue.use(VueRouter);

const importFile = (path)=>()=>import  (`@/${path}`);
const routes = [
    {
        path:"/login",
        component:importFile("views/login/Login")
    },
    {
        path:"/homePage",
        component:importFile("views/home/HomePage"),
        children:[

            { path:"/AppInfo",
                name:"应用管理",
                component:importFile("views/home/children/model/AppInfo")
            },
            { path:"/LogSearch",
                name:"日志查询",
                component:importFile("views/home/children/model/LogSearch")
            },
            { path:"/LogAllSearch",
                name:"全文查询",
                component:importFile("views/home/children/model/LogAllSearch")
            },
        ]


    },
    {
        //自动调转
        path: "/",
        redirect:"/AppInfo"
    },
    {
        path:"*",
        redirect:"/AppInfo"
    }
]

const router = new VueRouter({
    routes
})


export default router;