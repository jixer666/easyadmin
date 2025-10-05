import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}

export function getUserPage(data) {
  return request({
    url: '/system/user/page',
    method: 'get',
    params: data
  })
}

export function getUserRole(userId) {
  return request({
    url: '/system/user/getUserRole/' + userId,
    method: 'get',
  })
}

export function saveUserRole(data) {
  return request({
    url: '/system/user/saveUserRole',
    method: 'post',
    data
  })
}
