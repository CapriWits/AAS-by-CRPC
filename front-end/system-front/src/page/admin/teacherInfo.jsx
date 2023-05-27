import React, { useEffect, useState } from 'react'
import SimpleTable from '../../comp/table/SimpleTable'
import { getInfoApi, changeApi, addApi, delApi } from './api'
import { message } from 'antd';
/**
* @author
* @function StudentInfo
**/

const TeacherInfo = (props) => {
  const [data, setData] = useState()
  const { messageApi } = message
  const TITLE_MAP = {
    unique_id: '教师编号',
    name: '姓名',
    phone: '电话',
    gender: '性别',
    nationality: '民族',
    birthday: '生日',
    department: '院系',
    campus: '校区',
    email: '教师邮箱',
    password: '登录密码'
  }

  const TITLE_ARR = [
    'unique_id',
    'gender',
    'nationality',
    'department',
    'campus'
  ]

  useEffect(() => {
    const param = { filter: { id: null, name: null, department: null } }
    try {
      getInfoApi('tutor/get_tutor_info_with_filter', param).then((res) => {
        setData(res.data.data.tutor_info)
      })
    } catch (error) {
      messageApi.open({
        type: 'error',
        content: '请求失败',
      });
    }
  }, [])


  const changeData = (data, selcetData) => {
    setData([...data])
    changeApi('tutor/update_tutor_info', selcetData)
  }
  const delData = (info) => {
    setData(pre => { return pre.filter(item => item.unique_id !== info.unique_id) })
    delApi('tutor/delete_tutor_info', { id: info.unique_id })
  }
  const addData = (info) => {
    addApi('tutor/insert_tutor_info', info).then(res => {
      if (res.data.code !== 200) {
        messageApi.open({
          type: 'error',
          content: '添加失败|请查看输入是否为空，并查看网络是否正常',
        });
      }
    })
  }
  return (
    <SimpleTable addData={addData} delData={delData} changeData={changeData} btnTitle={'添加教师'} data={data} TITLE_MAP={TITLE_MAP} TITLE_ARR={TITLE_ARR} />
  )
}

export default TeacherInfo