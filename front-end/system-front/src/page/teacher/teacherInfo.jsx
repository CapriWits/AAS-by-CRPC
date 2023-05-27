import React, { useEffect, useState } from 'react'
import { Badge, Descriptions, Button, Modal } from 'antd';
import { Post } from '../../postApi/index'
import todoStore from '../../mobx/store';
/**
* @author
* @function TeacherInfo
**/

const TeacherInfo = (props) => {
  useEffect(() => {
    const id = window.localStorage.getItem('id')
  }, [])
  return (
    <>
      <Descriptions title="教师信息" bordered>
        <Descriptions.Item label="姓名">{todoStore.teacherInfo.name}</Descriptions.Item>
        <Descriptions.Item label="教师号">{todoStore.teacherInfo.unique_id}</Descriptions.Item>
        <Descriptions.Item label="电话" span={2}>
          {todoStore.teacherInfo.phone}
        </Descriptions.Item>
        <Descriptions.Item label="性别">
          {todoStore.teacherInfo.gender}
        </Descriptions.Item>
        <Descriptions.Item label="民族">{todoStore.teacherInfo.nationality}</Descriptions.Item>
        <Descriptions.Item label="生日">{todoStore.teacherInfo.birthday}</Descriptions.Item>
        <Descriptions.Item label="学院">
          {todoStore.teacherInfo.department}
        </Descriptions.Item>
        <Descriptions.Item label="校区">
          {todoStore.teacherInfo.campus}
        </Descriptions.Item>
      </Descriptions>
    </>

  )
}

export default TeacherInfo
