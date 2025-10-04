import request from '@/utils/request'

export function getMenuPage(data) {
  return request({
    url: '/system/menu/page',
    method: 'get',
    params: data
  })
}

export function updateMenu(data) {
  return request({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

export function addMenu(data) {
  return request({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}
