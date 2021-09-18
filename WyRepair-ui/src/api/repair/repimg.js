import request from '@/utils/request'

// 查询维修单图片列表
export function listImg(query) {
  return request({
    url: '/repair/repimg/list',
    method: 'get',
    params: query
  })
}

// 查询维修单图片详细
export function getImg(repimgId) {
  return request({
    url: '/repair/repimg/' + repimgId,
    method: 'get'
  })
}

// 新增维修单图片
export function addImg(data) {
  return request({
    url: '/repair/repimg',
    method: 'post',
    data: data
  })
}

// 修改维修单图片
export function updateImg(data) {
  return request({
    url: '/repair/repimg',
    method: 'put',
    data: data
  })
}

// 删除维修单图片
export function delImg(repimgId) {
  return request({
    url: '/repair/repimg/' + repimgId,
    method: 'delete'
  })
}

// 导出维修单图片
export function exportImg(query) {
  return request({
    url: '/repair/repimg/export',
    method: 'get',
    params: query
  })
}
