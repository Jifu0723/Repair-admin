import request from '@/utils/request'

//按报修时间、设备维修后状态、维修人姓名输出日、周、月报表(条件查询)
export function queryrepairOrderList(query) {
  return request({
    url: '/repair/tb/queryrepairOrderList',
    method: 'get',
    params: query
  })
}


// 按报修时间和报修类型统计报修信息输出日、周、月报表(渲染表格数据)
export function countrepairTimeByrepairType(query) {
  return request({
    url: '/repair/tb/countrepairTimeByrepairType',
    method: 'get',
    params: query
  })
}



