import http from "../../http";
//user-section
export const login = function(data) {
    return http.post('/user/login', data)
}
export const getUserInfo = function(data) {
    return http.get('/user/userinfo',{params:data})
}
export const updateUserInfo = function(data) {
    return http.post('/user/updateUser', data)
}
//user-section
//upload-section
export const uploadFile=function (data) {
    return http.post('/fh-upload/upload',data)
}
//upload-section