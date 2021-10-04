import request from '@/utils/request'

//后勤部门管理员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月报表(条件查询)
export function queryRepairAdminOrderList(query) {
  return request({
    url: '/repair/tb/queryRepairAdminOrderList',
    method: 'get',
    params: query
  })
}


// 后勤部门管理员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月报表(渲染表格数据)
export function countrepairTimeByrepairType(query) {
  return request({
    url: '/repair/tb/countrepairTimeByrepairType',
    method: 'get',
    params: query
  })
}

// 后勤部门管理员按二级学院、报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月报表(渲染表格数据)
export function CountCollegeByRepairOrder(query) {
  return request({
    url: '/repair/tb/CountCollegeByRepairOrder',
    method: 'get',
    params: query
  })
}




