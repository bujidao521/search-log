import Vue from 'vue'
import ElementUI from 'element-ui'


Vue.use(ElementUI)
Vue.prototype.$ELEMENT = { size: 'small', zIndex: 3000 };

Vue.prototype.$notify = ElementUI.Notification;

Vue.prototype.$success = (title,message)=> ElementUI.Notification({
    title: title,
    message: message,
    type: 'success'
});
Vue.prototype.$error  = (title,message)=> ElementUI.Notification.error({
    title: title,
    message: message
});

Vue.prototype.$warning = (title,message)=> ElementUI.Notification({
    title: title,
    message: message,
    type: 'warning'
});