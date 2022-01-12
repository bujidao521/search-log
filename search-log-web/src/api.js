import request from "./request";


export const LogSearch = params => request.post(`/log/search`,params);
export const LogAllSearch = params => request.post(`/log/allSearch`,params);
export const SearchFileName = params => request.get(`/log/searchFileName?appId=${params}`);

export const AppInfoList = params => request.post(`/info/list`,params);
export const AppInfoSaveOrUpdate = params => request.post(`/info/saveOrUpdate`,params);
export const AppInfoDelete = params => request.get(`/info/delete?id=${params}`);
export const AppInfolistOption = params => request.get(`/info/listOption?keyword=${params}`);

export const InstanceDelete = params => request.get(`/instance/delete?id=${params}`);
export const InstanceSaveOrUpdate = params => request.post(`/instance/saveOrUpdate`,params);
export const InstanceList = params => request.post(`/instance/list`,params);



