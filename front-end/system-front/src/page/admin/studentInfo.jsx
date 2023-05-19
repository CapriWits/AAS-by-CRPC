import React, { useState } from 'react'
import { useEffect } from 'react';
import { getInfoApi, changeApi, addApi, delApi } from './api'
import SimpleTable from '../../comp/table/SimpleTable'
import { message } from 'antd';
/**
* @author
* @function StudentInfo
**/

const StudentInfo = (props) => {

  const TITLE_MAP = {
    unique_id: '学号',
    name: '姓名(点击查看详情)',
    id_card_num: '身份证号',
    phone: '电话',
    gender: '性别',
    nationality: '民族',
    birthday: '生日',
    exam_from: '生源地',
    gaokao_score: '高考分数',
    graduated_school: '毕业学校',
    department: '院系',
    major: '专业',
    campus: '校区',
    class: '班级',
    trainning_level: '培养层次',
    coupon: '学分券',
    email: '学生邮箱',
    password: '登录密码'
  }

  const TITLE_ARR = [
    'unique_id',
    'gender',
    'nationality',
    'department',
    'major',
    'campus',
    'class'
  ]

  const { messageApi } = message
  const [data, setData] = useState()
  const changeData = (data, selcetData) => {
    setData([...data])
    changeApi('student/update_student_info', selcetData)
  }
  const delData = (info) => {
    setData(pre => { return pre.filter(item => item.unique_id !== info.unique_id) })
    delApi('student/delete_student_info', { id: info.unique_id })
  }
  const addData = (info) => {
    addApi('student/insert_student_info', info).then(res => {
      if (res.data.code !== 200) {
        messageApi.open({
          type: 'error',
          content: '添加失败|请查看输入是否为空，并查看网络是否正常',
        });
      }
    })
  }

  useEffect(() => {
    const param = { filter: { id: null, name: null, department: null } }
    try {
      getInfoApi('student/get_student_info_with_filter', param).then((res) => {
        setData(res.data.data.student_info)
      })
    } catch (error) {
      messageApi.open({
        type: 'error',
        content: '请求失败',
      });
    }
  }, [])

  return (
    <SimpleTable addData={addData} delData={delData} changeData={changeData} btnTitle={'添加学生'} data={data} TITLE_MAP={TITLE_MAP} TITLE_ARR={TITLE_ARR} />
  )
}

export default StudentInfo